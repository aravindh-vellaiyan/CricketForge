package com.cricforge.team_management.dto;

public record CreatePlayerRequest(
        String firstName,
        String lastName,
        String role,
        String type
) {}