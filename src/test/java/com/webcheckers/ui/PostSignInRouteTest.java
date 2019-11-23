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
    private static final String NUMBERS="123";
    private static final String NAME="Bob";

    /**
     * setup for a signinroute test
     */
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
    
    /**
     * checks if you can signin with numbers only
     */
    @Test
    public void SignInNumbers(){
        when(request.queryParams(eq(PostSignInRoute.USERNAME))).thenReturn(NUMBERS);
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute("title","Sign In!");
        //testHelper.assertViewModelAttribute("message", Message.info("Please select a username."));
        //testHelper.assertViewModelAttribute(PostSignInRoute.USERNAME, "123");
        testHelper.assertViewModelAttribute("logIN", PostSignInRoute.INVALID_USERNAME);
        testHelper.assertViewName("signin.ftl");
        when(request.queryParams(eq(PostSignInRoute.USERNAME))).thenReturn("bob");
    }

    /**
     * test for valid signin
     */
    @Test
    public void SignInValid(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(request.queryParams(eq(PostSignInRoute.USERNAME))).thenReturn(NAME);
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute("title","Sign In!");
        //testHelper.assertViewModelAttribute("message", Message.info("Please select a username."));
        //testHelper.assertViewModelAttribute(PostSignInRoute.USERNAME, "Bob");
        testHelper.assertViewModelAttribute("logIN", PostSignInRoute.USERNAME_GOOD);
        testHelper.assertViewName("signin.ftl");
    }
    
    /**
     * test for signin when using a name that already exists
     */
    @Test
    public void NameInUse(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();

        lobby.addUsername(NAME);
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        when(request.queryParams(eq(PostSignInRoute.USERNAME))).thenReturn(NAME);
        CuT.handle(request, response);
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute("title","Sign In!");
        testHelper.assertViewModelAttribute("logIN", PostSignInRoute.USERNAME_IN_USE);
        testHelper.assertViewName("signin.ftl");
    }
}
