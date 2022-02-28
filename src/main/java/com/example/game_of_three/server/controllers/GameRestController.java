package com.example.game_of_three.server.controllers;

import com.example.game_of_three.service.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameRestController {
  private final GameService gameService;

  public GameRestController(GameService gameService) {
    this.gameService = gameService;
  }

  @RequestMapping(value = "/games", method = RequestMethod.GET)
  public ResponseEntity<Object> getAllGames() {
    return new ResponseEntity<>(gameService.listGames(), HttpStatus.OK);
  }

  @RequestMapping(value = "/games/{gameId}", method = RequestMethod.GET)
  public ResponseEntity<Object> getGameById(@PathVariable("gameId") Long gameId) {
    return new ResponseEntity<>(gameService.getGame(gameId), HttpStatus.OK);
  }

  @RequestMapping(value = "/games/{gameId}", method = RequestMethod.DELETE)
  public ResponseEntity<Object> deleteGame(@PathVariable("gameId") Long gameId) {
    gameService.delete(gameId);
    return new ResponseEntity<>("Game deleted successfully", HttpStatus.OK);
  }

}
