package com.cricforge.team_management.service;

import com.cricforge.team_management.domain.*;
import com.cricforge.team_management.dto.TeamRegistrationRequest;
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

    @Transactional
    public Team registerTeam(TeamRegistrationRequest req, UserAccount creator) {

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

        Team registeredTeam = teamRepo.save(team);

        // Assign creator as TEAM_ADMIN
        UserTeamRole utr = new UserTeamRole();
        utr.setTeam(registeredTeam);
        utr.setUser(creator);
        utr.setRole(TeamRole.TEAM_ADMIN);

        userTeamRoleRepo.save(utr);

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
}