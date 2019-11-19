package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

import com.google.gson.Gson;
import com.webcheckers.model.Board;
import com.webcheckers.model.Game;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import com.webcheckers.util.ReplayHelper;

/**
 * The UI Controller to GET the Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class GetReplayGameRoute implements Route {
  private static final Logger LOG = Logger.getLogger(GetSigninRoute.class.getName());

  private final TemplateEngine templateEngine;
  private final Gson gson;

  /**
   * Create the Spark Route (UI controller) to handle all {@code GET /signin} HTTP requests.
   *
   * @param templateEngine
   *   the HTML template rendering engine
   */
  public GetReplayGameRoute(final TemplateEngine templateEngine, Gson gson) {
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    this.gson = Objects.requireNonNull(gson, "gson is required");
    //
    LOG.config("GetReplayGameRoute is initialized.");
  }

  /**
   * Render the WebCheckers Sign-in page.
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
    LOG.finer("GetReplayGameRoute is invoked.");
    //
    Map<String, Object> vm = new HashMap<>();

    Map<String, Object> modeOptions = new HashMap<>();

    Player currentPlayer = new Player("You.");
    Player opponent = new Player("Opponent.");

    Game rpyGame = new Game();

    // ReplayHelper rpyHelper = rpyGame.getReplayHelper();
    ReplayHelper rpyHelper = new ReplayHelper();
    rpyHelper.loadReplay("John;Mary;+w01:-10;-01:+R03");

    currentPlayer.setOpponent(opponent); // TODO
    opponent.setOpponent(currentPlayer); // TODO?

    rpyGame.setRedPlayer(currentPlayer); // TODO
    rpyGame.setWhitePlayer(opponent); // TODO
    currentPlayer.setGame(rpyGame);
    opponent.setGame(rpyGame);

    modeOptions.put("hasNext", rpyHelper.canGoForward());
    modeOptions.put("hasPrevious", rpyHelper.canGoBack());

    vm.put("modeOptionsAsJSON", gson.toJson(modeOptions));

    vm.put("title", "Replay");
    vm.put("activeColor", Piece.Color.RED);
    vm.put("viewMode", "REPLAY");
    vm.put("currentUser", currentPlayer);
    vm.put("redPlayer" , currentPlayer); // TODO: GET RED PLAYER
    vm.put("whitePlayer", opponent); // TODO: Get WHITE PLAYER
    vm.put("board", new Board()); // TODO: GET BOARD

    // render the View
    return templateEngine.render(new ModelAndView(vm , "game.ftl"));
  }
}
