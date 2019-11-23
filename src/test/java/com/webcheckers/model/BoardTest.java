package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit test suite for {@link Board} component
 * 
 * @author Giovanni Melchionne
 * @author Kyle Collins
 */
@Tag ("model-tier")
public class BoardTest {

    private Board board;
    private Board flipped;

    // for shorter name and 0-indexing
    private final int SIZE = Board.BOARD_SIZE - 1;

    /**
     * Create a new Board and Flipped Board for each test
     */
    @BeforeEach
    public void setup() {
        board = new Board();
        flipped = new Board().flipped();
    }

    /**
     * Test the Flipped() function of Board
     */
    @Test
    public void flip() {
        assertTrue(board.getSpace(0, 0).isCongruent(flipped.getSpace(SIZE, SIZE)),
                "Spaces on opposite corners should be equal");
    }

    /**
     * Test the Equals() function of Board
     */
    @Test
    public void equals() {
        Board b = new Board();
        // Equal test
        assertTrue(b.equals(board),
            "New boards should be equal to each other");
        // Not equal test
        assertFalse(b.equals(flipped),
            "Flipped board shouldn't equal non-flipped board");

        assertFalse(b.equals(5),
            "A board shouldn't equal a non-board object");
    }

    /**
     * Test the boards iterator
     */
    @Test
    public void iter() {
        assertNotNull(board.iterator(),
            "Iterator shouldn't be null");
    }


    /**
     * Test the preset constructor
     */
    @Test
    public void preset() {
        // use flipped as preset
        ArrayList<Row> preset = flipped.getBoard();
        Board b = new Board(preset);

        // Equal test
        assertTrue(b.equals(flipped),
            "Board created by preset should equal preset");

        // Flip b
        assertTrue(b.flipped().equals(board),
            "Flipped flipped board should equal unflipped board");
    }

    /**
     * Test getSpace null
     */
    @Test
    public void getSpaceNullTest() {

        Board b = new Board();
        
        assertNull(b.getSpace(100,100),
            "Space should be null");

    }

    /**
     * Test getSpace null
     */
    @Test
    public void toStringTest() {

        Board b = new Board();
        System.out.println(b);

    }



}