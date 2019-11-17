package com.webcheckers.model;

import com.webcheckers.model.Player;
import com.webcheckers.model.Turn;

import java.util.ArrayList;

public class HeldGame {
    private ArrayList<Turn> game=new ArrayList<>();
    private Player whitePlayer;
    private boolean isGameOver=false;
    private Player redPlayer;
    private Player winner=null;
    public HeldGame(Player redPlayer, Player whitePlayer){
        this.redPlayer=redPlayer;
        this.whitePlayer=whitePlayer;
    }

    /**
     *
     * @return the red player of game
     */
    public Player getRedPlayer() {
        return redPlayer;
    }

    /**
     *
     * @return list of turns that happened during the game
     */
    public ArrayList<Turn> getGame() {
        return game;
    }

    /**
     *
     * @return white player of game
     */
    public Player getWhitePlayer() {
        return whitePlayer;
    }

    /**
     *
     * @return if the game is over or not
     */
    public boolean isGameOver() {
        return isGameOver;
    }

    /**
     * sets who won the game
     * @param winningPlayer winning player
     */
    public void setWinner(Player winningPlayer){
        isGameOver=true;
        winner=winningPlayer;
    }
    public void add(Turn turn){
        game.add(turn);
    }
    public String toString(){
        return redPlayer.name + " versus " + whitePlayer.name;
    }
}
