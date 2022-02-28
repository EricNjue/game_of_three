package com.example.game_of_three.server.controllers;

import com.example.game_of_three.dto.GameMoveRequest;
import com.example.game_of_three.dto.GameMoveResponse;
import com.example.game_of_three.service.GameService;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.util.Random;

@Controller
public class GameController {

  private final GameService gameService;

  public GameController(GameService gameService) {
    this.gameService = gameService;
  }

  /***
   * Handling messages routed to the “/app/message” destination, by prefixing them and returning them to the “/topic/reply” destination,
   * broadcasting them to all connected clients.
   * @param message
   * @return
   */
  @MessageMapping("/message")
  @SendTo("/topic/reply")
  public GameMoveResponse replyToMessageFromClient(@Payload GameMoveRequest message) {

    Random random = new Random();
    Integer randomNumber = random.nextInt(100);
    while (randomNumber < 2) {
      randomNumber = random.nextInt(100);
    }


    //String next = GameUtilities.processNextNumber(message);
    return new GameMoveResponse(String.format("Hello, %s!!!", HtmlUtils.htmlEscape(message.getName())));
  }


  /***
   * Handling any exceptions from the @MessageMapping methods and sending them to the “/queue/errors” destination.
   * @param exception
   * @return
   */
  @MessageExceptionHandler
  @SendTo("/queue/errors")
  public String handleException(Throwable exception) {
    return exception.getMessage();
  }
}
