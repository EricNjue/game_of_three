package com.example.game_of_three.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker // enabling broker-backed messaging over a WebSocket connection
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  private static final String DEMO_ENDPOINT = "/websocketdemo";
  private static final String GAME_MOVE_ENDPOINT = "/make-move";

  private static final String TOPIC_DESTINATION = "/topic";
  private static final String QUEUE_DESTINATION = "/queue/";
  private static final String DESTINATION_PREFIX = "/app";

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint(DEMO_ENDPOINT, GAME_MOVE_ENDPOINT).withSockJS();
  }

  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    registry.enableSimpleBroker(TOPIC_DESTINATION, QUEUE_DESTINATION);
    registry.setApplicationDestinationPrefixes(DESTINATION_PREFIX);
  }
}
