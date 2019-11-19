package com.webcheckers.appl;

import java.util.HashMap;
import java.util.Map;

import com.webcheckers.model.Game;

public class GameList {

    private Map<Integer, Game> games = new HashMap<>();
    private int currentID = 0;

    public int addGame(Game game) {
        games.put(currentID, game);
        return currentID++;
    }

    public Game getGame(int gameID) {
        return games.get(gameID);
    }

}