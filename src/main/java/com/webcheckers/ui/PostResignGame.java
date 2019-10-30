package com.webcheckers.ui;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.model.*;
import com.webcheckers.util.MoveValidator;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

import com.google.gson.Gson;
import com.google.gson.annotations.JsonAdapter;

import com.webcheckers.util.Message;

/**
 * ui controller for validating moves
 */
public class PostResignGame implements Route {
    private static final Logger LOG = Logger.getLogger(PostValidateMoveRoute.class.getName());
    private final Gson gson;

    public PostResignGame(final Gson gson ){
        this.gson = gson;
    }

    @Override
    public Object handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();
        Player currentPlayer = request.session().attribute("Player");
        Game game = currentPlayer.getGame();

        vm.put("title", "Let's Play");
        vm.put("viewMode", "PLAY");
        vm.put("currentUser", currentPlayer);
        vm.put("redPlayer", game.getRedPlayer());
        vm.put("whitePlayer", game.getWhitePlayer());
        currentPlayer.resign();
        if(game.getGameStatus()){
            final Map<String, Object> modeOptions = new HashMap<>(2);
            modeOptions.put("isGameOver", true);
            modeOptions.put("gameOverMessage", currentPlayer.getOpponent().getName() + " resigned!");
            vm.put("modeOptionsAsJSON", gson.toJson(modeOptions));
            currentPlayer.setOpponent(null);
            return gson.toJson(new Message("You resigned!", Message.Type.INFO));   
        }
        return gson.toJson(new Message("Failed to resign", Message.Type.ERROR));
        //return templateEngine.render(new ModelAndView(vm , "game.ftl"));
    }
}
