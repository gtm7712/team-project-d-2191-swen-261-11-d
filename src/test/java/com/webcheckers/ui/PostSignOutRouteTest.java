package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.webcheckers.appl.PlayerLobby;
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
 * Unit test suite for {@link PostSignOutRoute} component
 *
 * @author Brandon Chen
 */
@Tag("UI-tier")
public class PostSignOutRouteTest {

    private PostSignOutRoute CuT;

    private Request request;
    private Response response;
    private Session session;
    private TemplateEngine engine;

    private PlayerLobby lobby;
    private Player testPlayer;

    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        engine = mock(TemplateEngine.class);
        response = mock(Response.class);
        lobby = new PlayerLobby();
        lobby.addUsername("test1");

        testPlayer = lobby.getPlayer("test1");
        CuT = new PostSignOutRoute(engine, lobby);
    }

    /**
     * Test for signout
     * Checks if the lobby is not null at first and when the request is handled, check if the lobby is empty
     * Checks the correct VM variables
     * Checks if the correct view is loaded
     */
    @Test
    public void signOut(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        
        when(session.attribute("Player")).thenReturn(testPlayer);     
        assertNotNull(lobby.getPlayer("test1"), "lobby should not be null!");
        CuT.handle(request, response);
        
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        testHelper.assertViewModelAttribute("title", "Welcome!");
        testHelper.assertViewModelAttribute("allUsers", lobby.getUsernames());
        
        testHelper.assertViewName("home.ftl");            
    
        assertTrue(lobby.countPlayers() == 0);
        assertTrue(lobby.checkUsername("test1") == 2);
    }
}
