package com.example.game_of_three.service.impl;

import com.example.game_of_three.models.Player;
import com.example.game_of_three.repository.PlayerRepository;
import com.example.game_of_three.service.PlayerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PlayerServiceImpl implements PlayerService {

  private final PlayerRepository playerRepository;

  public PlayerServiceImpl(PlayerRepository playerRepository) {
    this.playerRepository = playerRepository;
  }

  @Override
  public Player registerPlayer(Player player) {
    return playerRepository.save(player);
  }

  @Override
  public Player getPlayer(Long id) {
    return playerRepository.findById(id).orElse(null);
  }

  @Override
  public Player getPlayerByName(String name) {
    return playerRepository.findFirstByName(name);
  }
}
