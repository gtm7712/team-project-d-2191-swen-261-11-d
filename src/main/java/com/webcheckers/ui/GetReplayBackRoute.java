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
public class GetReplayBackRoute implements Route {
  private static final Logger LOG = Logger.getLogger(GetReplayBackRoute.class.getName());

  private final GameList gameList;
  private final Gson gson;
  /**
   * Create the Spark Route (UI controller) to handle all {@code GET /Replay/previousTurn} HTTP requests.
   *
   * @param gson
   *   the gson used for ajax calls
   * @param gameList
   *  the list of games
   */
  public GetReplayBackRoute(final Gson gson, final GameList gameList) {
    this.gameList = gameList;
    this.gson = gson;
    LOG.config("GetReplayBackRoute is initialized.");
  }

  /**
   * Send the correct ajax cal
   *
   * @param request
   *   the HTTP request
   * @param response
   *   the HTTP response
   *
   * @return
   *   the ajax call
   */
  @Override
  public Object handle(Request request, Response response) {
    
    Integer gameID = Integer.parseInt(request.queryParams("gameID"));
    Game game = gameList.getGame(gameID);
    ReplayHelper rply = game.getReplayHelper();
    rply.previous();
    return gson.toJson(new Message("true", Message.Type.INFO));
    
  }
}
