package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SpaceTest {
    private Space valid_space;
    private Space invalid_space;

    private Piece red_piece;

    @BeforeEach
    public void setup() {
        valid_space = new Space(1, 2, true);
        invalid_space = new Space(3, 4, false);
        red_piece = new Piece(Piece.Color.RED);
    }

    @Test
    public void getSetPieceTests() {
        assertTrue(valid_space.getPiece() == null);
        valid_space.setPiece(red_piece);
        assertTrue(valid_space.getPiece() != null);
        assertTrue(valid_space.getPiece().getColor() == Piece.Color.RED);
        assertTrue(invalid_space.getPiece() == null);
    }
/*
    @Test
    public void removeAndHasPieceTest() {
        assertTrue(!valid_space.hasPiece());
        valid_space.removePiece();
        assertTrue(valid_space.hasPiece());
    }
    */
}
