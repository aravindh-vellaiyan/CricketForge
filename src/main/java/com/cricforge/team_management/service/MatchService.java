package com.cricforge.team_management.service;

import com.cricforge.team_management.domain.*;
import com.cricforge.team_management.dto.BallUpdateRequest;
import com.cricforge.team_management.dto.MatchRequest;
import com.cricforge.team_management.dto.MatchResponse;
import com.cricforge.team_management.mapper.MatchMapper;
import com.cricforge.team_management.repository.BallEventRepository;
import com.cricforge.team_management.repository.MatchRepository;
import com.cricforge.team_management.repository.TeamRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class MatchService {

    @Autowired
    private MatchRepository matchRepo;

    @Autowired
    private TeamRepository teamRepo;

    @Autowired
    private BallEventRepository ballEventRepo;

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
    public MatchResponse updateBall(Match match, BallUpdateRequest req, UserAccount user) {

        if (match.getStatus() != MatchStatus.IN_PROGRESS) {
            throw new IllegalStateException("Match is not active");
        }

        BallEvent event = new BallEvent();
        event.setMatch(match);

        boolean legal = true;

        // EXTRAS
        if (req.extraType() != null) {
            legal = false;
            event.setExtra(true);
            event.setExtraType(req.extraType());
            match.setTotalRuns(match.getTotalRuns() + 1);
        }

        // Runs
        match.setTotalRuns(match.getTotalRuns() + req.runs());
        event.setRuns(req.runs());

        // Wicket
        if (req.wicket()) {
            match.setWickets(match.getWickets() + 1);
            event.setWicket(true);
            // Assign new batsman externally (another endpoint)
        }

        // Update ball/over counters
        if (legal) {
            int ball = match.getBalls() + 1;
            if (ball == 6) {
                match.setBalls(0);
                match.setOvers(match.getOvers() + 1);

                // Rotate strike end of over
                rotateStrike(match);
            } else {
                match.setBalls(ball);
            }

            // Rotate strike for odd runs
            if (req.runs() % 2 == 1) {
                rotateStrike(match);
            }
        }

        // Persist
        ballEventRepo.save(event);
        matchRepo.save(match);

        return MatchMapper.toResponse(match);
    }

    private void rotateStrike(Match m) {
        Player temp = m.getStriker();
        m.setStriker(m.getNonStriker());
        m.setNonStriker(temp);
    }

    public Match getMatch(Long matchId) {
        return matchRepo.findById(matchId)
                .orElseThrow(() -> new IllegalArgumentException("Match not found"));
    }
}