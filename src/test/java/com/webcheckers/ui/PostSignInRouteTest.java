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
 * Unit test suite for {@link PostSignInRoute} component
 *
 * @author Kris Schnupp
 */
@Tag("UI-tier")
public class PostSignInRouteTest {

    private PostSignInRoute CuT;

    private Request request;
    private Response response;
    private Session session;
    private TemplateEngine engine;

    private PlayerLobby lobby;

    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        engine = mock(TemplateEngine.class);
        response = mock(Response.class);
        lobby = new PlayerLobby();

        CuT = new PostSignInRoute(engine, lobby);
    }
    @Test
    public void SignIn(){

    }
}
