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
 * The UI Controller to GET the Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class GetSigninRoute implements Route {
  private static final Logger LOG = Logger.getLogger(GetSigninRoute.class.getName());

  private static final Message SELECT = Message.info("Please choose a username.");
  private static final Message INVALID = Message.info("That username is invalid.  Usernames must be alphanumeric.");
  private static final Message IN_USE = Message.info("That username is already in use..");

  private final TemplateEngine templateEngine;

  /**
   * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
   *
   * @param templateEngine
   *   the HTML template rendering engine
   */
  public GetSigninRoute(final TemplateEngine templateEngine) {
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    //
    LOG.config("GetSigninRoute is initialized.");
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
    LOG.finer("GetSigninRoute is invoked.");
    //
    Map<String, Object> vm = new HashMap<>();
    vm.put("title", "Sign In!");

    // display a user message in the Home page
    vm.put("message", SELECT);

    // render the View
    return templateEngine.render(new ModelAndView(vm , "signin.ftl"));
  }
}
