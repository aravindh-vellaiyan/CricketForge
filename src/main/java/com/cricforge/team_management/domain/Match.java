package com.cricforge.team_management.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@Entity
public class Match {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne private Team teamA;
    @ManyToOne private Team teamB;

    private int innings = 1;

    private int totalRuns = 0;
    private int wickets = 0;

    private int balls = 0; // balls in current over (0–5)
    private int overs = 0;

    @Enumerated(EnumType.STRING)
    private MatchStatus status = MatchStatus.IN_PROGRESS;

    @ManyToOne private Player striker;
    @ManyToOne private Player nonStriker;
    @ManyToOne private Player bowler;
}