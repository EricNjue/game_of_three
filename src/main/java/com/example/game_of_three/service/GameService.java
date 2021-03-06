package com.example.game_of_three.service;

import com.example.game_of_three.models.Game;
import com.example.game_of_three.models.Player;

import java.util.List;

public interface GameService {
  List<Game> listGames();

  Game create(Game game);

  Game applyToGame(Player player, Long gameId);

  Game move(Game updatedGame, Long playerId, Long gameId);

  Game getGame(Long gameId);

  void delete(Long gameId);
}
