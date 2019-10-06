package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.appl.PlayerLobby;
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

    private final String INVALID_USERNAME = "This username is not valid!  Usernames must be alphanumeric.";
    private final String USERNAME_IN_USE = "Pick another username, this one is already in use.";
    private final String USERNAME_GOOD = "Logged in";

    private final PlayerLobby lobby;
    private final TemplateEngine templateEngine;

    /**
     * Create the Spark Route (UI controller) to handle all {@code POST /signin} HTTP requests.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public PostSignInRoute(final TemplateEngine templateEngine, PlayerLobby lobby) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        this.lobby = lobby;
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
        if (lobby.checkUsername(username) == 0) {
            vm.put("logIN", INVALID_USERNAME);
        }
        if (lobby.checkUsername(username) == 1) {
            vm.put("logIN", USERNAME_IN_USE);
        }
        if (lobby.checkUsername(username) == 2) {
            vm.put("logIN", USERNAME_GOOD);
            lobby.addUsername(username);
            request.session().attribute("Player", currentPlayer );
            response.redirect("/");
        }
        // render the View
        return templateEngine.render(new ModelAndView(vm , "signin.ftl"));
    }
}
