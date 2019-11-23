package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The unit testing suite for {@link Piece}
 * 
 */
public class PieceTest {
    private Piece red_piece;
    private Piece white_piece;

    /**
     * Setup consisting of creating red and white pieces
     */
    @BeforeEach
    public void setup() {
        red_piece = new Piece(Piece.Color.RED);
        white_piece = new Piece(Piece.Color.WHITE);
    }

    /**
     * Test for return the color of a piece
     */
    @Test
    public void getColorTest() {
        assertTrue(red_piece.getColor() == Piece.Color.RED);
        assertTrue(white_piece.getColor() == Piece.Color.WHITE);
    }

    /**
     * Test to determine whether the piece is red
     */
    @Test
    public void isRedTest() {
        assertTrue(red_piece.isRed());
        assertTrue(!white_piece.isRed());
    }

    /**
     * Test to determine whether the piece is a kind
     */
    @Test
    public void kingTest() {
        assertTrue(!red_piece.isKing());
        red_piece.king();
        assertTrue(red_piece.isKing());
        assertTrue(!white_piece.isKing());
    }


    /**
     * Test to determine the pieces type
     */
    @Test
    public void typeTest() {
        assertTrue(red_piece.getType() == Piece.Type.SINGLE);
        red_piece.king();
        assertTrue(red_piece.getType() == Piece.Type.KING);
        assertTrue(white_piece.getType() == Piece.Type.SINGLE);
    }
}
