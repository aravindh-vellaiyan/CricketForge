package com.cricforge.team_management.dto;

public record PlayerRequest(
        String firstName,
        String lastName,
        String role,
        String type   // "CAPTAIN", "VICE_CAPTAIN", "NORMAL"
) {}