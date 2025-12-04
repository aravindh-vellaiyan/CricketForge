package com.cricforge.team_management.mongo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BallEventData {

    private int over;
    private int ball;
    private int runs;
    private boolean wicket;
    private String commentary;
    private Long batsmanId;
    private Long bowlerId;
}