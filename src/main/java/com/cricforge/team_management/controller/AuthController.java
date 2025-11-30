package com.cricforge.team_management.controller;

import com.cricforge.team_management.domain.UserAccount;
import com.cricforge.team_management.domain.UserSession;
import com.cricforge.team_management.dto.LoginRequest;
import com.cricforge.team_management.dto.SignupRequest;
import com.cricforge.team_management.dto.UserResponse;
import com.cricforge.team_management.exception.InvalidSessionException;
import com.cricforge.team_management.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public UserResponse signup(@RequestBody SignupRequest req) {
        return userService.signup(req);
    }

    @PostMapping("/login")
    public UserResponse login(@RequestBody LoginRequest req, HttpServletResponse response) {

        UserSession session = userService.login(req);

        Cookie cookie = new Cookie("SESSION_ID", session.getSessionId());
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(12 * 60 * 60); // 12 hours
        response.addCookie(cookie);
        return new UserResponse(
                session.getUser().getId(),
                session.getUser().getName(),
                session.getUser().getEmail()
        );
    }

    @GetMapping("/me")
    public UserResponse me(@CookieValue(value = "SESSION_ID", required = false) String sessionId) {

        UserAccount user = userService.validateSession(sessionId)
                .orElseThrow(() -> new InvalidSessionException("Invalid or expired session"));

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }
}