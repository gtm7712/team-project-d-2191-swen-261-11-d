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

import com.webcheckers.util.Message;

/**
 * ui controller for validating moves
 */
public class PostValidateMoveRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostValidateMoveRoute.class.getName());
    private final TemplateEngine templateEngine;
    public PostValidateMoveRoute(final TemplateEngine templateEngine ){
        this.templateEngine=templateEngine;
    }

    @Override
    public Object handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();
        Player currentPlayer = request.session().attribute("Player");
        Game game = currentPlayer.getGame();
        Board board = game.getClonedBoard();
        MoveValidator validate = new MoveValidator(board);
        System.out.println(request.queryParams("actionData"));

        String move=request.queryParams("actionData");
        int startR=Character.getNumericValue(move.charAt(16));
        int startC=Character.getNumericValue(move.charAt(25));
        int endR=Character.getNumericValue(move.charAt(41));
        int endC=Character.getNumericValue(move.charAt(50));
        vm.put("title", "Let's Play");
        vm.put("viewMode", "PLAY");
        vm.put("currentUser", currentPlayer);
        vm.put("redPlayer", game.getRedPlayer());
        vm.put("whitePlayer", game.getWhitePlayer());
        if(currentPlayer.equals(game.getRedPlayer()))
            vm.put("activeColor", Piece.Color.RED);
        if(currentPlayer.equals(game.getWhitePlayer()))
            vm.put("activeColor", Piece.Color.WHITE);

        Move madeMove= new Move(new Position(startR, startC), new Position(endR, endC));
        System.out.println(madeMove);
        Enum<MoveValidator.TurnResult> result = validate.validateMove(madeMove);
        if (result.equals(MoveValidator.TurnResult.COMPLETE))
        {
            board.makeMove(madeMove);
            vm.put("board", board);
        }
        if(result.equals(MoveValidator.TurnResult.CONTINUE)) {
            board.makeMove(madeMove);
            vm.put("board", board);
        }
        if(result.equals(MoveValidator.TurnResult.KING)){
            board.makeMove(madeMove);
            board.getSpace(madeMove.getEnd()).kingPiece();
            vm.put("board", board);
        }
        if(result.equals(MoveValidator.TurnResult.FAIL)){
            vm.put("board", board);
        }
        return templateEngine.render(new ModelAndView(vm , "game.ftl"));
    }
}
