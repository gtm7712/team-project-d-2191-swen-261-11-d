package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.gson.Gson;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Board;
import com.webcheckers.model.Game;
import com.webcheckers.model.Move;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Player;
import com.webcheckers.model.Position;
import com.webcheckers.model.Piece.Color;
import com.webcheckers.util.Message;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

/**
 * Unit test suite for {@link PostSubmitTurn} component
 *
 * @author Brandon Chen
 */
@Tag("UI-tier")
public class PostSubmitTurnTest {
    private Request request;
    private Response response;
    private Session session;
    private PostSubmitTurn CuT;
    private Gson gson;
    private Player player;
    private Player opponent;
    private Board board;
    private Game game;
    private PlayerLobby lobby;
    private static final String PLAYER_1="Player1";
    private static final String PLAYER_2="Player2";

    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);

        // Setting up game
        lobby = new PlayerLobby();
        lobby.addUsername(PLAYER_1);
        lobby.addUsername(PLAYER_2);

        gson = new Gson();
        game = new Game();
        
        player = lobby.getPlayer(PLAYER_1);
        opponent = lobby.getPlayer(PLAYER_2);
        
        opponent.setGame(game);
        player.setGame(game);

        game.setRedPlayer(player);
        game.setWhitePlayer(opponent);


        CuT = new PostSubmitTurn(gson);
    }

    /**
     * submitTurn test
     */
    @Test
    public void submitTurnRed(){
        when(session.attribute("Player")).thenReturn(player);
        when(session.attribute("jumped")).thenReturn(true);

        Object json = CuT.handle(request, response);

        assertEquals(json, gson.toJson(new Message("Good move!", Message.Type.INFO)));
    }
    
    /**
     * submitTurn white test
     */
    @Test
    public void submitTurnWhite(){

        when(session.attribute("Player")).thenReturn(opponent);
        when(session.attribute("jumped")).thenReturn(true);

        Object json = CuT.handle(request, response);

        assertEquals(json, gson.toJson(new Message("Good move!", Message.Type.INFO)));
    }
    
    /**
     * submitTurn white test
     */
    @Test
    public void submitTurnRedSingle(){

        when(session.attribute("Player")).thenReturn(opponent);
        when(session.attribute("jumped")).thenReturn(null);

        Object json = CuT.handle(request, response);

        assertEquals(json, gson.toJson(new Message("Good move!", Message.Type.INFO)));
    }

    /**
     * submitTurn white test
     */
    @Test
    public void submitTurnStillJump(){
        
        when(session.attribute("Player")).thenReturn(player);
        when(session.attribute("jumped")).thenReturn(true);

        game.getBoardRed().getSpace(0, 3).setPiece(null);
        Move m = new Move(new Position(5,4), new Position(4,3));
        game.makeMove(m);
        game.endTurn();
        Move m2 = new Move(new Position(2,5), new Position(3,4));
        game.makeMove(m2);
        game.endTurn(); 

        Object json = CuT.handle(request, response);

        assertEquals(json, gson.toJson(new Message("You can still jump!", Message.Type.ERROR)));
        
        assertEquals(Message.Type.ERROR, gson.fromJson(json.toString(), Message.class).getType());
        assertFalse(gson.fromJson(json.toString(), Message.class).isSuccessful());
    }

    /**
     * submitTurn white test
     */
    @Test
    public void submitTurnSingleJumpCheck(){
        
        when(session.attribute("Player")).thenReturn(player);
        when(session.attribute("jumped")).thenReturn(false);

        Move m = new Move(new Position(5,4), new Position(4,3));
        game.makeMove(m);
        game.endTurn();
        Move m2 = new Move(new Position(2,5), new Position(3,4));
        game.makeMove(m2);
        game.endTurn(); 

        Object json = CuT.handle(request, response);

        assertEquals(json, gson.toJson(new Message("Good move!", Message.Type.INFO)));
    }
    
    /**
     * submitTurn white test
     */
    @Test
    public void noPieces(){
        
        when(session.attribute("Player")).thenReturn(player);
        when(session.attribute("jumped")).thenReturn(null);
        game.__test_remove_all_pieces(Color.WHITE);

        Object json = CuT.handle(request, response);

        assertTrue(game.getGameStatus());
        assertEquals(player, game.getWinner());
        assertEquals(json, gson.toJson(new Message("Good move!", Message.Type.INFO)));
    }
}