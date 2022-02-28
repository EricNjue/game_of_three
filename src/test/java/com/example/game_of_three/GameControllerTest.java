package com.example.game_of_three;

import com.example.game_of_three.models.Game;
import com.example.game_of_three.service.GameService;
import com.example.game_of_three.utils.Status;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = {GameOfThreeApplication.class, MockConfiguration.class})
public class GameControllerTest {

  public static final Long GAME_ID = 24L;
  public static final Long PLAYER_ID = 32L;
  public static final Integer START_NUMBER = 15;
  public static final Integer ANOTHER_NUMBER = 5;

  @Autowired
  private WebApplicationContext context;
  @Autowired
  private MockConfiguration mockConfiguration;

  private MockMvc mockMvc;

  @Before
  public void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    reset(getGameService());
  }

  @Test
  public void shouldAcceptJsonHeader() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/games").accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk());
  }

  @Test
  public void shouldRespondWithContentTypeVersion() throws Exception {
    MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/games")
            .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    assertEquals(mvcResult.getResponse().getContentType(), MediaType.APPLICATION_JSON_VALUE);
  }

  @Test
  public void shouldNotAcceptWithOtherHeader() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/games").accept(MediaType.APPLICATION_ATOM_XML_VALUE))
            .andExpect(status().is4xxClientError());
  }

  @Test
  public void shouldNotValidateGameWithoutStartNumber() throws Exception {
    mockMvc.perform(post("/games")
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(newGameWithoutNumberAsJson()))
            .andExpect(status().isBadRequest());
  }

  @Test
  public void shouldSaveNewGame() throws Exception {
    when(getGameService().create(any(Game.class))).thenReturn(gameWithId(START_NUMBER));

    MvcResult mvcResult = mockMvc.perform(post("/games")
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(newGameAsJson())).andReturn();

    int status = mvcResult.getResponse().getStatus();
    assertEquals(200, status);

    String content = mvcResult.getResponse().getContentAsString();
    Game game = mapFromJson(content, Game.class);
    assertEquals(GAME_ID, game.getId());
    assertEquals(game.getNumber(), START_NUMBER);
  }

  private Game gameWithId(int startNumber) {
    Game game = new Game(startNumber, null, null);
    game.setId(GAME_ID);
    return game;
  }

  private String newGameAsJson() {
    return "{\"id\":null,\"number\":15,\"addedNumber\":0,\"previousNumber\":0,\"firstPlayer\":null,\"secondPlayer\":null,\"status\":null}";
  }

  private String newGameWithoutNumberAsJson() {
    return "{\"id\":null,\"number\":null,\"firstPlayer\":null,\"secondPlayer\":null,\"status\":null}";
  }

  @Test
  public void shouldLoadListOfGames() throws Exception {
    when(getGameService().listGames()).thenReturn(listOfGames());

    MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/games").accept(MediaType.APPLICATION_JSON_VALUE)
            .param("statuses", Status.NEW.name())).andReturn();

    int status = mvcResult.getResponse().getStatus();
    assertEquals(200, status);

    String content = mvcResult.getResponse().getContentAsString();
    Game[] game = mapFromJson(content, Game[].class);

    assertEquals(GAME_ID, game[0].getId());
  }

  @Test
  public void shouldLoadGamesWithAllStatuses() throws Exception {
    when(getGameService().listGames()).thenReturn(null);

    mockMvc.perform(MockMvcRequestBuilders.get("/games").accept(MediaType.APPLICATION_JSON_VALUE));

    verify(getGameService()).listGames();
  }

  @Test
  public void shouldLoadGame() throws Exception {
    when(getGameService().getGame(GAME_ID)).thenReturn(gameWithId(13));


    MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/games/{gameId}", GAME_ID)
            .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

    int status = mvcResult.getResponse().getStatus();
    assertEquals(200, status);

    String content = mvcResult.getResponse().getContentAsString();
    Game game = mapFromJson(content, Game.class);
    assertEquals(GAME_ID, game.getId());
    assertEquals(13, (int) game.getNumber());
  }

  @Test
  public void shouldReturnAcceptedWhenApplyToGame() throws Exception {
    mockMvc.perform(post("/games/{gameId}/players/{playerId}", GAME_ID, PLAYER_ID)
                    .content(playerAsJson())
                    .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isAccepted());
  }

  @Test
  public void shouldValidatePlayer() throws Exception {
    mockMvc.perform(post("/games/{gameId}/players/{playerId}", GAME_ID, PLAYER_ID)
                    .content(playerWithoutNameAsJson())
                    .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());
  }

  @Test
  public void shouldValidateApplyInCaseGameIdNotDefined() throws Exception {
    mockMvc.perform(post("/games/{gameId}/players/{playerId}", "", PLAYER_ID)
                    .content(playerAsJson())
                    .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().is4xxClientError());
  }

  @Test
  public void shouldValidateApplyInCasePlayerIdNotDefined() throws Exception {
    mockMvc.perform(post("/games/{gameId}/players/{playerId}", GAME_ID, "")
                    .content(playerAsJson())
                    .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().is4xxClientError());
  }

  @Test
  public void shouldValidateMoveInCasePlayerIdNotDefined() throws Exception {
    mockMvc.perform(put("/games/{gameId}/players/{playerId}", GAME_ID, "")
                    .content(newGameAsJson())
                    .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().is4xxClientError());
  }

  @Test
  public void shouldValidateMoveInCaseGameIdNotDefined() throws Exception {
    mockMvc.perform(put("/games/{gameId}/players/{playerId}", "", PLAYER_ID)
                    .content(newGameAsJson())
                    .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().is4xxClientError());
  }

  @Test
  public void shouldValidateMoveInCaseGameWithoutNumber() throws Exception {
    mockMvc.perform(put("/games/{gameId}/players/{playerId}", GAME_ID, PLAYER_ID)
                    .content(newGameWithoutNumberAsJson())
                    .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().is4xxClientError());
  }

  @Test
  public void shouldRespondAcceptedOnMoveIfSuccess() throws Exception {
    mockMvc.perform(put("/games/{gameId}/players/{playerId}", GAME_ID, PLAYER_ID)
                    .content(newGameAsJson())
                    .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isAccepted());
  }

  @Test
  public void shouldReturnUpdatedModel() throws Exception {
    when(getGameService().move(any(Game.class), eq(PLAYER_ID), eq(GAME_ID))).thenReturn(gameWithId(ANOTHER_NUMBER));

    MvcResult mvcResult = mockMvc.perform(put("/games/{gameId}/players/{playerId}", GAME_ID, PLAYER_ID)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(newGameAsJson())).andReturn();

    int status = mvcResult.getResponse().getStatus();
    assertEquals(202, status);

    String content = mvcResult.getResponse().getContentAsString();
    Game game = mapFromJson(content, Game.class);

    assertEquals(game.getId(), GAME_ID);
    assertEquals(game.getNumber(), ANOTHER_NUMBER);
  }

  private String playerAsJson() {
    return "{\"name\":\"user name\",\"id\":90}";
  }

  private String playerWithoutNameAsJson() {
    return "{\"name\":null,\"id\":89}";
  }

  private GameService getGameService() {
    return mockConfiguration.gameService();
  }

  private List<Game> listOfGames() {
    Game game = new Game(START_NUMBER, null, null);
    game.setId(GAME_ID);
    return Collections.singletonList(game);
  }

  protected String mapToJson(Object obj) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.writeValueAsString(obj);
  }

  protected <T> T mapFromJson(String json, Class<T> clazz)
          throws IOException {

    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.readValue(json, clazz);
  }
}
