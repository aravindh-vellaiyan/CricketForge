package com.cricforge.team_management.service;

import com.cricforge.team_management.domain.Player;
import com.cricforge.team_management.domain.PlayerType;
import com.cricforge.team_management.domain.Team;
import com.cricforge.team_management.dto.TeamRegistrationRequest;
import com.cricforge.team_management.dto.TeamResponse;
import com.cricforge.team_management.mapper.TeamMapper;
import com.cricforge.team_management.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepo;

    public Team registerTeam(TeamRegistrationRequest req) {

        if (req.players().size() > 15) {
            throw new IllegalArgumentException("Max 15 players allowed");
        }

        long captainCount = req.players().stream().filter(p -> "CAPTAIN".equals(p.type())).count();
        if (captainCount != 1) {
            throw new IllegalArgumentException("Exactly one captain required");
        }

        long viceCount = req.players().stream().filter(p -> "VICE_CAPTAIN".equals(p.type())).count();
        if (viceCount > 1) {
            throw new IllegalArgumentException("At most one vice-captain allowed");
        }

        Team team = new Team();
        team.setName(req.name());

        List<Player> players = req.players().stream().map(pr -> {
            if (pr.firstName() == null || pr.firstName().isBlank()) {
                throw new IllegalArgumentException("Player first name required");
            }

            Player player = new Player();
            player.setTeam(team);
            player.setFirstName(pr.firstName());
            player.setLastName(pr.lastName());
            player.setRole(pr.role());
            player.setType(PlayerType.valueOf(pr.type()));

            return player;
        }).toList();

        team.setPlayers(players);
        return teamRepo.save(team);
    }

    public List<TeamResponse> getAllTeams() {
        return teamRepo.findAll()
                .stream()
                .map(TeamMapper::toResponse)
                .toList();
    }
}