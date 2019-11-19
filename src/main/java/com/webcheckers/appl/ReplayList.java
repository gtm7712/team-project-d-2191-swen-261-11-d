package com.webcheckers.appl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.webcheckers.model.Game;

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

//     private ArrayList<HeldGame>games=new ArrayList<>();
//     private HashMap<HeldGame, Integer> returnedGames=new HashMap<>();

//     /**
//      * puts the game into a list, returns  gameindex
//      * @param player1 redplayer
//      * @param player2 whiteplayer
//      * @return gameindex
//      */
//     public int createGame(Player player1, Player player2){
//         HeldGame game=new HeldGame(player1, player2);
//         games.add(game);
//         returnedGames.put(game, games.indexOf(game));
//         return games.indexOf(game);
//     }
//     public HeldGame get(int index){
//         return games.get(index);
//     }
//     public int indexOf(HeldGame game){
//         return games.indexOf(game);
//     }
//     public HashMap<HeldGame, Integer> getList(){
//         return returnedGames;
//     }
}
