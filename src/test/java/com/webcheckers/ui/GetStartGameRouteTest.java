package com.webcheckers.ui;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

/**
 * Unit test suite for {@link GetStartGame} component
 * 
 * @author Kyle Collins
 */
@Tag("UI-tier")
public class GetStartGameRouteTest{

    private GetStartGameRoute CuT;

    private Request request;
    private Response response;
    private Session session;
    private TemplateEngine engine;

    private PlayerLobby lobby;
    private static final String PLAYER_1="Player1";
    private static final String PLAYER_2 ="Player2";
    private Player player;
    private Player opponent;
    private Game game;

    @BeforeEach
    public void setup(){
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        engine = mock(TemplateEngine.class);
        response = mock(Response.class);

        // Setting up game
        lobby = new PlayerLobby();
        lobby.addUsername(PLAYER_1);
        lobby.addUsername(PLAYER_2);

        game = new Game();

        player = lobby.getPlayer(PLAYER_1);
        opponent = lobby.getPlayer(PLAYER_1);

        player.setOpponent(opponent);
        opponent.setOpponent(player);

        game.setRedPlayer(player);
        game.setWhitePlayer(player);

        player.setGame(game);
        player.inGame(true);
        opponent.setGame(game);
        player.inGame(true);

        CuT = new GetStartGameRoute(engine, lobby);
    }

    /**
     * Test for start game route
     * Checks if the correct view is rendered
     * Checks if the correct variables are in the vm.
     */
    @Test
    public void startGame(){

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        testHelper.assertViewModelAttribute("title", "Let's Play");
        testHelper.assertViewModelAttribute("board", game.getBoardRed());
        testHelper.assertViewModelAttribute("currentUser", player);
        testHelper.assertViewModelAttribute("redPlayer", player);
        testHelper.assertViewModelAttribute("whitePlayer", opponent);
        testHelper.assertViewModelAttribute("viewMode", "PLAY");

        testHelper.assertViewName("game.ftl");
    }

}