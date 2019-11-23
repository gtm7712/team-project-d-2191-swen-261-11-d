package com.webcheckers.ui;

import com.webcheckers.model.*;
import spark.Request;
import spark.Response;
import spark.Route;

import com.google.gson.Gson;

import com.webcheckers.util.Message;

/**
 * ui controller for checkturn
 */
public class PostCheckTurn implements Route {
    private final Gson gson;

    /**
     * checkturn constructor
     * @param gson the gson to used in the route
     */
    public PostCheckTurn(final Gson gson ){
        this.gson = gson;
    }

    /**
     * Send checkturn ajax to client-side
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   an ajax call saying whether it is your turn
     */
    
    @Override
    public Object handle(Request request, Response response) {
        Player currentPlayer = request.session().attribute("Player");
        Game game = currentPlayer.getGame();
        if(game.getGameStatus()){
            return gson.toJson(new Message("true", Message.Type.INFO));
        }
        if(currentPlayer.equals(game.whoseTurn())){
            return gson.toJson(new Message("true", Message.Type.INFO));
        }
        else{
            return gson.toJson(new Message("false", Message.Type.INFO));   
        }
    }
}
