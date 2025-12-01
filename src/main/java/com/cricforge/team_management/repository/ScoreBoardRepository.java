package com.cricforge.team_management.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cricforge.team_management.domain.Match;
import com.cricforge.team_management.domain.ScoreBoard;

public interface ScoreBoardRepository extends JpaRepository<ScoreBoard, Long> {

    Optional<ScoreBoard> findByMatch(Match match);

    Optional<ScoreBoard> findByMatchId(Long matchId);
}