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
 * Unit test suite for {@link PostHelperRoute} component
 *
 * @author Brandon Chen
 */
@Tag("UI-tier")
public class PostHelperRouteTest {
    private Request request;
    private Response response;
    private Session session;
    private PostHelpRoute CuT;
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
        player.setGame(game);
        opponent = lobby.getPlayer(PLAYER_2);
        opponent.setGame(game);

        game.setRedPlayer(player);
        game.setWhitePlayer(opponent);

        player.setOpponent(opponent);
        opponent.setOpponent(player);

        CuT = new PostHelpRoute(gson);
    }

    /**
     * Help test
     */
    @Test
    public void Help(){
        when(session.attribute("Player")).thenReturn(player);

        Object json = CuT.handle(request, response);
        assertEquals(gson.toJson(new Message("56 54 52 50 ", Message.Type.INFO)), json);
        assertEquals(Message.Type.INFO, gson.fromJson(json.toString(), Message.class).getType());
        assertTrue(gson.fromJson(json.toString(), Message.class).isSuccessful());
    }

}