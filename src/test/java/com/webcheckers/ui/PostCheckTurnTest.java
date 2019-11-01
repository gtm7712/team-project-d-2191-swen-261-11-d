package com.webcheckers.ui;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.webcheckers.appl.PlayerLobby;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

/**
 * Unit test suite for {@link PostCheckTurn} component
 *
 * @author Kris Schnupp
 */
@Tag("UI-tier")
public class PostCheckTurnTest {
    private Request request;
    private Response response;
    private Session session;
    private PostCheckTurn CuT;
    private final Gson gson;

    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        when(session.attribute("Player")).thenReturn(player);

        gson = new Gson();
        CuT = new PostCheckTurn(gson);
    }
    @test
    public void checkTurn(){
        
    }
}