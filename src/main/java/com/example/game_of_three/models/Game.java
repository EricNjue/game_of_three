package com.example.game_of_three.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "GAMES")
public class Game {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private Long playerOneId;
  private Long playerTwoId;
  private Long winnerId;
}
