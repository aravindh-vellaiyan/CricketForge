package com.cricforge.team_management.service;

import com.cricforge.team_management.domain.*;
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

    public boolean canScoreMatch(UserAccount user, Match match) {
        if (user.getRole() == Role.APP_ADMIN) return true;

        boolean teamAAdmin = isTeamAdmin(user, match.getTeamA());
        boolean teamBAdmin = isTeamAdmin(user, match.getTeamB());

        return teamAAdmin || teamBAdmin;
    }
}
