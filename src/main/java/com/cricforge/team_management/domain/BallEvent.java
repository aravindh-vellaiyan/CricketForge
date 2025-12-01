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
    private ScoreBoard scoreBoard;

    private int overNumber;
    private int ballNumber;

    private int runs;
    private boolean wicket;

    private boolean extra;
    private String extraType;
}