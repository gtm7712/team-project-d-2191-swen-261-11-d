package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Player;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

import com.webcheckers.util.Message;

import org.eclipse.jetty.util.security.Credential;

/**
 * The UI Controller for start game.
 *
 * @author Kyle Collins
 */
public class GetStartGameRoute implements Route {

    private enum view { PLAY_MODE, SPECTATOR_MODE, REPLAY_MODE;}

    private static final Logger LOG = Logger.getLogger(GetStartGameRoute.class.getName());
    private final TemplateEngine templateEngine;
    private final PlayerLobby lobby;
    private final Game game;

    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public GetStartGameRoute(final TemplateEngine templateEngine, final PlayerLobby lobby) {
      this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
      this.lobby = lobby;
      game = new Game();
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

      Player currentPlayer = request.session().attribute("Player");
      String otherPlayer = request.queryParams("otherPlayer");
      Player opponent = lobby.getPlayer(otherPlayer);

      // check to see if player is in game
      if(!currentPlayer.isInGame()) {

        if(opponent.isInGame()){
            vm.put("title", "Welcome!");
            vm.put("allUsers",lobby.getUsernames());
            vm.put("error", "Player is already in a game!");
            return templateEngine.render(new ModelAndView(vm , "home.ftl"));
        }

        currentPlayer.setOpponent(opponent);
        opponent.setOpponent(currentPlayer);

        lobby.getPlayer(currentPlayer.name).inGame(true);
        lobby.getPlayer(otherPlayer).inGame(true);

        game.setRedPlayer(currentPlayer);
        game.setWhitePlayer(opponent);

        currentPlayer.setGame(game);
        opponent.setGame(game);

    }

    opponent = currentPlayer.getOpponent();

    // Inject game information into template
    vm.put("title", "Let's Play");

    vm.put("viewMode", "PLAY");
    vm.put("currentUser", currentPlayer);
    vm.put("redPlayer", game.getRedPlayer());
    vm.put("whitePlayer", game.getWhitePlayer());

    Player playerTurn = game.whoseTurn();

    if(playerTurn == game.getRedPlayer()) {
      vm.put("activeColor", Piece.Color.RED);
      vm.put("board", game.getBoardRed());
    }
    else {
      vm.put("activeColor", Piece.Color.WHITE);
      vm.put("board", game.getBoardWhite());
    }

    // render the View
    return templateEngine.render(new ModelAndView(vm , "game.ftl"));
    }
  }

