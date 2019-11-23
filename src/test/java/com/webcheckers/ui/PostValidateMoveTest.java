package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.gson.Gson;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player;
import com.webcheckers.model.Position;
import com.webcheckers.util.Message;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import spark.Request;
import spark.Response;
import spark.Session;

/**
 * Unit test suite for {@link PostValidateMove} component
 *
 * @author Brandon Chen
 */
@Tag("UI-tier")
public class PostValidateMoveTest {
    private Request request;
    private Response response;
    private Session session;
    private PostValidateMoveRoute CuT;
    private Gson gson;
    private Player player;
    private Player opponent;
    private Game game;
    private PlayerLobby lobby;
    private static final String PLAYER_1="Player1";
    private static final String PLAYER_2="Player2";

    /**
     * setup for a postvalidatemovetest
     */
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


        CuT = new PostValidateMoveRoute(gson);
    }

    /**
     * validateMove test for red
     */
    @Test
    public void validMoveRed(){
        when(session.attribute("Player")).thenReturn(player);
        when(request.queryParams(eq("actionData"))).thenReturn("{'start':{'row':5,'cell':4},'end':{'row':4,'cell':3}}");

        Object json = CuT.handle(request, response);

        assertEquals(json, gson.toJson(new Message("Nice Move!", Message.Type.INFO)));
    }
    
    /**
     * validateMove test for white
     */
    @Test
    public void validMoveWhite(){
        game.endTurn();
        opponent.setBoard(game.getBoardWhite());
        when(session.attribute("Player")).thenReturn(opponent);
        when(request.queryParams(eq("actionData"))).thenReturn("{'start':{'row':5,'cell':5},'end':{'row':4,'cell':4}}");

        Object json = CuT.handle(request, response);

        assertEquals(json, gson.toJson(new Message("Nice Move!", Message.Type.INFO)));
    }

    /**
     * validateMove already moved test
     */
    @Test
    public void alreadyMoved(){
        game.setComplete();
        when(session.attribute("Player")).thenReturn(player);
        when(request.queryParams(eq("actionData"))).thenReturn("{'start':{'row':5,'cell':4},'end':{'row':4,'cell':3}}");

        Object json = CuT.handle(request, response);

        assertEquals(json, gson.toJson(new Message("Move already made", Message.Type.ERROR)));
    }
    
    /**
     * validateMove jump move test
     */
    @Test
    public void jumpMove(){
        Move m = new Move(new Position(5,4), new Position(4,3));
        game.makeMove(m);
        game.endTurn();
        Move m2 = new Move(new Position(2,5), new Position(3,4));
        game.makeMove(m2);
        game.endTurn(); 
        when(session.attribute("Player")).thenReturn(player);
        when(request.queryParams(eq("actionData"))).thenReturn("{'start':{'row':4,'cell':3},'end':{'row':2,'cell':5}}");

        Object json = CuT.handle(request, response);

        assertEquals(json, gson.toJson(new Message("Nice Move!", Message.Type.INFO)));
    }

    /**
     * validateMove bad move
     */
    @Test
    public void badMove(){
        Move m = new Move(new Position(5,4), new Position(4,3));
        game.makeMove(m);
        game.endTurn();
        Move m2 = new Move(new Position(2,5), new Position(3,4));
        game.makeMove(m2);
        game.endTurn(); 
        when(session.attribute("Player")).thenReturn(player);
        when(request.queryParams(eq("actionData"))).thenReturn("{'start':{'row':4,'cell':3},'end':{'row':5,'cell':4}}");

        Object json = CuT.handle(request, response);

        assertEquals(json, gson.toJson(new Message("Invalid move!", Message.Type.ERROR)));
    }

    /**
     * validateMove continue move test
     */
    @Test
    public void continueMove(){
        Move m = new Move(new Position(5,4), new Position(4,5));
        game.makeMove(m);
        game.endTurn();
        Move m2 = new Move(new Position(2,5), new Position(3,4));
        game.makeMove(m2);
        game.endTurn(); 
        Move m3 = new Move(new Position(5,0), new Position(4,1));
        game.makeMove(m3);
        game.endTurn();
        Move m4 = new Move(new Position(1,4), new Position(2,5));
        game.makeMove(m4);
        game.endTurn(); 
        Move m5 = new Move(new Position(6,1), new Position(5,0));
        game.makeMove(m5);
        game.endTurn();
        Move m6 = new Move(new Position(0,5), new Position(1,4));
        game.makeMove(m6);
        game.endTurn(); 
        Move m7 = new Move(new Position(7,2), new Position(6,1));
        game.makeMove(m7);
        game.endTurn();
        Move m8 = new Move(new Position(2,7), new Position(3,6));
        game.makeMove(m8);
        game.endTurn(); 
        when(session.attribute("Player")).thenReturn(player);
        when(request.queryParams(eq("actionData"))).thenReturn("{'start':{'row':4,'cell':5},'end':{'row':2,'cell':7}}");

        Object json = CuT.handle(request, response);

        assertEquals(json, gson.toJson(new Message("Nice Move!", Message.Type.INFO)));
    }

    /**
     * validateMove king move test
     */
    @Test
    public void kingMove(){
        Move m = new Move(new Position(5,4), new Position(4,5));
        game.makeMove(m);
        game.endTurn();
        Move m2 = new Move(new Position(2,5), new Position(3,4));
        game.makeMove(m2);
        game.endTurn(); 
        Move m3 = new Move(new Position(5,0), new Position(4,1));
        game.makeMove(m3);
        game.endTurn();
        Move m4 = new Move(new Position(1,4), new Position(2,5));
        game.makeMove(m4);
        game.endTurn(); 
        Move m5 = new Move(new Position(6,1), new Position(5,0));
        game.makeMove(m5);
        game.endTurn();
        Move m6 = new Move(new Position(0,5), new Position(1,4));
        game.makeMove(m6);
        game.endTurn(); 
        Move m7 = new Move(new Position(7,2), new Position(6,1));
        game.makeMove(m7);
        game.endTurn();
        Move m8 = new Move(new Position(2,7), new Position(3,6));
        game.makeMove(m8);
        game.endTurn(); 
        Move m9 = new Move(new Position(4,5), new Position(2,7));
        game.makeMove(m9);
        when(session.attribute("Player")).thenReturn(player);
        when(request.queryParams(eq("actionData"))).thenReturn("{'start':{'row':2,'cell':7},'end':{'row':0,'cell':5}}");

        Object json = CuT.handle(request, response);

        assertEquals(json, gson.toJson(new Message("Nice Move!", Message.Type.INFO)));
    }
}