package com.example.game_of_three;

import com.example.game_of_three.dto.ErrorResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GameOfThreeApplication {

  public static void main(String[] args) {
    SpringApplication.run(GameOfThreeApplication.class, args);
  }


  @Bean
  public ErrorResponse getErrorResponse() {
    return new ErrorResponse();
  }
}
