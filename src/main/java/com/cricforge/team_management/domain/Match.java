package com.cricforge.team_management.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Match {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne private Team teamA;
    @ManyToOne private Team teamB;

    private int maxOvers;     // metadata
    private LocalDateTime startTime;

    @Enumerated(EnumType.STRING)
    private MatchStatus status = MatchStatus.NOT_STARTED;

    // Pre-match setup
    @ElementCollection
    private List<Long> teamAPlayers; // Playing XI IDs

    @ElementCollection
    private List<Long> teamBPlayers;
}