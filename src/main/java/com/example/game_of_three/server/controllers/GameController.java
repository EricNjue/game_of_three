package com.example.game_of_three.server.controllers;

import com.example.game_of_three.dto.GameMoveRequest;
import com.example.game_of_three.dto.GameMoveResponse;
import com.example.game_of_three.utils.GameUtilities;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GameController {

  /***
   * Handling messages routed to the “/app/message” destination, by prefixing them and returning them to the “/topic/reply” destination,
   * broadcasting them to all connected clients.
   * @param message
   * @return
   */
  @MessageMapping("/message")
  @SendTo("/topic/reply")
  public GameMoveResponse replyToMessageFromClient(@Payload GameMoveRequest message) {

    // user has made a move ...
    // compute the next step ... and return the response ...
    // if not winner, let the computer make a move


    //String next = GameUtilities.processNextNumber(message);
    return new GameMoveResponse(String.format("Hello, %s!!!", HtmlUtils.htmlEscape(message.getName())));
  }

  /***
   * Handling messages routed to the “/app/whisper” destination, only replying to the connected sender on the “/user/queue/reply” destination,
   * even though multiple clients can be connected to this destination.
   * @param message
   * @return
   */
  @MessageMapping("/whisper")
  @SendToUser("/queue/reply")
  public String replyToWhisperFromClient(@Payload String message) {
    return String.format("Replying to whisper: %s", message);
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
