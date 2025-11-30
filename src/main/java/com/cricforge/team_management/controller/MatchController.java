package com.cricforge.team_management.controller;

import com.cricforge.team_management.domain.Match;
import com.cricforge.team_management.domain.UserAccount;
import com.cricforge.team_management.dto.BallUpdateRequest;
import com.cricforge.team_management.dto.MatchRequest;
import com.cricforge.team_management.dto.MatchResponse;
import com.cricforge.team_management.exception.AccessDeniedException;
import com.cricforge.team_management.service.AuthorizationService;
import com.cricforge.team_management.service.MatchService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/matches")
public class MatchController {

    @Autowired
    private MatchService matchService;
    @Autowired
    private AuthorizationService auth;

    @PostMapping
    public ResponseEntity<?> createMatch(@RequestBody MatchRequest req) {
        return ResponseEntity.ok(matchService.createMatch(req));
    }

    @PostMapping("/matches/{matchId}/ball")
    public MatchResponse updateBall(
            @PathVariable Long matchId,
            @RequestBody BallUpdateRequest req,
            HttpServletRequest http) {

        UserAccount user = (UserAccount) http.getAttribute("authenticatedUser");
        Match match = matchService.getMatch(matchId);

        // Check permission: only TeamAdmins of A or B can update score
        if (!auth.canScoreMatch(user, match)) {
            throw new AccessDeniedException("Not allowed to update match");
        }
        return matchService.updateBall(match, req, user);
    }
}