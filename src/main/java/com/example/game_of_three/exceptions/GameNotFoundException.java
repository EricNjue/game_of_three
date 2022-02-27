package com.example.game_of_three.exceptions;

public class GameNotFoundException extends RuntimeException {
  public GameNotFoundException(String message) {
    super(message);
  }
}
