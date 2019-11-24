package com.webcheckers.appl;

import java.util.HashMap;
import java.util.Map;

import com.webcheckers.model.Game;

public class GameList {

    private Map<Integer, Game> games = new HashMap<>();
    private Map<String, Game> gamesString = new HashMap<>();
    private int currentID = 0;

    /**
     * Adds a game to the map of games 
     * @param game
     *  The game to be added
     * @return
     *  The game id associated with that game
     */
    public int addGame(Game game) {
        games.put(currentID, game);
        gamesString.put(currentID + "", game);
        return currentID++;
    }

    /**
     * Get a game given an id
     * @param gameID
     *  the id associated with the game
     * @return
     *  the game
     */
    public Game getGame(int gameID) {
        return games.get(gameID);
    }

    /**
     * Get the map of games
     */
    public Map<Integer, Game> getGames() {
        return games;
    }

    public Map<String, Game> getGamesString() {
        return gamesString;
    }

}