package com.example.game_of_three.server.controllers;

import com.example.game_of_three.models.Game;
import com.example.game_of_three.models.Player;
import com.example.game_of_three.service.GameService;
import com.sun.istack.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@RequestMapping(value = "/games")
public class GameRestController {
  private final GameService gameService;

  public GameRestController(GameService gameService) {
    this.gameService = gameService;
  }

  @RequestMapping(method = RequestMethod.GET)
  public ResponseEntity<Object> getAllGames() {
    return new ResponseEntity<>(gameService.listGames(), HttpStatus.OK);
  }

  @RequestMapping(value = "/{gameId}", method = RequestMethod.GET)
  public ResponseEntity<Object> getGameById(@PathVariable("gameId") Long gameId) {
    return new ResponseEntity<>(gameService.getGame(gameId), HttpStatus.OK);
  }

  @RequestMapping(value = "/{gameId}", method = RequestMethod.DELETE)
  public ResponseEntity<Object> deleteGame(@PathVariable("gameId") Long gameId) {
    gameService.delete(gameId);
    return new ResponseEntity<>("Game deleted successfully", HttpStatus.OK);
  }

  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<Object> createGame(@RequestBody @Valid Game game) {
    return new ResponseEntity<>(gameService.create(game), HttpStatus.OK);
  }

  @RequestMapping(path = "/{gameId}/players/{playerId}", method = RequestMethod.PUT)
  public ResponseEntity<Object> move(@PathVariable("playerId") @NotNull Long playerId,
                                     @PathVariable("gameId") @NotNull Long gameId,
                                     @RequestBody @Valid Game game) {
    return new ResponseEntity<>(gameService.move(game, playerId, gameId), HttpStatus.ACCEPTED);
  }

  @RequestMapping(path = "/{gameId}/players/{playerId}", method = RequestMethod.POST)
  public ResponseEntity<Object> applyToGame(@PathVariable("playerId") @NotNull @Min(1) Long playerId,
                                            @PathVariable("gameId") @NotNull @Min(1) Long gameId,
                                            @RequestBody @Valid Player player) {
    return new ResponseEntity<>(gameService.applyToGame(player, gameId), HttpStatus.ACCEPTED);
  }

}
