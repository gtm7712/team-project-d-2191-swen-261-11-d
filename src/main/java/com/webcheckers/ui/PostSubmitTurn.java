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
 * ui controller for submitting turn
 * 
 * @author Kyle Collins
 */
public class PostSubmitTurn implements Route {
    private static final Logger LOG = Logger.getLogger(PostValidateMoveRoute.class.getName());
    private final Gson gson;

    public PostSubmitTurn(final Gson gson ){
        this.gson = gson;
    }

    /**
     * Send submitTurn ajax to client-side
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   an ajax call saying whether the submitTurn was successful
     */
    
    @Override
    public Object handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();
        Player currentPlayer = request.session().attribute("Player");
        Game game = currentPlayer.getGame();
        
        // vm.put("title", "Let's Play");
        // vm.put("viewMode", "PLAY");
        // vm.put("currentUser", currentPlayer);
        // vm.put("redPlayer", game.getRedPlayer());
        // vm.put("whitePlayer", game.getWhitePlayer());

        MoveValidator validate = new MoveValidator(game);
        Piece.Color currentColor;
        if(currentPlayer == game.getRedPlayer()) {
            currentColor = Piece.Color.RED;
        }
        else {
            currentColor = Piece.Color.WHITE;
        }
        if(request.session().attribute("jumped") != null){
            boolean jumped = request.session().attribute("jumped");
            
            if(validate.shouldMakeJump(currentColor) && jumped){
                return gson.toJson(new Message("You can still jump!", Message.Type.ERROR));
            }
        }
        // End the current player's turn
        game.endTurn();
        boolean f = game.noMorePieces();
        if(game.noMorePieces()){
            game.setGameOver(true);
            Player winner = game.getWinner();
        }
        if(game.hasNoMoves()){
            game.setGameOver(true);
            Player winner=game.getWinner();
        }
        return gson.toJson(new Message("Good move!", Message.Type.INFO));

    }
}