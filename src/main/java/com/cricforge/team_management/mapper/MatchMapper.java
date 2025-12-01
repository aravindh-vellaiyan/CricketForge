package com.cricforge.team_management.mapper;

import com.cricforge.team_management.domain.Match;
import com.cricforge.team_management.domain.ScoreBoard;
import com.cricforge.team_management.dto.MatchResponse;
import com.cricforge.team_management.dto.PlayerResponse;
import com.cricforge.team_management.dto.TeamResponse;

public class MatchMapper {

    public static MatchResponse toResponse(Match match, ScoreBoard sb) {

        PlayerResponse striker = sb.getStriker() != null ?
                new PlayerResponse(
                        sb.getStriker().getId(),
                        sb.getStriker().getFirstName(),
                        sb.getStriker().getLastName(),
                        sb.getStriker().getRole(),
                        sb.getStriker().getType().name()
                ) : null;

        PlayerResponse nonStriker = sb.getNonStriker() != null ?
                new PlayerResponse(
                        sb.getNonStriker().getId(),
                        sb.getNonStriker().getFirstName(),
                        sb.getNonStriker().getLastName(),
                        sb.getNonStriker().getRole(),
                        sb.getNonStriker().getType().name()
                ) : null;

        PlayerResponse bowler = sb.getBowler() != null ?
                new PlayerResponse(
                        sb.getBowler().getId(),
                        sb.getBowler().getFirstName(),
                        sb.getBowler().getLastName(),
                        sb.getBowler().getRole(),
                        sb.getBowler().getType().name()
                ) : null;

        TeamResponse teamA = new TeamResponse(
                match.getTeamA().getId(),
                match.getTeamA().getName(),
                null,   // admins not needed here
                null    // players not needed here
        );

        TeamResponse teamB = new TeamResponse(
                match.getTeamB().getId(),
                match.getTeamB().getName(),
                null,
                null
        );

        return new MatchResponse(
                match.getId(),
                match.getStatus().name(),
                sb.getInnings(),
                sb.getRuns(),
                sb.getWickets(),
                sb.getOvers(),
                sb.getBalls(),
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