package com.cricforge.team_management.domain;

import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "matches")
public class MatchDocument {
    @Id
    private String id;
    private int teamA;
    private int teamB;
    private MatchMetadata metadata;
    private ScoreBoardData scoreboard;
    private List<BallEvent> balls;
    private String status;
}