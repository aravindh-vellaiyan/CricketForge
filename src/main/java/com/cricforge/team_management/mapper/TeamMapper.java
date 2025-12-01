package com.cricforge.team_management.mapper;

import com.cricforge.team_management.domain.Player;
import com.cricforge.team_management.domain.Team;
import com.cricforge.team_management.domain.TeamRole;
import com.cricforge.team_management.dto.PlayerResponse;
import com.cricforge.team_management.dto.TeamResponse;
import com.cricforge.team_management.dto.UserResponse;

import java.util.List;

public class TeamMapper {

    public static TeamResponse toResponse(Team team) {

        List<UserResponse> admins =
                team.getUserRoles().stream()
                        .filter(role -> role.getRole() == TeamRole.TEAM_ADMIN)
                        .map(role -> new UserResponse(
                                role.getUser().getId(),
                                role.getUser().getName(),
                                role.getUser().getEmail(),
                                TeamRole.TEAM_ADMIN.name()
                        ))
                        .toList();

        List<PlayerResponse> players =
                team.getPlayers().stream()
                        .map(TeamMapper::toPlayerResponse)
                        .toList();

        return new TeamResponse(
                team.getId(),
                team.getName(),
                admins,
                players
        );
    }

    private static PlayerResponse toPlayerResponse(Player player) {
        return new PlayerResponse(
                player.getId(),
                player.getFirstName(),
                player.getLastName(),
                player.getRole(),
                player.getType().name()
        );
    }
}