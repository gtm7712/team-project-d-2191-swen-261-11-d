package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.ReplayList;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.model.TestReplay;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

public class GetStartReplayRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetStartReplayRoute.class.getName());
    TemplateEngine templateEngine;
    PlayerLobby lobby;
    Gson gson;
    TestReplay replay;
    ReplayList gameList;
    public GetStartReplayRoute(final TemplateEngine templateEngine, final PlayerLobby lobby, final Gson gson, final ReplayList gameList) {
            this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
            this.lobby = lobby;
            this.gson = gson;
            this.replay = new TestReplay();
            this.gameList=gameList;
            //
            LOG.config("GetStartReplayRoute is initialized.");
    }
    @Override
    public Object handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();
        Player currentPlayer = request.session().attribute("Player");
        vm.put("viewMode", "REPLAY");
        return templateEngine.render(new ModelAndView(vm , "game.ftl"));
    }
}
