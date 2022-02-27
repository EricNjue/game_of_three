package com.example.game_of_three.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "PLAYERS")
public class Player {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "PLAYER_NAME")
  private String name;

  @Column(name = "GAME_ID")
  private Long gameId;

}
