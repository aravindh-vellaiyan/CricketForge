package com.cricforge.team_management.dto;

import com.cricforge.team_management.domain.TeamRole;

public record TeamRoleUpdateRequest (
    TeamRole newRole
){}
