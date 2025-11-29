package com.cricforge.team_management.service;

import com.cricforge.team_management.dto.MatchRequest;
import com.cricforge.team_management.domain.Match;
import com.cricforge.team_management.domain.Team;
import com.cricforge.team_management.repository.MatchRepository;
import com.cricforge.team_management.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class MatchService {

    @Autowired
    private MatchRepository matchRepo;

    @Autowired
    private TeamRepository teamRepo;

    public Match createMatch(MatchRequest req) {

        if (Objects.equals(req.teamAId(), req.teamBId())) {
            throw new IllegalArgumentException("Team cannot play against itself");
        }

        Team a = teamRepo.findById(req.teamAId())
                .orElseThrow(() -> new IllegalArgumentException("Team A not found"));

        Team b = teamRepo.findById(req.teamBId())
                .orElseThrow(() -> new IllegalArgumentException("Team B not found"));

        Match match = new Match();
        match.setTeamA(a);
        match.setTeamB(b);
        match.setScheduledAt(req.scheduledAt());

        return matchRepo.save(match);
    }
}