package com.example.game_of_three.exceptions;

public class GameAlreadyStartedException extends RuntimeException {
  public GameAlreadyStartedException(String message) {
    super(message);
  }
}
