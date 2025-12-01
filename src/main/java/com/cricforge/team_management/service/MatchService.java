package com.cricforge.team_management.service;

import com.cricforge.team_management.domain.*;
import com.cricforge.team_management.dto.BallUpdateRequest;
import com.cricforge.team_management.dto.MatchRequest;
import com.cricforge.team_management.dto.ScoreBoardResponse;
import com.cricforge.team_management.mapper.ScoreBoardMapper;
import com.cricforge.team_management.repository.BallEventRepository;
import com.cricforge.team_management.repository.MatchRepository;
import com.cricforge.team_management.repository.ScoreBoardRepository;
import com.cricforge.team_management.repository.TeamRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class MatchService {

    @Autowired
    private MatchRepository matchRepo;

    @Autowired
    private TeamRepository teamRepo;

    @Autowired
    private BallEventRepository ballEventRepo;

    @Autowired
    private ScoreBoardRepository scoreboardRepo;

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

        return matchRepo.save(match);
    }

    @Transactional
    public ScoreBoardResponse updateBall(Match match, BallUpdateRequest req, UserAccount user) {

        ScoreBoard scoreBoard = scoreboardRepo.findByMatch(match)
                .orElseThrow(() -> new IllegalStateException("Scoreboard not initialized"));

        // All scoring logic applied to "scoreBoard", NOT match
        // - Update runs, wickets, overs, balls
        // - Rotate strike
        // - Check end of innings
        // - Save BallEvent

        scoreboardRepo.save(scoreBoard);
        return ScoreBoardMapper.toResponse(scoreBoard);
    }

    public Match getMatch(Long matchId) {
        return matchRepo.findById(matchId)
                .orElseThrow(() -> new IllegalArgumentException("Match not found"));
    }
}