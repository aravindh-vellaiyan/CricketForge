package com.cricforge.team_management.service;

import com.cricforge.team_management.domain.UserAccount;
import com.cricforge.team_management.domain.UserSession;
import com.cricforge.team_management.repository.UserSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class SessionService {

    @Autowired
    private UserSessionRepository sessionRepo;

    public UserSession createSession(UserAccount user) {
        UserSession session = new UserSession();
        session.setUser(user);
        session.setSessionId(UUID.randomUUID().toString());
        session.setExpiresAt(LocalDateTime.now().plusDays(7));
        return sessionRepo.save(session);
    }

    public void deleteSession(String sessionId) {
        sessionRepo.deleteBySessionId(sessionId);
    }
}
