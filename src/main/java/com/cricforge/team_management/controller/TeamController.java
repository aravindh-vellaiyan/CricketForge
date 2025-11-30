package com.cricforge.team_management.controller;

import com.cricforge.team_management.domain.UserAccount;
import com.cricforge.team_management.dto.TeamRegistrationRequest;
import com.cricforge.team_management.dto.TeamResponse;
import com.cricforge.team_management.service.TeamService;
import com.cricforge.team_management.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> register(@RequestBody TeamRegistrationRequest req) {
        return ResponseEntity.ok(teamService.registerTeam(req));
    }

    @GetMapping("/teams")
    public List<TeamResponse> getTeams(HttpServletRequest req) {
        UserAccount user = (UserAccount) req.getAttribute("authenticatedUser");
        return teamService.getAllTeams();
    }
}