package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit test suite for {@link Board} component
 * 
 * @author Giovanni Melchionne
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
        Space e = board.getSpace(0,0);
        Space f = flipped.getSpace(SIZE, SIZE);
        assertTrue(e.isCongruent(f),
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
        assertTrue(b.equals(flipped) == false,
            "Flipped board shouldn't equal non-flipped board");
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

}