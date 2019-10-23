package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test suite for @link{Position}
 */
@Tag("model-tier")
public class PositionTest {

    /**
     * Tests the equals function
     */
    @Test
    public void equals() {
        Position p1 = new Position(1, 2);
        Position p2 = new Position(1, 2);
        Position p3 = new Position(6, 7);

        assertFalse(p1.equals(6));
        assertTrue(p1.equals(p2));
        assertFalse(p1.equals(p3));

    }

}