package com.cricforge.team_management.dto;

public record AuthResponse(
        Long id,
        String name,
        String email,
        String role
) {}