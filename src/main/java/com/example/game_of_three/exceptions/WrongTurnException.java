package com.example.game_of_three.exceptions;

public class WrongTurnException extends RuntimeException {
  public WrongTurnException(String message) {
    super(message);
  }
}
