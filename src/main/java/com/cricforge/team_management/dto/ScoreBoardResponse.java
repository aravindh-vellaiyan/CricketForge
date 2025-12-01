package com.cricforge.team_management.dto;

public record ScoreBoardResponse (
        Long matchId,
        int innings,
        int runs,
        int wickets,
        int overs,
        int balls,
        PlayerResponse striker,
        PlayerResponse nonStriker,
        PlayerResponse bowler
){}
