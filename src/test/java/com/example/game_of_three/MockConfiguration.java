package com.example.game_of_three;

import com.example.game_of_three.service.GameService;
import com.example.game_of_three.service.PlayerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import static org.mockito.Mockito.mock;

@Configuration
public class MockConfiguration {
  private GameService gameService = mock(GameService.class);
  private PlayerService playerService = mock(PlayerService.class);

  @Bean
  @Primary
  GameService gameService() {
    return gameService;
  }

  @Bean
  @Primary
  PlayerService playerService() {
    return playerService;
  }
}
