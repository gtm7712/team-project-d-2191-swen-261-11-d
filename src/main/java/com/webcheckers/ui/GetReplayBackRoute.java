package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.webcheckers.appl.GameList;
import com.webcheckers.appl.ReplayList;
import com.webcheckers.model.Game;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

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
   * Create the Spark Route (UI controller) to handle all {@code GET /Replay/next} HTTP requests.
   *
   * @param templateEngine
   *   the HTML template rendering engine
   */
  public GetReplayBackRoute(final Gson gson, final GameList gameList) {
    this.gameList = gameList;
    this.gson = gson;
    LOG.config("GetReplayBackRoute is initialized.");
  }

  /**
   * Render the WebCheckers ReplayBack page.
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
    
    Integer gameID = Integer.parseInt(request.queryParams("gameID"));
    Game game = gameList.getGame(gameID);
    ReplayHelper rply = game.getReplayHelper();
    rply.previous();
    return gson.toJson(new Message("True", Message.Type.INFO));
    
  }
}
