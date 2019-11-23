package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;

import com.webcheckers.model.*;
import spark.Request;
import spark.Response;
import spark.Route;

import com.google.gson.Gson;

import com.webcheckers.util.Message;

/**
 * ui controller for backing up
 */
public class PostBackupRoute implements Route {
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
        Player currentPlayer = request.session().attribute("Player");
        Game game = currentPlayer.getGame();
        String message = game.revertTurn();

        return gson.toJson(new Message("Backup Successful!", Message.Type.INFO));
    }
}
