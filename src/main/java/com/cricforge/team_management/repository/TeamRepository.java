package com.cricforge.team_management.repository;

import com.cricforge.team_management.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {}