package com.webcheckers.ui;
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

import com.webcheckers.util.Message;

/**
 * ui controller for validating moves
 */
public class PostValidateMoveRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostValidateMoveRoute.class.getName());
    private final Gson gson;

    public PostValidateMoveRoute(final Gson gson ){
        this.gson = gson;
    }

    @Override
    public Object handle(Request request, Response response) {
//        Map<String, Object> vm = new HashMap<>();
        Player currentPlayer = request.session().attribute("Player");
        Game game = currentPlayer.getGame();
        Board board = game.getClonedBoard();
        MoveValidator validate = new MoveValidator(game);
        System.out.println(request.queryParams("actionData"));

        String move = request.queryParams("actionData");
        int startR = Character.getNumericValue(move.charAt(16));
        int startC = Character.getNumericValue(move.charAt(25));
        int endR = Character.getNumericValue(move.charAt(41));
        int endC = Character.getNumericValue(move.charAt(50));
//        vm.put("title", "Let's Play");
//        vm.put("viewMode", "PLAY");
//        vm.put("currentUser", currentPlayer);
//        vm.put("redPlayer", game.getRedPlayer());
//        vm.put("whitePlayer", game.getWhitePlayer());
//        if(currentPlayer.equals(game.getRedPlayer()))
//            vm.put("activeColor", Piece.Color.RED);
//        if(currentPlayer.equals(game.getWhitePlayer()))
//            vm.put("activeColor", Piece.Color.WHITE);
        Move madeMove;
        if(currentPlayer.equals(game.getWhitePlayer())){
            madeMove= new Move(new Position(7-startR, startC), new Position(7-endR, endC));
        }
        else {
            madeMove = new Move(new Position(startR, startC), new Position(endR, endC));
        }
        System.out.println(madeMove);
        //Enum<MoveValidator.TurnResult> result = validate.validateMove(madeMove);
        if(game.isComplete())
            return gson.toJson(new Message("Move already made", Message.Type.ERROR));

        ValidationResult result = validate.validateMove(madeMove); 
        if(validate.didJump){
            request.session().attribute("jumped", validate.didJump);
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
        //return templateEngine.render(new ModelAndView(vm , "game.ftl"));
    }
}
