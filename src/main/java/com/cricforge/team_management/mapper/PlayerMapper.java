package com.cricforge.team_management.mapper;

import com.cricforge.team_management.domain.Player;
import com.cricforge.team_management.domain.PlayerType;
import com.cricforge.team_management.dto.PlayerRequest;

public class PlayerMapper {

    public static Player toPlayer(PlayerRequest req) {
        Player player = new Player();

        player.setFirstName(req.firstName());
        player.setLastName(req.lastName());
        player.setRole(req.role());

        if (req.type() == null || req.type().isBlank()) {
            player.setType(PlayerType.NORMAL);
        } else {
            player.setType(PlayerType.valueOf(req.type().toUpperCase()));
        }
        return player;
    }
}
