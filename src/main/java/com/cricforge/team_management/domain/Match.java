package com.cricforge.team_management.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@Entity
public class Match {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    private Team teamA;

    @ManyToOne(optional = false)
    private Team teamB;

    private LocalDateTime scheduledAt;
}