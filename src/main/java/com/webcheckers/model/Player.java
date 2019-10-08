package com.webcheckers.model;

public class Player {

    public String name;
    private boolean inGame;  //if a user is in a game or not

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
