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

    private final String INVALID_USERNAME = "This username is not valid!  Usernames must be alphanumeric.";
    private final String USERNAME_IN_USE = "Pick another username, this one is already in use.";
    private final String USERNAME_GOOD = "Logged in";

    /**
     * Checks if a username is valid
     * 
     * @param username
     * @return String that says if your username is good or not and why if it is not
     *         good
     */
    public String checkUsername(String username) {
        if (username == null || username.isEmpty()) {
            return INVALID_USERNAME;
        }

        for (int i = 0; i < username.length(); i++) {
            if (!(Character.isLetterOrDigit(username.charAt(i)) || username.charAt(i) == ' ')) {
                return INVALID_USERNAME;
            }
        }
        if (loggedIn.containsKey(username)) {
            return USERNAME_IN_USE;
        } else {
            return USERNAME_GOOD;
        }
    }

    /**
     * Takes a username and adds it to the logged in array if it is a valid name
     * 
     * @param username to be logged in
     * @return if username is added or not, and why if it is not added
     */
    public String addUsername(String username) {
        if (checkUsername(username) == USERNAME_GOOD) {
            loggedIn.put(username, new Player(username));
            return USERNAME_GOOD;
        } else {
            return checkUsername(username);
        }
    }

}
