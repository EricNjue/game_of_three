package com.example.game_of_three.server.controllers;

import com.example.game_of_three.dto.ErrorResponse;
import com.example.game_of_three.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
  private final ErrorResponse response;

  public ControllerAdvisor(ErrorResponse errorResponse) {
    this.response = errorResponse;
  }

  @ExceptionHandler({GameNotFoundException.class})
  public final ResponseEntity<Object> handleGameNotFoundException(Exception ex) {
    response.setDescription(ex.getLocalizedMessage());
    response.setMessage("Game Not Found");
    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler({GameAlreadyStartedException.class})
  public final ResponseEntity<Object> handleGameAlreadyStartedException(Exception ex) {
    response.setDescription(ex.getLocalizedMessage());
    response.setMessage("Game Already Started");
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({GameNotPlayingException.class})
  public final ResponseEntity<Object> handleGameNotPlayingException(Exception ex) {
    response.setDescription(ex.getLocalizedMessage());
    response.setMessage("Invalid Request");
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({PlayerNotAllowedException.class})
  public final ResponseEntity<Object> handlePlayerNotAllowedException(Exception ex) {
    response.setDescription(ex.getLocalizedMessage());
    response.setMessage("Invalid Player");
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({UserNotRegisteredException.class})
  public final ResponseEntity<Object> handleUserNotRegisteredException(Exception ex) {
    response.setDescription(ex.getLocalizedMessage());
    response.setMessage("Invalid Player");
    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler({WrongTurnException.class})
  public final ResponseEntity<Object> handleWrongTurnException(Exception ex) {
    response.setDescription(ex.getLocalizedMessage());
    response.setMessage("Bad Move");
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }
}
