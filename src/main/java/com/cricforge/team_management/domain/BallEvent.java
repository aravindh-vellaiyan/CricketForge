package com.cricforge.team_management.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class BallEvent {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Match match;

    private int runs;          // 0–6 (including boundaries)
    private boolean isWicket;
    private boolean isExtra;
    private String extraType;  // WIDE, NO_BALL, BYE, LEG_BYE

    private int overNumber;
    private int ballNumber;
}