package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.appl.ReplayList;
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
public class GetReplayRoute implements Route {
  private static final Logger LOG = Logger.getLogger(GetSigninRoute.class.getName());

  private final TemplateEngine templateEngine;
  private final ReplayList gamesList;
  /**
   * Create the Spark Route (UI controller) to handle all {@code GET /signin} HTTP requests.
   *
   * @param templateEngine
   *   the HTML template rendering engine
   */
  public GetReplayRoute(final TemplateEngine templateEngine, ReplayList gamesList) {
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    this.gamesList=gamesList;
    LOG.config("GetReplayRoute is initialized.");
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
    LOG.finer("GetReplayRoute is invoked.");
    //
    Map<String, Object> vm = new HashMap<>();
    vm.put("title", "Load a Replay");
    vm.put("allGames", gamesList.getGames());
    // render the View
    return templateEngine.render(new ModelAndView(vm , "replaytest.ftl"));
  }
}
