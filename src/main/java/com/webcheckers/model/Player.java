package com.webcheckers.model;

public class Player {

    public String name;
    private boolean inGame;  //if a user is in a game or not
    private Player opponent;
    private Game game;
    private Board board;
    //todo

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

    public void resign(){
        this.game.setGameOver(true);
        this.game.setWinner(opponent);
        this.inGame = false;
        this.opponent.inGame(false);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

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

    @Override
    public String toString() {
        return name;
    }
}
