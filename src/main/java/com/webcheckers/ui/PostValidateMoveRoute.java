package com.webcheckers.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.model.*;
import com.webcheckers.util.MoveValidator;
import com.webcheckers.util.ValidationResult;

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
 * ui controller for validating moves
 */
public class PostValidateMoveRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostValidateMoveRoute.class.getName());
    private final Gson gson;

    /**
     * Constructor that initializes the gson
     * @param gson gson to be used by route
     */
    public PostValidateMoveRoute(final Gson gson ){
        this.gson = gson;
    }

    @Override
    public Object handle(Request request, Response response) {

        Player currentPlayer = request.session().attribute("Player");
        Game game = currentPlayer.getGame();
        Board board = game.getClonedBoard();
        MoveValidator validate = new MoveValidator(game);

        String move = request.queryParams("actionData");
        int startR = Character.getNumericValue(move.charAt(16));
        int startC = Character.getNumericValue(move.charAt(25));
        int endR = Character.getNumericValue(move.charAt(41));
        int endC = Character.getNumericValue(move.charAt(50));
        Move madeMove;

        if(currentPlayer.equals(game.getWhitePlayer())){
            madeMove= new Move(new Position(7-startR, startC), new Position(7-endR, endC));
        }
        else {
            madeMove = new Move(new Position(startR, startC), new Position(endR, endC));
        }
        //Enum<MoveValidator.TurnResult> result = validate.validateMove(madeMove);
        if(game.isComplete())
            return gson.toJson(new Message("Move already made", Message.Type.ERROR));

        ValidationResult result = validate.validateMove(madeMove); 
        if(result.wasJump()){
            request.session().attribute("jumped", result.wasJump());
        }
        switch (result.getTurnResult()) {
            case COMPLETE:
                game.makeMove(madeMove);
                request.session().attribute("jumped", false);
//                vm.put("board", board);
                game.setComplete();
                break;
            case CONTINUE:
                game.makeMove(madeMove);
//                vm.put("board", board);
                break;
            case KING:
                game.makeMove(madeMove);
                game.kingPiece(madeMove.getEnd());
//                vm.put("board", board);
                break;
            case FAIL:
//                vm.put("board", board);
                return gson.toJson(new Message(validate.msg, Message.Type.ERROR));
        }
        if (result.wasJump()) {
            //game.makeMove(madeMove);
            game.makeMove(new Move(validate.getMidpoint(madeMove), new Position(-1, -1)));
        }
        return gson.toJson(new Message("Nice Move!", Message.Type.INFO));
    }
}
