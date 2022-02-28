package com.example.game_of_three.service.impl;

import com.example.game_of_three.dto.CreateGameRequest;
import com.example.game_of_three.exceptions.*;
import com.example.game_of_three.models.Game;
import com.example.game_of_three.models.Player;
import com.example.game_of_three.repository.GameRepository;
import com.example.game_of_three.service.GameService;
import com.example.game_of_three.service.PlayerService;
import com.example.game_of_three.utils.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class GameServiceImpl implements GameService {

  private final PlayerService playerService;
  private final GameRepository gameRepository;

  public GameServiceImpl(PlayerService playerService, GameRepository gameRepository) {
    this.playerService = playerService;
    this.gameRepository = gameRepository;
  }

  @Override
  public List<Game> listGames() {
    log.info("Fetching list of games ...");
    return gameRepository.findAll();
  }

  @Override
  public Game create(Game game) {
    log.info("Creating a new game ...");
    game.setStatus(Status.NEW);
    gameRepository.save(game);

    return game;
  }

  @Override
  public Game applyToGame(Player player, Long gameId) {
    validatePlayer(player);
    Game game = gameRepository.getById(gameId);
    validateGameForApplying(gameId, game);

    Player firstPlayer = playerService.getPlayer(game.getPlayerOneId());
    if (firstPlayer == null) {
      game.setPlayerOneId(player.getId());
      game.setStatus(Status.WAITING_FOR_OPPONENT);
      game.setPlayerPerformedLastMoveId(player.getId());
      return game;
    }

    Player secondPlayer = playerService.getPlayer(game.getPlayerTwoId());

    if (secondPlayer == null) {
      game.setPlayerOneId(player.getId());
      game.setStatus(Status.PLAYING);
    }
    return game;
  }

  @Override
  public Game move(Game updatedGame, Long playerId, Long gameId) {
    Game savedGame = getGameHelper(gameId);
    if (savedGame == null)
      throw new GameNotFoundException(String.format("Game '%s' not found", gameId));

    if (savedGame.getStatus() == Status.FINISHED) {
      delete(savedGame.getId());
      return savedGame;
    }

    if (savedGame.getStatus() != Status.PLAYING) {
      throw new GameNotPlayingException("Game can't be played: opponent absent or game finished");
    }

    Long playerOneId = savedGame.getPlayerOneId();
    Long playerTwoId = savedGame.getPlayerTwoId();

    if ((!Objects.equals(playerId, playerOneId)) && (!Objects.equals(playerId, playerTwoId))) {
      throw new PlayerNotAllowedException("Game is played by other players");
    }

    if (savedGame.getPlayerPerformedLastMoveId().equals(playerId)) {
      throw new WrongTurnException("Please, wait for another player to move");
    }

    Integer number = updatedGame.getNumber();

    savedGame.setNumber(number);
    savedGame.setPreviousNumber(updatedGame.getPreviousNumber());
    savedGame.setAddedNumber(updatedGame.getAddedNumber());
    savedGame.setPlayerPerformedLastMoveId(playerId);

    if (1 == number) {
      savedGame.setStatus(Status.FINISHED);
    }

    return savedGame;

  }

  @Override
  public Game getGame(Long gameId) {
    Game game = getGameHelper(gameId);
    if (game == null) {
      throw new GameNotFoundException(String.format("Game '%s' not found", gameId));
    }
    return game;
  }

  @Override
  public void delete(Long gameId) {
    Game game = getGameHelper(gameId);

    if (game == null) {
      throw new GameNotFoundException(String.format("Game '%s' not found", gameId));
    }
    gameRepository.delete(game);
  }

  private void validatePlayer(Player player) {
    if (playerService.getPlayer(player.getId()) == null) {
      throw new UserNotRegisteredException(String.format("User '%s' not registered", player.getName()));
    }
  }

  private void validateGameForApplying(Long gameId, Game game) {
    if (game == null) {
      throw new GameNotFoundException(String.format("Game '%d' not found", gameId));
    }
    Status status = game.getStatus();
    if (!status.canApplyToGame()) {
      throw new GameAlreadyStartedException(String.format("Game '%d' already started", gameId));
    }
  }

  private Game getGameHelper(Long id) {
    return gameRepository.findById(id).orElse(null);
  }
}
