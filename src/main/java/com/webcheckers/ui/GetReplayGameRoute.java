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

import com.google.gson.Gson;
import com.webcheckers.appl.GameList;
import com.webcheckers.model.Board;
import com.webcheckers.model.Game;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import com.webcheckers.util.ReplayHelper;

/**
 * The UI Controller to GET the Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class GetReplayGameRoute implements Route {
  private static final Logger LOG = Logger.getLogger(GetReplayGameRoute.class.getName());

  private final TemplateEngine templateEngine;
  private final Gson gson;
  private final GameList gameList;

  /**
   * Create the Spark Route (UI controller) to handle all {@code GET /signin} HTTP requests.
   *
   * @param templateEngine
   *   the HTML template rendering engine
   */
  public GetReplayGameRoute(final TemplateEngine templateEngine, Gson gson, GameList gameList) {
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    this.gson = Objects.requireNonNull(gson, "gson is required");
    this.gameList = gameList;
    //
    LOG.config("GetReplayGameRoute is initialized.");
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
    LOG.finer("GetReplayGameRoute is invoked.");
    //
    Player currentPlayer = request.session().attribute("Player");
    Map<String, Object> vm = new HashMap<>();

    Map<String, Object> modeOptions = new HashMap<>();


    Integer gameID = Integer.parseInt(request.queryParams("gameID"));
    Game game = gameList.getGame(gameID);
    
    Player redPlayer = game.getRedPlayer();
    Player whitePlayer = game.getWhitePlayer();
    
    // ReplayHelper rpyHelper = rpyGame.getReplayHelper();
    ReplayHelper rpyHelper = game.getReplayHelper();
    game.setBoard(new Board());
    int index = rpyHelper.getIndex();
    // game.setReplay(rpyHelper);
    // System.out.println(rpyHelper.getReplay());

    modeOptions.put("hasNext", rpyHelper.canGoForward());
    modeOptions.put("hasPrevious", rpyHelper.canGoBack());

    System.out.println(rpyHelper.getReplay());
    System.out.println(rpyHelper.canGoForward());
    System.out.println(rpyHelper.getIndex());
    System.out.println(rpyHelper.getBoards());

    if (!rpyHelper.canGoForward()) {
      if (rpyHelper.getResult() != null) {
        switch(rpyHelper.getResult()) {
          case RED_RESIGN:
          vm.put("message", Message.info(rpyHelper.getRed() + " resigned!"));
          case WHITE_RESIGN:
          vm.put("message", Message.info(rpyHelper.getWhite() + " resigned!"));
          case RED_WIN:
          vm.put("message", Message.info(rpyHelper.getRed() + " won!"));
          case WHITE_WIN:
          vm.put("message", Message.info(rpyHelper.getWhite() + " won!"));
        }
      } else {
        vm.put("message", Message.info("Who will win?"));
      }
    } else {
      vm.put("message", Message.info("Who will win?"));
    }

    vm.put("modeOptionsAsJSON", gson.toJson(modeOptions));

    vm.put("title", "Replay");
    vm.put("viewMode", "REPLAY");
    vm.put("currentUser", currentPlayer);
    vm.put("redPlayer" , redPlayer);
    vm.put("whitePlayer", whitePlayer);
    vm.put("board", rpyHelper.getAtIndex(index));


    if(index % 2 == 0) {
      vm.put("activeColor", Piece.Color.RED);
      
    }
    else {
      vm.put("activeColor", Piece.Color.WHITE);
    }

    // render the View
    return templateEngine.render(new ModelAndView(vm , "game.ftl"));
  }
}
