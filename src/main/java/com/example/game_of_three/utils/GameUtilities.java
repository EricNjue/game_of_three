package com.example.game_of_three.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GameUtilities {
  /**
   * The method processes the number received, prints appropriate messages
   * and returns the next number to be sent to another player
   *
   * @param message - number received
   * @return - next number to be sent
   */
  public static String processNextNumber(String message) {

    if (message.equals(Constants.DONE_KEYWORD)) {
      log.info(Constants.GAME_OVER);
      return Constants.SUCCESS_STR;
    }

    int current = Integer.parseInt(message);
    log.info("Received number: " + current);

    if (current == 1) {
      log.info(Constants.WINNER);
      return Constants.DONE_KEYWORD;
    } else if (current <= 0) {
      log.info(Constants.ERROR);
      return Constants.FAILURE_STR;
    }

    String operation = getOperationPerformed(current);
    log.info(operation);
    String next = Long.toString(Math.round(((double) current) / 3));
    log.info(String.format("Sending number: %s", next));

    return next;
  }

  /**
   * Returns the operation performed on the number received by the player
   *
   * @param current
   */
  public static String getOperationPerformed(int current) {

    String operation = "";
    if ((current + 1) % 3 == 0) {
      operation = "Added 1";
    } else if ((current - 1) % 3 == 0) {
      operation = "Subtracted 1";
    } else if (current % 3 == 0) {
      operation = "Added 0";
    }

    return operation;
  }
}
