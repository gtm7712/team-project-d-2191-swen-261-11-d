package com.webcheckers.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webcheckers.model.Board;
import com.webcheckers.model.Move;
import com.webcheckers.model.Position;
import com.webcheckers.util.MoveValidator.TurnResult;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test suite for the @link{MoveValidator}
 */
public class MoveValidatorTest {

    MoveValidator mv;
    Board board;

    /**
     * Set up a new board amd MoveValidator instance for each test
     */
    @BeforeEach
    public void setup() {
        board = new Board();
        mv = new MoveValidator(board);
    }

    /**
     * Test a move where no jump is made
     */
    @Test
    public void noJump() {
        Move m = new Move(new Position(1, 1), new Position(2, 2));assertEquals(mv.validateMove(m), TurnResult.COMPLETE);
        assertEquals(mv.validateMove(m), TurnResult.COMPLETE);
    }

    /**
     * Test a move where an invalid jump is made
     */
    @Test
    public void invalidJumpNoPiece() {
        Move m = new Move(new Position(1, 1), new Position(3, 3));
        assertEquals(mv.validateMove(m), TurnResult.FAIL);
    }

}