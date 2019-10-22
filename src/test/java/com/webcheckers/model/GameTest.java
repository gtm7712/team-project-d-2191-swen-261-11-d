package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit testing suite for {@link Game}
 * 
 * @author Giovanni Melchionne
 */
@Tag ("Model-tier")
public class GameTest {

    private Game game;
    private Player pRed;
    private Player pWhite;
    
    /**
     * Set up a generic Game for each test
     */
    @BeforeEach
    public void setup() {
        game = new Game();
        pRed= new Player("John");
        pWhite = new Player("Mary");
        game.setRedPlayer(pRed);
        game.setWhitePlayer(pWhite);
    }

    /**
     * Test that the board is not null
     */
    @Test
    public void board_not_null_r() {
        assertNotNull(game.getBoardRed(),
            "Board shouldn't be null");
    }

    /**
     * Test that the flipped board is not null
     */
    @Test
    public void board_not_null_w() {
        assertNotNull(game.getBoardWhite(),
            "Board shouldn't be null");
    }

    /**
     * Test that the red and white boards aren't equal
     */
    @Test
    public void boards_not_equal() {
        assertFalse(game.getBoardRed().equals(game.getBoardWhite()),
            "Red board & White board shouldn't be equal");
    }

    /**
     * Null testing on the Player objects
     */
    @Test
    public void players_null() {
        assertNotNull(game.getRedPlayer(),
            "Red player should not be null");
        assertNotNull(game.getWhitePlayer(),
            "White player should not be null");

        Game g = new Game();
        assertNull(g.getRedPlayer(),
            "Red player should be null");
        assertNull(g.getWhitePlayer(),
            "White player should be null");
    }

}