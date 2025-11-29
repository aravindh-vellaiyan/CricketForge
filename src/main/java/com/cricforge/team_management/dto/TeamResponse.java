package com.cricforge.team_management.dto;

import java.util.List;

public record TeamResponse(
        Long id,
        String name,
        List<PlayerResponse> players
) {}