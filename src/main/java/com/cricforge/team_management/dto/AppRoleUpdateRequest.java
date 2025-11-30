package com.cricforge.team_management.dto;

import com.cricforge.team_management.domain.Role;

public record AppRoleUpdateRequest (
        Role newRole
) {}
