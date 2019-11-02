package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.gson.Gson;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Piece;
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
    private Gson gson;

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
        gson = new Gson();

        player = lobby.getPlayer(PLAYER_1);
        opponent = lobby.getPlayer(PLAYER_2);

        player.setOpponent(opponent);
        opponent.setOpponent(player);

        CuT = new GetStartGameRoute(engine, lobby, gson); 
        CuT.getGame().setGameOver(true);
        CuT.getGame().setRedPlayer(player);
        CuT.getGame().setWhitePlayer(opponent);
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
        when(request.queryParams(eq("otherPlayer"))).thenReturn(opponent.getName());
        when(session.attribute("Player")).thenReturn(player);        

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

        assertTrue(player.isInGame());
        assertTrue(opponent.isInGame());
               
    }

    /**
     * Test for start game route
     * Check for if a user is in a game
     */
    @Test
    public void userAlreadyInGame(){

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        
        lobby.addUsername("test");
        Player newPlayer = lobby.getPlayer("test");
        opponent.inGame(true);
        when(request.queryParams(eq("otherPlayer"))).thenReturn(opponent.getName());
        when(session.attribute("Player")).thenReturn(newPlayer);        

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        testHelper.assertViewModelAttribute("title", "Welcome!");
        testHelper.assertViewModelAttribute("allUsers", lobby.getUsernames());
        testHelper.assertViewModelAttribute("error", "Player is already in a game!");

        testHelper.assertViewName("home.ftl");          
    }

    /**
     * Test for start game route
     * Check for if the game has ended for a red player
     */
<<<<<<< HEAD
    // @Test
    public void gameStarted(){
=======
    @Test
    public void gameOver(){
>>>>>>> 84cbd592de03856144c5280f720935ce486da1a6

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        
        player.inGame(true);
        player.setBoard(CuT.getGame().getBoardRed());
        when(request.queryParams(eq("otherPlayer"))).thenReturn(opponent.getName());
        when(session.attribute("Player")).thenReturn(player);

        CuT.handle(request, response);
        
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        assertTrue(CuT.getGame().getGameStatus());
        assertFalse(player.isInGame());
        testHelper.assertViewModelAttribute("title", "Let's Play");
        testHelper.assertViewModelAttribute("viewMode", "PLAY");
        testHelper.assertViewModelAttribute("currentUser", player);
        testHelper.assertViewModelAttribute("redPlayer", player);
        testHelper.assertViewModelAttribute("whitePlayer", opponent);
        testHelper.assertViewModelAttribute("board", game.getBoardRed());     
        testHelper.assertViewModelAttribute("activeColor", Piece.Color.RED);     
                
        testHelper.assertViewName("game.ftl");          
    }

    /**
     * Test for start game route
     * Check for if the game has ended for a white player
     */
    @Test
    public void gameOverWhite(){

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        
        opponent.inGame(true);
        opponent.setBoard(CuT.getGame().getBoardWhite());
        CuT.getGame().endTurn();
        when(request.queryParams(eq("otherPlayer"))).thenReturn(player.getName());
        when(session.attribute("Player")).thenReturn(opponent);

        CuT.handle(request, response);
        
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        assertTrue(CuT.getGame().getGameStatus());
        assertFalse(player.isInGame());
        testHelper.assertViewModelAttribute("title", "Let's Play");
        testHelper.assertViewModelAttribute("viewMode", "PLAY");
        testHelper.assertViewModelAttribute("currentUser", opponent);
        testHelper.assertViewModelAttribute("redPlayer", player);
        testHelper.assertViewModelAttribute("whitePlayer", opponent);
        testHelper.assertViewModelAttribute("board", game.getBoardWhite());     
        testHelper.assertViewModelAttribute("activeColor", Piece.Color.WHITE);     
                
        testHelper.assertViewName("game.ftl");          
    }

}