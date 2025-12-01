package com.cricforge.team_management.service;

import com.cricforge.team_management.domain.*;
import com.cricforge.team_management.dto.PlayerRequest;
import com.cricforge.team_management.dto.PlayerResponse;
import com.cricforge.team_management.dto.TeamRegistrationRequest;
import com.cricforge.team_management.exception.AccessDeniedException;
import com.cricforge.team_management.mapper.PlayerMapper;
import com.cricforge.team_management.repository.PlayerRepository;
import com.cricforge.team_management.repository.TeamRepository;
import com.cricforge.team_management.repository.UserAccountRepository;
import com.cricforge.team_management.repository.UserTeamRoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepo;

    @Autowired
    private UserAccountRepository userRepo;

    @Autowired
    private UserTeamRoleRepository userTeamRoleRepo;

    @Autowired
    private PlayerRepository playerRepository;

    @Transactional
    public Team registerTeam(TeamRegistrationRequest req, UserAccount creator) {

        if (req.players().size() > 15) {
            throw new IllegalArgumentException("Max 15 players allowed");
        }

        long captainCount = req.players().stream().filter(playerRequest -> "CAPTAIN".equals(playerRequest.type())).count();
        if (captainCount != 1) {
            throw new IllegalArgumentException("Exactly one captain required");
        }

        long viceCount = req.players().stream().filter(p -> "VICE_CAPTAIN".equals(p.type())).count();
        if (viceCount > 1) {
            throw new IllegalArgumentException("At most one vice-captain allowed");
        }

        Team team = new Team();
        team.setName(req.name());

        List<Player> players = req.players().stream().map(playerRequest -> {
            if (playerRequest.firstName() == null || playerRequest.firstName().isBlank()) {
                throw new IllegalArgumentException("Player first name required");
            }

            Player player = new Player();
            player.setTeam(team);
            player.setFirstName(playerRequest.firstName());
            player.setLastName(playerRequest.lastName());
            player.setRole(playerRequest.role());
            player.setType(PlayerType.valueOf(playerRequest.type()));

            return player;
        }).toList();
        team.setPlayers(players);

        Team registeredTeam = teamRepo.save(team);

        // Assign creator as TEAM_ADMIN
        UserTeamRole userTeamRole = new UserTeamRole();
        userTeamRole.setTeam(registeredTeam);
        userTeamRole.setUser(creator);
        userTeamRole.setRole(TeamRole.TEAM_ADMIN);

        userTeamRoleRepo.save(userTeamRole);

        return registeredTeam;
    }

    public List<Team> getAllTeams() {
        return teamRepo.findAll();
    }

    public Optional<Team> getTeam(Long teamId) {
        return teamRepo.findById(teamId);
    }

    public void updateUserTeamRole(UserAccount user, Team team, TeamRole teamRole) {
        UserTeamRole utr = new UserTeamRole();
        utr.setTeam(team);
        utr.setUser(user);

        if(teamRole == null || teamRole == TeamRole.TEAM_MEMBER) {
            userTeamRoleRepo.delete(utr);
            return;
        }
        utr.setRole(teamRole);
        userTeamRoleRepo.save(utr);
    }

    @Transactional
    public PlayerResponse updatePlayer(Team team, Long playerId, PlayerRequest body) {

        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new IllegalArgumentException("Player not found"));

        if (!player.getTeam().getId().equals(team.getId())) {
            throw new AccessDeniedException("Player does not belong to this team");
        }

        if (body.firstName() != null && !body.firstName().isBlank()) {
            player.setFirstName(body.firstName());
        }

        if (body.lastName() != null) {
            player.setLastName(body.lastName());
        }

        if (body.role() != null) {
            player.setRole(body.role());
        }

        if (body.type() != null && !body.type().isBlank()) {
            try {
                PlayerType newType = PlayerType.valueOf(body.type().toUpperCase());
                player.setType(newType);
            } catch (IllegalArgumentException ex) {
                throw new IllegalArgumentException("Invalid player type: " + body.type());
            }
        }

        Player saved = playerRepository.save(player);

        return new PlayerResponse(
                saved.getId(),
                saved.getFirstName(),
                saved.getLastName(),
                saved.getRole(),
                saved.getType().name()
        );
    }

    @Transactional
    public PlayerResponse addPlayer(Team team, PlayerRequest request) {

        Player player = PlayerMapper.toPlayer(request);
        player.setTeam(team);

        // max 15 players rule
        if (team.getPlayers().size() >= 15) {
            throw new IllegalArgumentException("Team cannot have more than 15 players");
        }

        Player saved = playerRepository.save(player);
        team.getPlayers().add(saved);

        return new PlayerResponse(
                saved.getId(),
                saved.getFirstName(),
                saved.getLastName(),
                saved.getRole(),
                saved.getType().name()
        );
    }
}