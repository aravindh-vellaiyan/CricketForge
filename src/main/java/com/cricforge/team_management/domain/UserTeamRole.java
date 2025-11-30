package com.cricforge.team_management.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class UserTeamRole {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private UserAccount user;

    @ManyToOne
    private Team team;

    @Enumerated(EnumType.STRING)
    private TeamRole role;
}