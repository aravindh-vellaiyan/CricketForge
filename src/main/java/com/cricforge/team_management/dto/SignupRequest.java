package com.cricforge.team_management.dto;

public record SignupRequest(
        String name,
        String email,
        String password
) {}
