package com.webcheckers.appl;

import com.webcheckers.model.HeldGame;
import com.webcheckers.model.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Application tier, holds the list of games that have been played and gives them an index
 *
 */
public class ReplayList {
    private ArrayList<HeldGame>games=new ArrayList<>();
    private HashMap<HeldGame, Integer> returnedGames=new HashMap<>();

    /**
     * puts the game into a list, returns  gameindex
     * @param player1 redplayer
     * @param player2 whiteplayer
     * @return gameindex
     */
    public int createGame(Player player1, Player player2){
        HeldGame game=new HeldGame(player1, player2);
        games.add(game);
        returnedGames.put(game, games.indexOf(game));
        return games.indexOf(game);
    }
    public HeldGame get(int index){
        return games.get(index);
    }
    public int indexOf(HeldGame game){
        return games.indexOf(game);
    }
    public HashMap<HeldGame, Integer> getList(){
        return returnedGames;
    }
}
