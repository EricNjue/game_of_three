package com.example.game_of_three.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "GAME_MOVES")
public class GameMove {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private Long gameId;
  private Long playerId;
  private String moveDescription;
  private Integer moveValue;
}
