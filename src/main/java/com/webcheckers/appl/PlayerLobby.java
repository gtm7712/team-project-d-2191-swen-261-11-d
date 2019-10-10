package com.webcheckers.appl;
import com.webcheckers.model.Player;

import java.util.HashMap;

/**
 * Application tier, controls and holds who is logged in, and if names are valid
 * to be logged in
 *
 */
public class PlayerLobby {
    private HashMap<String, Player> loggedIn = new HashMap<>(); // logged in people



    /**
     * Checks if a username is valid
     * 
     * @param username
     * @return String that says if your username is good or not and why if it is not
     *         good
     */
    public int checkUsername(String username) {
        if (username == null || username.isEmpty()) {
            return 0;
        }
        int numChars = 0;
        for (int i = 0; i < username.length(); i++) {
            if (!(Character.isLetterOrDigit(username.charAt(i)) || username.charAt(i) == ' ')) {
                return 0;
            }
            if(Character.isLetter(username.charAt(i))){
                numChars++;
            }
        }
        if(numChars == 0){
            return 0;
        }
        if (loggedIn.containsKey(username)) {
            return 1;
        } else {
            return 2;
        }
    }

    /**
     * Takes a username and adds it to the logged in array if it is a valid name
     * 
     * @param username to be logged in
     * @return if username is added or not, and why if it is not added
     */
    public void addUsername(String username) {
        loggedIn.put(username, new Player(username));
    }

    /**
     *
     * @return how many players are logged in
     */
    public int countPlayers(){
        return loggedIn.size();
    }

    /**
     *
     * @param name
     * @return the logged in Player with that name
     */

    public Player getPlayer(String name){
        return loggedIn.get(name);
    }

    /**
     *
     * @return The hashmap of all the logged in users
     */
    public HashMap<String, Player> getUsernames() {
        return loggedIn;
    }

}
