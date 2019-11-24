package com.webcheckers.ui;

import com.webcheckers.model.*;
import com.webcheckers.util.MoveValidator;
import spark.Request;
import spark.Response;
import spark.Route;

import com.google.gson.Gson;

import com.webcheckers.util.Message;

/**
 * ui controller for submitting a turn
 * 
 * @author Kyle Collins
 */
public class PostSubmitTurn implements Route {
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
        Player currentPlayer = request.session().attribute("Player");
        Game game = currentPlayer.getGame();
        
        MoveValidator validate = new MoveValidator(game);
        Piece.Color currentColor;
        if(currentPlayer == game.getRedPlayer()) {
            currentColor = Piece.Color.RED;
        }
        else {
            currentColor = Piece.Color.WHITE;
        }
        if(game.hasJumped()!=null){
            if(validate.shouldMakeJump(currentColor) && game.hasJumped()!=null){
                Position lastJump=game.hasJumped().getEnd();
                if(validate.isCapturePossible(lastJump,game.getBoardRed().getSpace(lastJump).getPiece())&&!game.isComplete()) {
                    return gson.toJson(new Message("You can still jump!", Message.Type.ERROR));
                }
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