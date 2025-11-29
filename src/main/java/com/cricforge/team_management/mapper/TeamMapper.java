package com.cricforge.team_management.mapper;

import com.cricforge.team_management.domain.Player;
import com.cricforge.team_management.domain.Team;
import com.cricforge.team_management.dto.PlayerResponse;
import com.cricforge.team_management.dto.TeamResponse;

import java.util.stream.Collectors;

public class TeamMapper {

    public static TeamResponse toResponse(Team team) {
        return new TeamResponse(
                team.getId(),
                team.getName(),
                team.getPlayers().stream()
                        .map(TeamMapper::toPlayerResponse)
                        .collect(Collectors.toList())
        );
    }

    private static PlayerResponse toPlayerResponse(Player p) {
        return new PlayerResponse(
                p.getId(),
                p.getFirstName(),
                p.getLastName(),
                p.getRole(),
                p.getType().name()
        );
    }
}