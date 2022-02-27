package com.example.game_of_three.exceptions;

public class GameNotPlayingException extends RuntimeException {
  public GameNotPlayingException(String message) {
    super(message);
  }
}
