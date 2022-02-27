package com.example.game_of_three.exceptions;

public class UserNotRegisteredException extends RuntimeException {
  public UserNotRegisteredException(String message) {
    super(message);
  }
}
