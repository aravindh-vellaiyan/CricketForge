package com.cricforge.team_management.dto;

public record PlayerResponse(
        Long id,
        String firstName,
        String lastName,
        String role,
        String type
) {}