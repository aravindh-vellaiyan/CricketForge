package com.cricforge.team_management.mapper;

import com.cricforge.team_management.domain.Match;
import com.cricforge.team_management.dto.MatchResponse;
import com.cricforge.team_management.dto.PlayerSummary;
import com.cricforge.team_management.dto.TeamSummary;

public class MatchMapper {

    public static MatchResponse toResponse(Match match) {

        PlayerSummary striker = match.getStriker() != null ?
                new PlayerSummary(match.getStriker().getId(),
                        match.getStriker().getFirstName() + " " + safe(match.getStriker().getLastName()))
                : null;

        PlayerSummary nonStriker = match.getNonStriker() != null ?
                new PlayerSummary(match.getNonStriker().getId(),
                        match.getNonStriker().getFirstName() + " " + safe(match.getNonStriker().getLastName()))
                : null;

        PlayerSummary bowler = match.getBowler() != null ?
                new PlayerSummary(match.getBowler().getId(),
                        match.getBowler().getFirstName() + " " + safe(match.getBowler().getLastName()))
                : null;

        TeamSummary teamA = new TeamSummary(
                match.getTeamA().getId(),
                match.getTeamA().getName()
        );

        TeamSummary teamB = new TeamSummary(
                match.getTeamB().getId(),
                match.getTeamB().getName()
        );

        return new MatchResponse(
                match.getId(),
                match.getStatus().name(),
                match.getInnings(),
                match.getTotalRuns(),
                match.getWickets(),
                match.getOvers(),
                match.getBalls(),
                striker,
                nonStriker,
                bowler,
                teamA,
                teamB
        );
    }

    private static String safe(String s) {
        return s == null ? "" : s;
    }
}