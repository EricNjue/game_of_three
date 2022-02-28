package com.example.game_of_three.models;

import com.example.game_of_three.utils.Status;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(name = "GAMES")
@NoArgsConstructor
public class Game {

  public Game(Integer number, Integer previousNumber, Integer addedNumber) {
    this.number = number;
    this.previousNumber = previousNumber;
    this.addedNumber = addedNumber;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotNull
  private Integer number;
  private Long playerOneId;
  private Long playerTwoId;
  private Long winnerId;
  private Status status;

  private Long playerPerformedLastMoveId;

  @NotNull
  private Integer previousNumber;

  @NotNull
  private Integer addedNumber;

}
