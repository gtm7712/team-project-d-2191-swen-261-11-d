package com.webcheckers.ui;

import spark.Request;
import spark.Response;
import spark.Route;

import com.google.gson.Gson;

import com.webcheckers.model.*;
import com.webcheckers.util.Message;

/**
 * ui controller for resigning a game
 * @author Brandon Chen
 */
public class PostResignGame implements Route {
    private final Gson gson;

    /**
     * constructor for the PostResignGame
     * @param gson the gson used to send ajax
     */
    public PostResignGame(final Gson gson ){
        this.gson = gson;
    }

    /**
     * Send resignGame ajax to client-side
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   an ajax call saying whether the resignGame successful
     */
    
    @Override
    public Object handle(Request request, Response response) {
        Player currentPlayer = request.session().attribute("Player");
        currentPlayer.resign();
        currentPlayer.setOpponent(null);
        return gson.toJson(new Message("You resigned!", Message.Type.INFO));   
    }
}
