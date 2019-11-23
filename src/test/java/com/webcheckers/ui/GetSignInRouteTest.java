package com.webcheckers.ui;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.webcheckers.appl.PlayerLobby;
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
 * Unit test suite for {@link GetSignInRoute} component
 * 
 * @author Brandon Chen
 */
@Tag("UI-tier")
public class GetSignInRouteTest{

    private GetSigninRoute CuT;

    private Request request;
    private Response response;
    private Session session;
    private TemplateEngine engine;

    private PlayerLobby lobby;

    /**
     * setup for the getsignin route test
     */
    @BeforeEach
    public void setup(){
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        engine = mock(TemplateEngine.class);
        response = mock(Response.class);
        lobby = new PlayerLobby();
        
        CuT = new GetSigninRoute(engine);
    }

    /**
     * Test for signin route
     * Checks if the correct view is rendered
     * Checks if the correct variables are in the vm.
     */
    @Test
    public void signIn(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        testHelper.assertViewModelAttribute("title","Sign In!");

        testHelper.assertViewName("signin.ftl");
    }
    


}
