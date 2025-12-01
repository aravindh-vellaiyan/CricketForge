package com.cricforge.team_management.controller;

import com.cricforge.team_management.domain.Team;
import com.cricforge.team_management.domain.UserAccount;
import com.cricforge.team_management.dto.*;
import com.cricforge.team_management.exception.InvalidTeamException;
import com.cricforge.team_management.mapper.TeamMapper;
import com.cricforge.team_management.service.AuthorizationService;
import com.cricforge.team_management.service.TeamService;
import com.cricforge.team_management.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorizationService authorizationService;

    @PostMapping
    public ResponseEntity<?> register(@RequestBody TeamRegistrationRequest teamRegistrationRequest, HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(teamService.registerTeam(teamRegistrationRequest, (UserAccount)httpServletRequest.getAttribute("authenticatedUser")));
    }

    @GetMapping
    public List<TeamResponse> getTeams(HttpServletRequest req) {
        UserAccount user = (UserAccount) req.getAttribute("authenticatedUser");
        return teamService.getAllTeams()
                .stream()
                .map(TeamMapper::toResponse)
                .toList();
    }

    @PutMapping("/teams/{teamId}/players/{playerId}")
    public PlayerResponse updatePlayer(
            @PathVariable Long teamId,
            @PathVariable Long playerId,
            HttpServletRequest req,
            @RequestBody PlayerRequest body
    ) {
        UserAccount user = (UserAccount) req.getAttribute("authenticatedUser");

        Team team = teamService.getTeam(teamId).orElseThrow(() -> new NoSuchElementException("Invalid Team ID"));

        authorizationService.requireTeamAdmin(user, team);

        return teamService.updatePlayer(team, playerId, body);
    }

    @PostMapping("/teams/{teamId}/updateadmin/{userId}")
    public void updateTeamAdmin(
            @PathVariable Long teamId,
            @PathVariable Long userId,
            @RequestBody TeamRoleUpdateRequest teamRoleUpdateRequest,
            HttpServletRequest req) {

        UserAccount user = (UserAccount) req.getAttribute("authenticatedUser");

        Team team = teamService.getTeam(teamId).orElseThrow(() -> new NoSuchElementException("Invalid Team ID"));

        authorizationService.requireTeamAdmin(user, team);

        teamService.updateUserTeamRole(user, team, teamRoleUpdateRequest.newRole());
    }

    @PostMapping("/{teamId}/players")
    public PlayerResponse addPlayer(
            @PathVariable Long teamId,
            @RequestBody PlayerRequest request,
            HttpServletRequest http) {

        UserAccount user = (UserAccount) http.getAttribute("authUser");
        Team team = teamService.getTeam(teamId).orElseThrow(() -> new InvalidTeamException("Invalid Team.!"));

        authorizationService.requireTeamAdmin(user, team);

        return teamService.addPlayer(team, request);
    }
}