package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Player;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

import com.webcheckers.util.Message;

/**
 * The UI Controller to GET the Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class GetHomeRoute implements Route {
  private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

  private static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");

  private final PlayerLobby lobby;
  private final TemplateEngine templateEngine;

  /**
   * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
   *
   * @param templateEngine
   *   the HTML template rendering engine
   */
  public GetHomeRoute(final TemplateEngine templateEngine, PlayerLobby lobby) {
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    //
    LOG.config("GetHomeRoute is initialized.");
    this.lobby = lobby;
  }

  /**
   * Render the WebCheckers Home page.
   *
   * @param request
   *   the HTTP request
   * @param response
   *   the HTTP response
   *
   * @return
   *   the rendered HTML for the Home page
   */
  @Override
  public Object handle(Request request, Response response) {
    LOG.finer("GetHomeRoute is invoked.");
    //
    Map<String, Object> vm = new HashMap<>();

    Player currentPlayer = request.session().attribute("Player");


    if(currentPlayer != null){
      vm.put("currentUser", currentPlayer);
      request.session().attribute("Player",currentPlayer);
      currentPlayer = lobby.getPlayer(currentPlayer.name);
      System.out.println(currentPlayer.isInGame());
      if(currentPlayer.isInGame()){
        // Inject game information into template

        Game currentGame = currentPlayer.getGame();

        vm.put("title", "Let's Play");
        vm.put("board", currentPlayer.getBoard());
        vm.put("viewMode", "PLAY");
        vm.put("currentUser", currentPlayer);
        vm.put("redPlayer", currentGame.getRedPlayer());
        vm.put("whitePlayer", currentGame.getWhitePlayer());
        vm.put("activeColor", Piece.Color.RED);
        return templateEngine.render(new ModelAndView(vm, "game.ftl"));
      }
    }

    vm.put("title", "Welcome!");
    vm.put("allUsers",lobby.getUsernames());

    // display a user message in the Home page
    String plural = " is ";
    if(lobby.countPlayers() != 1) {
      plural = "s are ";
    }
    vm.put("message", Message.info(WELCOME_MSG.getText() + "\n" + lobby.countPlayers() + " player" + plural + "currently online!"));
    // render the View
    return templateEngine.render(new ModelAndView(vm , "home.ftl"));
  }
}
