package com.cricforge.team_management.dto;

public record UserSummaryResponse(
        Long id,
        String name,
        String email
) {}