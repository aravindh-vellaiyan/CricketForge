package com.cricforge.team_management.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Player {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "team_id")
    @JsonIgnore
    private Team team;

    @Column(nullable = false)
    private String firstName;

    private String lastName;
    private String role; // Batsman/Bowler etc.

    @Enumerated(EnumType.STRING)
    private PlayerType type; // CAPTAIN, VICE_CAPTAIN, NORMAL
}