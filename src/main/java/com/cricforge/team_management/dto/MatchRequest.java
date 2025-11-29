package com.cricforge.team_management.dto;

import java.time.LocalDateTime;

public record MatchRequest(
        Long teamAId,
        Long teamBId,
        LocalDateTime scheduledAt
) {}