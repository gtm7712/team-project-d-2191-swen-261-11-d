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

import com.webcheckers.util.Message;

/**
 * The UI Controller for start game.
 *
 * @author Kyle Collins
 */
public class GetStartGameRoute implements Route {

    private static final Logger LOG = Logger.getLogger(GetStartGameRoute.class.getName());
    private final TemplateEngine templateEngine;
  
    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public GetStartGameRoute(final TemplateEngine templateEngine) {
      this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
      //
      LOG.config("GetStartGameRoute is initialized.");
    }
  
    /**
     * Render the web checkers start game
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
      LOG.finer("GetStartGameRoute is invoked.");
      //
      Map<String, Object> vm = new HashMap<>();

      // Inject game information into template
      vm.put("title", "");
      vm.put("message", "");
  
      // render the View
      return templateEngine.render(new ModelAndView(vm , "game.ftl"));
    }
  }