package com.cricforge.team_management.dto;

public record MatchResponse(
        Long matchId,
        String status,
        int innings,
        int totalRuns,
        int wickets,
        int overs,
        int balls,
        PlayerResponse striker,
        PlayerResponse nonStriker,
        PlayerResponse bowler,
        TeamResponse teamA,
        TeamResponse teamB
) {}