package com.cricforge.team_management.domain;

public enum Role {
    USER,
//    TEAM_ADMIN,
    APP_ADMIN;

    public static String getRole(Role role) {
        return role != null ? role.name() : USER.name();
    }
}
