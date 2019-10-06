package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.model.Player;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

import com.webcheckers.util.Message;


public class PostSignInRoute implements Route{

    static final String USERNAME = "username";

    private static final Logger LOG = Logger.getLogger(PostSignInRoute.class.getName());

    private static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");

    private final TemplateEngine templateEngine;

    /**
     * Create the Spark Route (UI controller) to handle all {@code POST /signin} HTTP requests.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public PostSignInRoute(final TemplateEngine templateEngine) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        //
        LOG.config("PostSignInRoute is initialized.");
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
        LOG.finer("PostSignIn is invoked.");
        //

        final String username = request.queryParams(USERNAME);
        
        // TODO: Add username checks

        Map<String, Object> vm = new HashMap<>();

        Player currentPlayer = new Player(username);

        vm.put("title", "Welcome!");

        vm.put("message", WELCOME_MSG);

        request.session().attribute("Player", currentPlayer );
        response.redirect("/");
        // render the View
        return templateEngine.render(new ModelAndView(vm , "signin.ftl"));
    }
}
