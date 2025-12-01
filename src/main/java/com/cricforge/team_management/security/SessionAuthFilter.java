package com.cricforge.team_management.security;

import com.cricforge.team_management.domain.UserAccount;
import com.cricforge.team_management.exception.InvalidSessionException;
import com.cricforge.team_management.service.UserService;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SessionAuthFilter extends OncePerRequestFilter {

    private final UserService userService;

    public SessionAuthFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {

        String path = request.getServletPath();

        return path.startsWith("/auth") ||
                path.startsWith("/static") ||
                path.endsWith(".css") ||
                path.endsWith(".js") ||
                path.endsWith(".png") ||
                path.endsWith(".jpg") ||
                path.endsWith(".jpeg") ||
                path.endsWith(".ico") ||
                path.endsWith(".html") ||
                path.equals("/");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Extract session cookie
        String sessionId = null;

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("SESSION_ID".equals(cookie.getName())) {
                    sessionId = cookie.getValue();
                    break;
                }
            }
        }

        if (sessionId == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\":\"Missing session\"}");
            return;
        }
        // Session validation
        UserAccount user = userService.validateSession(sessionId)
                .orElseThrow(() -> new InvalidSessionException("Invalid or expired session"));

        // Store user in request attribute so controllers can pull it
        request.setAttribute("authUser", user);

        filterChain.doFilter(request, response);
    }
}