package com.cricforge.team_management.repository;

import com.cricforge.team_management.domain.TeamRole;
import com.cricforge.team_management.domain.UserTeamRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTeamRoleRepository extends JpaRepository<UserTeamRole, Long> {
    boolean existsByUserIdAndTeamIdAndRole(Long userId, Long teamId, TeamRole teamRole);
}