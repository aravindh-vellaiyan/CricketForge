package com.cricforge.team_management.dto;

public record LoginRequest(
        String email,
        String password
) {}