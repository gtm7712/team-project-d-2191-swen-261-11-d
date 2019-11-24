package com.webcheckers.appl;

import java.util.HashMap;
import java.util.Map;

/**
 * Application tier, holds the list of games that have been played and gives them an index
 *
 */
public class ReplayList {
    
    private Map<Integer, String> replayStringMap = new HashMap<>();

    /**
     * Add a game to the replay list
     * @param gameID
     *  the game id
     * @param replayString
     *  the string representation of the game
     */
    public void addGame(int gameID, String replayString) {
        replayStringMap.put(gameID, replayString);
    }

    /**
     * Get the map of games
     * @return
     *  the map of games
     */
    public Map<Integer, String> getGames() {
        return replayStringMap;
    }

    /**
     * get the replaystring associated with a game
     * @param gameID
     *  the game that you want
     * @return
     *  the string replay
     */
    public String getReplayString(int gameID) {
        return replayStringMap.get(gameID);
    }

}
