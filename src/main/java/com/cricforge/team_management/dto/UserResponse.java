package com.cricforge.team_management.dto;

public record UserResponse(
        Long id,
        String name,
        String email
) {}