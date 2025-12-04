package com.cricforge.team_management.mongo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ScoreBoardData {

    private int runs;
    private int wickets;
    private int completedOvers;
    private int ballsInCurrentOver;

    private Long strikerId;
    private Long nonStrikerId;
    private Long bowlerId;
}
