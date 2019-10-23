package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Testing suite for @link{Move}
 */
@Tag("model-tier")
public class MoveTest {

    /**
     * Tests the equals function
     */
    @Test
    public void equals() {
        Position p1 = new Position(1, 2);
        Position p2 = new Position(6, 7);

        Move m1 = new Move(p1, p2);
        Move m2 = new Move(p1, p2);
        Move m3 = new Move(p2, p1);

        assertFalse(m1.equals(5));
        assertTrue(m1.equals(m2));
        assertFalse(m1.equals(m3));
    }
}