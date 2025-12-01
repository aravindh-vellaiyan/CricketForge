package com.cricforge.team_management.mapper;

import com.cricforge.team_management.domain.Player;
import com.cricforge.team_management.domain.ScoreBoard;
import com.cricforge.team_management.dto.PlayerSummary;
import com.cricforge.team_management.dto.ScoreBoardResponse;

public class ScoreBoardMapper {

    public static ScoreBoardResponse toResponse(ScoreBoard sb) {

        return new ScoreBoardResponse(
                sb.getMatch().getId(),
                sb.getInnings(),
                sb.getRuns(),
                sb.getWickets(),
                sb.getOvers(),
                sb.getBalls(),
                toSummary(sb.getStriker()),
                toSummary(sb.getNonStriker()),
                toSummary(sb.getBowler())
        );
    }

    private static PlayerSummary toSummary(Player p) {
        if (p == null) return null;
        return new PlayerSummary(
                p.getId(),
                p.getFirstName() + " " + (p.getLastName() == null ? "" : p.getLastName())
        );
    }
}