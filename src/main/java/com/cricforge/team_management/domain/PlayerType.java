package com.cricforge.team_management.domain;

public enum PlayerType {
    CAPTAIN,
    VICE_CAPTAIN,
    NORMAL;

    public static String getPlayerType(PlayerType playerType) {
        return playerType != null ? playerType.name() : NORMAL.name();
    }
}