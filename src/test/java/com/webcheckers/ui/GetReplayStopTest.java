package com.webcheckers.ui;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.gson.Gson;
import com.webcheckers.appl.GameList;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.ReplayList;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import com.webcheckers.util.ReplayHelper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

/**
 * Unit test suite for {@link GetReplayStop} component
 * 
 * @author Brandon Chen
 */
@Tag("UI-tier")
public class GetReplayStopTest{

    private GetReplayStopRoute CuT;

    private Request request;
    private Response response;
    private Session session;
    private TemplateEngine engine;
    private Gson gson;
    private GameList gameList;
    private Player player1;
    private Player player2;
    private ReplayHelper replay;
    private Game game;
    private PlayerLobby lobby;
    private ReplayList replays;

    @BeforeEach
    public void setup(){
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        engine = mock(TemplateEngine.class);
        response = mock(Response.class);

        gson = new Gson();
        gameList = new GameList();
        player1 = new Player("player1");
        player2 = new Player("player2");
        replay = new ReplayHelper();
        game = new Game();
        lobby = new PlayerLobby();
        replays = new ReplayList();

        game.setRedPlayer(player1);
        game.setWhitePlayer(player2);
        replay.loadReplay("b;a;+r43:-54;-23:+w32;+r34:-43;-25:-34:+w43;yW");
        game.setReplay(replay);
        gameList.addGame(game);
        replays.addGame(0, "b;a;+r43:-54;-23:+w32;+r34:-43;-25:-34:+w43;yW");
        lobby.addUsername("player1");
        CuT = new GetReplayStopRoute(engine, lobby, replays, gameList);
    }

    /**
     * Test for replay route
     * Checks if the correct view is rendered
     * Checks if the correct variables are in the vm.
     */
    @Test
    public void stop(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        when(request.queryParams(eq("gameID"))).thenReturn("0");
        when(session.attribute("Player")).thenReturn(player1);

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        testHelper.assertViewModelAttribute("title","Welcome!");
        testHelper.assertViewName("home.ftl");
    }
    


}
