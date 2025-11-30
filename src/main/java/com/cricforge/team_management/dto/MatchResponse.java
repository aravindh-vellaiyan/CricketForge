package com.cricforge.team_management.dto;

import java.util.List;

public record MatchResponse(
        Long matchId,
        String status,
        int innings,
        int totalRuns,
        int wickets,
        int overs,
        int balls,
        PlayerSummary striker,
        PlayerSummary nonStriker,
        PlayerSummary bowler,
        TeamSummary teamA,
        TeamSummary teamB
) {}