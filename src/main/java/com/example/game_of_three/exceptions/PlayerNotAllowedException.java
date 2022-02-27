package com.example.game_of_three.exceptions;

public class PlayerNotAllowedException extends RuntimeException {
  public PlayerNotAllowedException(String message) {
    super(message);
  }
}
