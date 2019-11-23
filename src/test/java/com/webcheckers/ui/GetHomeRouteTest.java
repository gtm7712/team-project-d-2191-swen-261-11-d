package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.webcheckers.appl.GameList;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.ReplayList;
import com.webcheckers.model.Game;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Player;
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
 * Unit test suite for {@link GetHomeRoute} component
 * 
 * @author Brandon Chen
 */
@Tag("UI-tier")
public class GetHomeRouteTest{

    private static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");

    private GetHomeRoute CuT;

    private Request request;
    private Response response;
    private Session session;
    private TemplateEngine engine;
    private Player player;

    private PlayerLobby lobby;

    /**
     * setup for the home route test
     */
    @BeforeEach
    public void setup(){
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        engine = mock(TemplateEngine.class);
        response = mock(Response.class);
        lobby = new PlayerLobby();
        
        CuT = new GetHomeRoute(engine, lobby, new GameList(), new ReplayList());
    }

    /**
     * Test for home route
     * Checks if the correct view is rendered
     * Checks if the correct variables are in the vm.
     */
    @Test
    public void firstTime(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        when(session.attribute("Player")).thenReturn(null);

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        testHelper.assertViewModelAttribute("title","Welcome!");
        testHelper.assertViewModelAttribute("allUsers",lobby.getUsernames());
    
        testHelper.assertViewName("home.ftl");
    }

    /**
     * Test for home route if a user is logged in
     * Checks if the correct view is rendered
     * Checks if the correct variables are in the vm.
     */
    @Test
    public void userLoggedIn(){
        lobby.addUsername("player1");
        player = lobby.getPlayer("player1");
        lobby.addUsername("player2");
        Player player2 = lobby.getPlayer("player2");
        Game game = new Game();
        player.setGame(game);
        player.inGame(true);

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        when(session.attribute("Player")).thenReturn(player);

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        testHelper.assertViewModelAttribute("title", "Let's Play");
        testHelper.assertViewModelAttribute("allUsers", null);
        testHelper.assertViewModelAttribute("currentUser", player);
        testHelper.assertViewModelAttribute("board", player.getFlippedBoard());
        testHelper.assertViewModelAttribute("redPlayer", null);
        testHelper.assertViewModelAttribute("whitePlayer", null);
        testHelper.assertViewModelAttribute("activeColor", Piece.Color.RED);
    
        testHelper.assertViewName("game.ftl");
    }
    
    /**
     * Test for home route for if someone is not in a game
     * Checks if the correct view is rendered
     * Checks if the correct variables are in the vm.
     */
    @Test
    public void notInGame(){
        lobby.addUsername("player1");
        player = lobby.getPlayer("player1");
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        when(session.attribute("Player")).thenReturn(player);
        Game game = new Game();
        player.setGame(game);
        player.inGame(false);

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        testHelper.assertViewModelAttribute("title","Welcome!");
        testHelper.assertViewModelAttribute("allUsers",lobby.getUsernames());
    
        testHelper.assertViewName("home.ftl");
        assertTrue(lobby.countPlayers() == 1);
    }

}
