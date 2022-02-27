package com.example.game_of_three.models;

import com.example.game_of_three.utils.Status;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "GAMES")
public class Game {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private Integer number;
  private Long playerOneId;
  private Long playerTwoId;
  private Long winnerId;
  private Status status;

  private Long playerPerformedLastMoveId;
  private Integer previousNumber;
  private Integer addedNumber;

}
