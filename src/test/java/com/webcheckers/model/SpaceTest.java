package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The unit testing suite for {@link Space}
 * 
 */
public class SpaceTest {
    private Space valid_space;
    private Space invalid_space;

    private Piece red_piece;
    private Piece white_piece;

    /**
     * Creates a valid, invalid space, red piece and white piece
     */
    @BeforeEach
    public void setup() {
        valid_space = new Space(1, 2, true);
        invalid_space = new Space(3, 4, false);
        red_piece = new Piece(Piece.Color.RED);
        white_piece = new Piece(Piece.Color.WHITE);
    }

    /**
     * Test for getter and setter for piece
     */
    @Test
    public void getSetPieceTests() {
        assertTrue(valid_space.getPiece() == null);
        valid_space.setPiece(red_piece);
        assertTrue(valid_space.getPiece() != null);
        assertTrue(valid_space.getPiece().getColor() == Piece.Color.RED);
        assertTrue(invalid_space.getPiece() == null);
    }

    /**
     * Test remove and has piece
     */
    @Test
    public void removeAndHasPieceTest() {
        valid_space.setPiece(red_piece);
        assertTrue(valid_space.hasPiece());
        valid_space.removePiece();
        assertTrue(!valid_space.hasPiece());
    }

    /**
     * Test if the piece is red
     */
    @Test
    public void isRedPieceTest() {
        assertTrue(!valid_space.isPieceRed());
        valid_space.setPiece(red_piece);
        invalid_space.setPiece(white_piece);
        assertTrue(valid_space.isPieceRed());
        assertTrue(!invalid_space.isPieceRed());
    }

    /**
     * Test for checking if space and piece are valid
     */
    @Test
    public void vailidtyTests() {
        assertTrue(valid_space.isValid());
        assertTrue(!invalid_space.isValid());
        assertTrue(valid_space.isSpaceValid());
        assertTrue(!invalid_space.isSpaceValid());
        valid_space.setPiece(red_piece);
        assertTrue(!valid_space.isValid());
        assertTrue(valid_space.isSpaceValid());
    }

    /**
     * Test for get row and cellIdx
     */
    @Test
    public void getRowCellTests() {
        assertTrue(valid_space.getRow() == 1);
        assertTrue(valid_space.getCellIdx() == 2);
    }

    /**
     * Test for checking the equality of spaces
     */
    @Test
    public void equalityTests() {
        Space other_valid = new Space(1, 2, true);
        Space other_invalid = new Space(1, 2, false);

        assertTrue(!valid_space.isCongruent(invalid_space));
        assertTrue(valid_space.isCongruent(other_valid));
        assertTrue(!valid_space.isCongruent(other_invalid));
        valid_space.setPiece(red_piece);
        assertTrue(!valid_space.isCongruent(other_valid));
        assertTrue(!valid_space.isCongruent(other_invalid));
        other_valid.setPiece(red_piece);
        other_invalid.setPiece(red_piece);
        assertTrue(valid_space.isCongruent(other_valid));
        assertTrue(!valid_space.isCongruent(other_invalid));
        other_valid.setPiece(white_piece);
        other_invalid.setPiece(white_piece);
        assertTrue(!valid_space.isCongruent(other_valid));
        assertTrue(!valid_space.isCongruent(other_invalid));
        valid_space.setPiece(white_piece);
        invalid_space.setPiece(white_piece);
        assertTrue(invalid_space.isCongruent(other_invalid));
        assertTrue(!invalid_space.equals(other_invalid));
        assertTrue(valid_space.equals(other_valid));
    }
}
