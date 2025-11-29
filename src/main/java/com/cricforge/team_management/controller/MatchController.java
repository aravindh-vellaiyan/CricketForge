package com.cricforge.team_management.controller;

import com.cricforge.team_management.dto.MatchRequest;
import com.cricforge.team_management.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/matches")
public class MatchController {

    @Autowired
    private MatchService matchService;

    @PostMapping
    public ResponseEntity<?> createMatch(@RequestBody MatchRequest req) {
        return ResponseEntity.ok(matchService.createMatch(req));
    }
}