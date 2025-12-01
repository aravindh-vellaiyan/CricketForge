package com.cricforge.team_management.service;

import com.cricforge.team_management.domain.*;
import com.cricforge.team_management.exception.AccessDeniedException;
import com.cricforge.team_management.repository.UserTeamRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    @Autowired
    private UserTeamRoleRepository userTeamRoleRepository;

    public void requireAppAdmin(UserAccount user) {
        if (user.getRole() != Role.APP_ADMIN) {
            throw new AccessDeniedException("Only APP_ADMIN can perform this action");
        }
    }

    public void requireTeamAdmin(UserAccount user, Team team) {
        if (user.getRole() == Role.APP_ADMIN || isTeamAdmin(user.getId(), team.getId())) return;
        throw new AccessDeniedException("Only team admin of this team can perform this action");
    }

    private boolean isTeamAdmin(Long userId, Long teamId) {
        return userTeamRoleRepository.existsByUserIdAndTeamIdAndRole(
                userId, teamId, TeamRole.TEAM_ADMIN
        );
    }

    public boolean canScoreMatch(UserAccount user, Match match) {
        if (user.getRole() == Role.APP_ADMIN) return true;

        boolean teamAAdmin = isTeamAdmin(user.getId(), match.getTeamA().getId());
        boolean teamBAdmin = isTeamAdmin(user.getId(), match.getTeamB().getId());

        return teamAAdmin || teamBAdmin;
    }
}
