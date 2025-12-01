package com.cricforge.team_management.service;

import com.cricforge.team_management.domain.Role;
import com.cricforge.team_management.domain.UserAccount;
import com.cricforge.team_management.domain.UserSession;
import com.cricforge.team_management.dto.LoginRequest;
import com.cricforge.team_management.dto.SignupRequest;
import com.cricforge.team_management.repository.UserAccountRepository;
import com.cricforge.team_management.repository.UserSessionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HexFormat;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserAccountRepository userRepo;

    @Autowired
    private UserSessionRepository sessionRepo;

    private static final SecureRandom random = new SecureRandom();

    @Transactional
    public UserAccount signup(SignupRequest request) {

        if (userRepo.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email already registered");
        }

        UserAccount user = new UserAccount();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPasswordHash(hash(request.password()));
        user.setRole(Role.USER);

        return userRepo.save(user);
    }

    public UserSession login(LoginRequest req) {
        UserAccount user = userRepo.findByEmail(req.email())
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

        if (!user.getPasswordHash().equals(hash(req.password()))) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        UserSession session = new UserSession();
        session.setUser(user);
        session.setSessionId(generateSessionId());
        session.setExpiresAt(LocalDateTime.now().plusHours(12)); // Session valid for 12 hrs

        return sessionRepo.save(session);
    }

    public Optional<UserAccount> validateSession(String sessionId) {
        return sessionRepo.findBySessionId(sessionId)
                .filter(s -> s.getExpiresAt().isAfter(LocalDateTime.now()))
                .map(UserSession::getUser);
    }

    private String generateSessionId() {
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);
        return HexFormat.of().formatHex(bytes);
    }

    private String hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return HexFormat.of().formatHex(md.digest(input.getBytes()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateUserRole(Long userId, Role role) {
        UserAccount user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setRole(role);
        userRepo.save(user);
    }
}