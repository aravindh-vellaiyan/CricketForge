package com.cricforge.team_management.dto;

public record BallUpdateRequest(
        int runs,
        boolean wicket,
        String extraType  // null or WIDE, NO_BALL, LEG_BYE, BYE
) {}