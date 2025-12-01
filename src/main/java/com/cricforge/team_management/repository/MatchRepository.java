package com.cricforge.team_management.repository;

import com.cricforge.team_management.domain.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, Long> {}