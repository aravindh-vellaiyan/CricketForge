package com.cricforge.team_management.repository;

import com.cricforge.team_management.domain.Match;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long> {

    List<Match> findByTeamAIdOrTeamBId(Long teamAId, Long teamBId);
}