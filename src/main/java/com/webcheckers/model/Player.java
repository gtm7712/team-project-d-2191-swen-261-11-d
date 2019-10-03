package com.webcheckers.model;

public class Player {
    private String username;
    private boolean inGame;  //if a user is in a game or not

    //todo

    /**
     * A Player is logged in
     * @param username of logged in player
     */
    public Player(String username) {
        this.username = username;
        this.inGame = false;
    }

    /**
     *
     * @return Players username
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @return if a player is in game or not
     */
    public boolean isInGame() {
        return inGame;
    }

}
