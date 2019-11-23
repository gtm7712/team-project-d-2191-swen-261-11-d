package com.webcheckers.appl;

import java.util.HashMap;
import java.util.Map;

/**
 * Application tier, holds the list of games that have been played and gives them an index
 *
 */
public class ReplayList {
    
    private Map<Integer, String> replayStringMap = new HashMap<>();

    public void addGame(int gameID, String replayString) {
        replayStringMap.put(gameID, replayString);
    }

    public Map<Integer, String> getGames() {
        return replayStringMap;
    }

    public String getReplayString(int gameID) {
        return replayStringMap.get(gameID);
    }

}
