package com.example.game_of_three.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "PLAYERS")
public class Player {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "PLAYER_NAME")
  @NotNull
  private String name;
}
