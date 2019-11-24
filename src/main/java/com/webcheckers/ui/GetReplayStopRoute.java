package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.appl.GameList;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.ReplayList;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

import com.webcheckers.util.Message;


public class GetReplayStopRoute implements Route{

    private static final Logger LOG = Logger.getLogger(GetReplayStopRoute.class.getName());

    private static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");
  
    private final PlayerLobby lobby;
    private final TemplateEngine templateEngine;
    private final ReplayList replays;
    private final GameList gameList;
    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /replay/stopWatching} HTTP requests.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     * @param lobby
     *  the player lobby
     * @param replays
     *  the replay list
     * @param gameList
     *  the list of games
     */
    public GetReplayStopRoute(final TemplateEngine templateEngine, PlayerLobby lobby, ReplayList replays, GameList gameList) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        this.lobby = lobby;
        this.replays = replays;
        this.gameList = gameList;
        //

        LOG.config("GetReplayStopRoute is initialized.");
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
        LOG.finer("GetReplayStopRoute is invoked.");
        //

        Map<String, Object> vm = new HashMap<>();

    Player currentPlayer = request.session().attribute("Player");

    // Reset the index
    Integer gameID = Integer.parseInt(request.queryParams("gameID"));
    Game game = gameList.getGame(gameID);
    game.getReplayHelper().getAtIndex(0);

    vm.put("currentUser", currentPlayer);
    request.session().attribute("Player",currentPlayer);
    currentPlayer = lobby.getPlayer(currentPlayer.name);
    request.session().attribute("Player",currentPlayer);

    vm.put("title", "Welcome!");
    vm.put("allUsers",lobby.getUsernames());

    if(replays.getGames().size() > 0){
      vm.put("gameList", replays.getGames());
    }

    // display a user message in the Home page
    String plural = " is ";
    if(lobby.countPlayers() != 1) {
      plural = "s are ";
    }
    vm.put("message", Message.info(WELCOME_MSG.getText() + "\n" + lobby.countPlayers() + " player" + plural + "currently online!"));
    // render the View
    return templateEngine.render(new ModelAndView(vm , "home.ftl"));
    }
}
