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
 * ui controller for backing up
 */
public class PostBackupRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostBackupRoute.class.getName());
    private final Gson gson;

    public PostBackupRoute(final Gson gson ){
        this.gson = gson;
    }

    /**
     * Send backup ajax to client-side
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   an ajax call saying whether the backup was successful
     */
    @Override
    public Object handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();
        Player currentPlayer = request.session().attribute("Player");
        Game game = currentPlayer.getGame();

        String message = game.revertTurn();
        
        vm.put("title", "Let's Play");
        vm.put("viewMode", "PLAY");
        vm.put("currentUser", currentPlayer);
        vm.put("redPlayer", game.getRedPlayer());
        vm.put("whitePlayer", game.getWhitePlayer());
        if(currentPlayer.equals(game.getRedPlayer()))
            vm.put("activeColor", Piece.Color.RED);
        if(currentPlayer.equals(game.getWhitePlayer()))
            vm.put("activeColor", Piece.Color.WHITE);

        if(message == null){
            return gson.toJson(new Message("Backup Successful!", Message.Type.INFO));
        }
        else{
            return gson.toJson(new Message(message, Message.Type.ERROR));   
        }
    }
}