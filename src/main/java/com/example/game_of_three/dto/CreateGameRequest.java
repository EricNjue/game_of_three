package com.example.game_of_three.dto;

import com.example.game_of_three.models.Game;
import lombok.Data;

@Data
public class CreateGameRequest {
  private String playerName;
  private Game game;
}
