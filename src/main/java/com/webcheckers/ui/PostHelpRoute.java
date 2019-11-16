package com.webcheckers.ui;

import java.util.ArrayList;
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
import com.webcheckers.util.Helper;
import com.webcheckers.util.Message;

/**
 * ui controller for help feature
 * @author Kyle Collins
 */
public class PostHelpRoute implements Route {

    private static final Logger LOG = Logger.getLogger(PostHelpRoute.class.getName());
    private final Gson gson;

    /**
     * constructor for the Posthelp
     * @param gson the gson used to send ajax
     */
    public PostHelpRoute(final Gson gson ){
        this.gson = gson;
    }

    /**
     * Send help ajax to client-side
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   an ajax call saying whether help was successful
     */
    
    @Override
    public Object handle(Request request, Response response) {

        Player currentPlayer = request.session().attribute("Player");
        Helper help = new Helper(currentPlayer);
        ArrayList<Space> moveablePieces = help.validPieces();
        String result = "";
        for(int i = 0; i < moveablePieces.size(); i++){
            Space s = moveablePieces.get(i);
            int row = s.getRow();
            int col = s.getCellIdx();
            result += row + "" + col + " ";
        }

        return gson.toJson(new Message(result, Message.Type.INFO));   

    }
}