package com.webcheckers.ui;

import java.util.logging.Logger;

import com.google.gson.Gson;
import com.webcheckers.appl.GameList;
import com.webcheckers.model.Game;

import spark.Request;
import spark.Response;
import spark.Route;

import com.webcheckers.util.Message;
import com.webcheckers.util.ReplayHelper;

/**
 * The UI Controller to GET the Next Replay page.
 *
 * @author Brandon Chen
 */
public class GetReplayNextRoute implements Route {
  private static final Logger LOG = Logger.getLogger(GetReplayNextRoute.class.getName());

  private final GameList gameList;
  private final Gson gson;
  /**
   * Create the Spark Route (UI controller) to handle all {@code GET /Replay/next} HTTP requests.
   *
   * @param templateEngine
   *   the HTML template rendering engine
   */
  public GetReplayNextRoute(final Gson gson, final GameList gamesList) {
    this.gameList = gamesList;
    this.gson = gson;
    LOG.config("GetReplayNextRoute is initialized.");
  }

  /**
   * Render the WebCheckers ReplayNext page.
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

    Integer gameID = Integer.parseInt(request.queryParams("gameID"));
    Game game = gameList.getGame(gameID);
    // System.out.println(game.getBoardRed());
    ReplayHelper rply = game.getReplayHelper();
    rply.next();
    return gson.toJson(new Message("true", Message.Type.INFO));
    
  }
}
