package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.*;
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
 * Unit test suite for {@link PostBackupRoute} component
 *
 * @author Brandon Chen
 */
@Tag("UI-tier")
public class PostBackupRouteTest {
    private Request request;
    private Response response;
    private Session session;
    private PostBackupRoute CuT;
    private Gson gson;
    private Player player;
    private Game game;
    private PlayerLobby lobby;
    private static final String PLAYER_1="Player1";

    /**
     * setup for backuproute test
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

        gson = new Gson();
        game = new Game();
        
        player = lobby.getPlayer(PLAYER_1);
        player.setGame(game);
        CuT = new PostBackupRoute(gson);
    }

    /**
     * backup test
     */
    @Test
    public void backup(){
        when(session.attribute("Player")).thenReturn(player);

        Move m = new Move(new Position(5,4), new Position(4,3));

        game.makeMove(m);
        player.setGame(game);

        Object json = CuT.handle(request, response);
        assertEquals(gson.toJson(new Message("Backup Successful!", Message.Type.INFO)), json);
        assertEquals(Message.Type.INFO, gson.fromJson(json.toString(), Message.class).getType());
        assertTrue(gson.fromJson(json.toString(), Message.class).isSuccessful());
    }

}