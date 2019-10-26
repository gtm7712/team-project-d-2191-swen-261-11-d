package com.webcheckers.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webcheckers.model.Board;
import com.webcheckers.model.Move;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Position;
import com.webcheckers.model.Piece.Color;
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
        Move m = new Move(new Position(1, 1), new Position(2, 2));
        assertEquals(mv.validateMove(m), TurnResult.COMPLETE);
    }

    /**
     * Test a move where an invalid jump is made (nothing to jump)
     */
    @Test
    public void invalidJumpNoPiece() {
        Move m = new Move(new Position(1, 1), new Position(3, 3));
        assertEquals(mv.validateMove(m), TurnResult.FAIL);
    }

    /**
     * Test a move where an invalid jump is made (same color jumped)
     */
    @Test
    public void invalidJumpWrongPiece() {
        Piece p = new Piece(Color.RED);
        Piece j = new Piece(Color.RED);

        Move m = new Move(new Position(1, 1), new Position(3, 3));
        
        board.getSpace(1, 1).setPiece(p);
        board.getSpace(2, 2).setPiece(j);
        board.getSpace(3, 3).setPiece(null);

        assertEquals(mv.validateMove(m), TurnResult.FAIL);
    }

}