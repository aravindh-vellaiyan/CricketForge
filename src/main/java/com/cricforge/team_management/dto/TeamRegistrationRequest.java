package com.cricforge.team_management.dto;

import java.util.List;

public record TeamRegistrationRequest(
        String name,
        List<PlayerRequest> players
) {}