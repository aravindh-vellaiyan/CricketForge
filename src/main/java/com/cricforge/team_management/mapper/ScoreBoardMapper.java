package com.cricforge.team_management.mapper;

import com.cricforge.team_management.domain.Player;
import com.cricforge.team_management.domain.PlayerType;
import com.cricforge.team_management.domain.ScoreBoard;
import com.cricforge.team_management.dto.PlayerResponse;
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
                toPlayerResponse(sb.getStriker()),
                toPlayerResponse(sb.getNonStriker()),
                toPlayerResponse(sb.getBowler())
        );
    }

    private static PlayerResponse toPlayerResponse(Player player) {
        if (player == null) return null;
        return new PlayerResponse(
                player.getId(),
                player.getFirstName() + " " + (player.getLastName() == null ? "" : player.getLastName()),
                player.getLastName(),
                player.getRole(),
                PlayerType.getPlayerType(player.getType())
        );
    }
}