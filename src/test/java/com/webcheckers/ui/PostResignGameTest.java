package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.*;
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
 * Unit test suite for {@link PostResignGame} component
 *
 * @author Brandon Chen
 */
@Tag("UI-tier")
public class PostResignGameTest {
    private Request request;
    private Response response;
    private Session session;
    private PostResignGame CuT;
    private Gson gson;
    private Player player;
    private Player opponent;
    private Game game;
    private PlayerLobby lobby;
    private static final String PLAYER_1="Player1";
    private static final String PLAYER_2="Player2";

    /**
     * setup for the resigngame test
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

        CuT = new PostResignGame(gson);
    }

    /**
     * resign test
     */
    @Test
    public void resign(){
        player.setGame(game);
        when(session.attribute("Player")).thenReturn(player);

        Object json = CuT.handle(request, response);

        assertTrue(!player.isInGame());
        assertNull(player.getOpponent());
        assertTrue(game.getGameStatus());
        assertEquals(gson.toJson(new Message("You resigned!", Message.Type.INFO)), json);
        assertEquals(Message.Type.INFO, gson.fromJson(json.toString(), Message.class).getType());
        assertTrue(gson.fromJson(json.toString(), Message.class).isSuccessful());
    }
    


}