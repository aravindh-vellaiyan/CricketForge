package com.cricforge.team_management.repository;

import com.cricforge.team_management.domain.BallEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BallEventRepository extends JpaRepository<BallEvent, Long> {
}
