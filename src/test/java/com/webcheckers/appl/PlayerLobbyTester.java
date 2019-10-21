package com.webcheckers.appl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;

import com.webcheckers.model.Player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("application-tier")
public class PlayerLobbyTester{

    @Test
    public void numericOnly(){
        final PlayerLobby CuT = new PlayerLobby();
        assertEquals(CuT.checkUsername("123"), 0);
    }

    @Test
    public void emptyName(){
        final PlayerLobby CuT = new PlayerLobby();
        assertEquals(CuT.checkUsername(""), 0);
    }
    
    @Test
    public void nullName(){
        final PlayerLobby CuT = new PlayerLobby();
        assertEquals(CuT.checkUsername(null), 0);
    }

    @Test
    public void notAvailable(){
        final PlayerLobby CuT = new PlayerLobby();
        CuT.addUsername("test");
        assertEquals(CuT.checkUsername("test"), 1);
    }
    
    @Test
    public void goodName(){
        final PlayerLobby CuT = new PlayerLobby();
        assertEquals(CuT.checkUsername("a123"), 2);
    }
    
    @Test
    public void addUsernameTest(){
        final PlayerLobby CuT = new PlayerLobby();
        CuT.addUsername("Brandon");
        assertNotNull(CuT.getUsernames(), "Player Lobby should not be null");
    }

    @Test
    public void countPlayerTest(){
        final PlayerLobby CuT = new PlayerLobby();
        HashMap<String, Player> test = new HashMap<>();
        test.put("Brandon", new Player("Brandon"));
        CuT.addUsername("Brandon");
        assertEquals(CuT.countPlayers(), test.size());
    }

    @Test
    public void getPlayerTest(){
        final PlayerLobby CuT = new PlayerLobby();
        CuT.addUsername("Brandon");
        assertEquals(CuT.getPlayer("Brandon"), new Player("Brandon"));
    }

    @Test
    public void getUsernamesTest(){
        final PlayerLobby CuT = new PlayerLobby();
        HashMap<String, Player> test = new HashMap<>();
        test.put("Brandon", new Player("Brandon"));
        CuT.addUsername("Brandon");
        assertEquals(CuT.getUsernames(), test);
    }
    
}