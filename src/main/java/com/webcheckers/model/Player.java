package com.webcheckers.model;

public class Player {

    public String name;
    private boolean inGame;  //if a user is in a game or not
    private Player opponent;
    private Game game;
    private Board board;

    /**
     * A Player is logged in
     * @param name of logged in player
     */
    public Player(String name) {
        this.name = name;
        this.inGame = false;
    }

    /**
     *
     * @return Players username
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return if a player is in game or not
     */
    public boolean isInGame() {
        return inGame;
    }

    /**
     * sets player to be in/ out of  game
     * @param inGame
     */
    public void inGame(boolean inGame){
        this.inGame = inGame;
    }

    /**
     * Get the opponent of the player
     * @return the player's opponent
     */
    public Player getOpponent(){
        return opponent;
    }

    /**
     * sets the Players opponent if you are in game
     * @param opponent a Player that you are playing in checkers
     */
    public void setOpponent(Player opponent){
        this.opponent = opponent;
    }

    /**
     *
     * @return the board of the game you are playing
     */
    public Board getBoard(){
        return this.game.getBoardRed();
    }
 
    /**
     *
     * @return the board of the game you are playing
     */
    public Board getFlippedBoard(){
        return this.game.getBoardWhite();
    }

    /**
     *
     * @return the board of the game you are playing
     */
    public void setBoard(Board board){
        this.board = board;
    }

    /**
     *
     * @return get player board
     */
    public Board getPlayerBoard(){
        return this.board;
    }

    /**
     *
     * @return the game you are playing
     */
    public Game getGame(){
        return this.game;
    }

    /**
     * sets the game you are playing
     * @param game
     */
    public void setGame(Game game){
        this.game = game;
    }

    /**
     * Resign the game by setting the game to over
     */
    public void resign(){
        this.game.setGameOver(true);
        this.inGame = false;
        this.game = null;
    }

    /**
     * hash function for players
     * @return the hashed value
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * equals method for two players
     * @return true if the players are equal, else false
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this){
            return true;
        }
        if (!(obj instanceof Player)){
            return false;
        }
        final Player temp = (Player) obj;
        return temp.name.equals(this.name);
    }

    /**
     * toString method for a player
     * @return the string representation of a player
     */
    @Override
    public String toString() {
        return name;
    }
}
