package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.gson.Gson;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;

import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import spark.Request;
import spark.Response;
import spark.Session;

/**
 * Unit test suite for {@link PostCheckTurn} component
 *
 * @author Kris Schnupp
 */
@Tag("UI-tier")
public class PostCheckTurnTest {
    private Request request;
    private Response response;
    private Session session;
    private PostCheckTurn CuT;
    private Gson gson;
    private Player player;
    String PLAYER_1="bob";
    String PLAYER_2="bill";
    private Game game;
    private PlayerLobby lobby;
    private Player opponent;
    @BeforeEach
    public void setup() {
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
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        gson = new Gson();
        
        CuT = new PostCheckTurn(gson);
    }
    @Test
    public void checkTurnRed(){
        when(session.attribute("Player")).thenReturn(player);
        Object json =CuT.handle(request, response);
        assertEquals(json,gson.toJson(new Message("true", Message.Type.INFO)));
    }
    @Test
    public void checkTurnWhite(){
        when(session.attribute("Player")).thenReturn(opponent);
        Object json =CuT.handle(request, response);
        assertEquals(json,gson.toJson(new Message("false", Message.Type.INFO)));
    }
    @Test
    public void checkEndGameWhite(){
        game.setGameOver(true);
        when(session.attribute("Player")).thenReturn(opponent);
        Object json =CuT.handle(request, response);
        assertEquals(json,gson.toJson(new Message("true", Message.Type.INFO)));
    }
}