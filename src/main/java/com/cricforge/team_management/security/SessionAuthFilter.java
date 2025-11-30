package com.cricforge.team_management.security;

import com.cricforge.team_management.domain.UserAccount;
import com.cricforge.team_management.exception.InvalidSessionException;
import com.cricforge.team_management.service.UserService;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class SessionAuthFilter implements Filter {

    private final UserService userService;

    public SessionAuthFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String path = req.getRequestURI();

        // Allow unauthenticated endpoints
        if (path.startsWith("/auth")) {
            chain.doFilter(request, response);
            return;
        }

        // Extract session cookie
        String sessionId = null;

        if (req.getCookies() != null) {
            for (Cookie c : req.getCookies()) {
                if ("SESSION_ID".equals(c.getName())) {
                    sessionId = c.getValue();
                    break;
                }
            }
        }

        if (sessionId == null) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.getWriter().write("{\"error\":\"Missing session\"}");
            return;
        }

        try {
            UserAccount user = userService.validateSession(sessionId)
                    .orElseThrow(() -> new InvalidSessionException("Invalid or expired session"));

            req.setAttribute("authenticatedUser", user);

            chain.doFilter(request, response);

        } catch (InvalidSessionException e) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.getWriter().write("{\"error\":\"Invalid or expired session\"}");
        }
    }
}