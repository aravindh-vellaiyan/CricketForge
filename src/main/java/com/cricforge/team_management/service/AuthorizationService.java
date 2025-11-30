package com.cricforge.team_management.service;

import com.cricforge.team_management.domain.Role;
import com.cricforge.team_management.domain.Team;
import com.cricforge.team_management.domain.TeamRole;
import com.cricforge.team_management.domain.UserAccount;
import com.cricforge.team_management.exception.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    public void requireAppAdmin(UserAccount user) {
        if (user.getRole() != Role.APP_ADMIN) {
            throw new AccessDeniedException("Only APP_ADMIN can perform this action");
        }
    }

    public void requireTeamAdmin(UserAccount user, Team team) {
        if (user.getRole() == Role.APP_ADMIN || isTeamAdmin(user, team)) return;
        throw new AccessDeniedException("Only team admin of this team can perform this action");
    }

    private boolean isTeamAdmin(UserAccount user, Team team) {
        return user.getTeamRoles().stream()
                .anyMatch(r -> r.getTeam().getId().equals(team.getId())
                        && r.getRole() == TeamRole.TEAM_ADMIN);
    }
}
