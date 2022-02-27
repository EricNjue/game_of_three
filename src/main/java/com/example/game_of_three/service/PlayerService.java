package com.example.game_of_three.service;

import com.example.game_of_three.models.Player;

public interface PlayerService {
  Player registerPlayer(Player player);

  Player getPlayer(Long id);

  Player getPlayerByName(String name);
}
