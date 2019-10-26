package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;


public class PostSignOutRoute implements Route{

    static final String USERNAME = "username";

    private static final Logger LOG = Logger.getLogger(PostSignOutRoute.class.getName());

    private static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");

    private final PlayerLobby lobby;
    private final TemplateEngine templateEngine;

    /**
     * Create the Spark Route (UI controller) to handle all {@code POST /signout} HTTP requests.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public PostSignOutRoute(final TemplateEngine templateEngine, PlayerLobby lobby) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        this.lobby = lobby;
        //

        LOG.config("PostSignOutRoute is initialized.");
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
        LOG.finer("PostSignOut is invoked.");
        //

        Player currentPlayer = request.session().attribute("Player");

        Map<String, Object> vm = new HashMap<>();

        lobby.removeUsername(currentPlayer.getName());
        request.session().removeAttribute("Player");
        vm.put("title", "Welcome!");
        vm.put("allUsers",lobby.getUsernames());
        
        // display a user message in the Home page
        String plural = " is ";
        if(lobby.countPlayers() != 1) {
            plural = "s are ";
        }
        vm.put("message", Message.info(WELCOME_MSG.getText() + "\n" + lobby.countPlayers() + " player" + plural + "currently online!"));
        
        return templateEngine.render(new ModelAndView(vm , "home.ftl"));
    }
}
