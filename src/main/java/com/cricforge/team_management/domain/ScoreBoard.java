package com.cricforge.team_management.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ScoreBoard {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Match match;

    private int innings = 1;

    private int runs = 0;
    private int wickets = 0;

    private int overs = 0;
    private int balls = 0;

    private int target;  // For innings 2

    @ManyToOne
    private Player striker;

    @ManyToOne
    private Player nonStriker;

    @ManyToOne
    private Player bowler;
}