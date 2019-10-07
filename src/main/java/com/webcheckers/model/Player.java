package com.webcheckers.model;

public class Player {

    public final String name;
    private boolean inGame;  //if a user is in a game or not

    //todo

    /**
     * A Player is logged in
     * @param username of logged in player
     */
    public Player(String username) {
        this.name = username;
        this.inGame = false;
    }

    /**
     *
     * @return Players username
     */
    public String getUsername() {
        return name;
    }

    /**
     *
     * @return if a player is in game or not
     */
    public boolean isInGame() {
        return inGame;
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
