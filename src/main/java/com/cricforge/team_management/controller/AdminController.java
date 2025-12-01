package com.cricforge.team_management.controller;

import com.cricforge.team_management.domain.Role;
import com.cricforge.team_management.domain.UserAccount;
import com.cricforge.team_management.service.AuthorizationService;
import com.cricforge.team_management.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserService userService;

    @Autowired
    AuthorizationService auth;

    @PostMapping("/update-user-role/{userId}")
    public void promoteToAppAdmin(
            @PathVariable Long userId,
            HttpServletRequest req
    ) {
        UserAccount admin = (UserAccount) req.getAttribute("authenticatedUser");
        auth.requireAppAdmin(admin);
        userService.updateUserRole(userId, Role.APP_ADMIN);
    }
}