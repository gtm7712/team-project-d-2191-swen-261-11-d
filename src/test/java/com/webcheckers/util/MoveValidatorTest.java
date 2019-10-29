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
        board.getSpace(1, 1).setPiece(new Piece(Color.RED));
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

    @Test
    public void validSingleJump() {
        Piece p = new Piece(Color.RED);
        Piece j = new Piece(Color.WHITE);

        Move m = new Move(new Position(1, 1), new Position(3, 3));
        
        board.getSpace(1, 1).setPiece(p);
        board.getSpace(2, 2).setPiece(j);
        board.getSpace(3, 3).setPiece(null);

        assertEquals(mv.validateMove(m), TurnResult.COMPLETE);
    }

    @Test
    public void upRight() {
        Piece p = new Piece(Color.RED);
        Piece j = new Piece(Color.WHITE);
        Piece j2 = new Piece(Color.WHITE);

        Move m = new Move(new Position(1, 1), new Position(3, 3));
        
        board.getSpace(1, 1).setPiece(p);
        board.getSpace(2, 2).setPiece(j);
        board.getSpace(3, 3).setPiece(null);
        board.getSpace(4, 4).setPiece(j2);

        assertEquals(mv.validateMove(m), TurnResult.CONTINUE);
    }

    @Test
    public void upLeft() {
        Piece p = new Piece(Color.RED);
        Piece j = new Piece(Color.WHITE);
        Piece j2 = new Piece(Color.WHITE);

        Move m = new Move(new Position(1, 1), new Position(3, 3));
        
        board.getSpace(1, 1).setPiece(p);
        board.getSpace(2, 2).setPiece(j);
        board.getSpace(3, 3).setPiece(null);
        board.getSpace(4, 2).setPiece(j2);

        assertEquals(mv.validateMove(m), TurnResult.CONTINUE);
    }

    @Test
    public void downRight() {
        Piece p = new Piece(Color.RED);
        p.king();
        Piece j = new Piece(Color.WHITE);
        Piece j2 = new Piece(Color.WHITE);

        Move m = new Move(new Position(4, 4), new Position(2, 2));
        
        board.getSpace(4, 4).setPiece(p);
        board.getSpace(3, 3).setPiece(j);
        board.getSpace(2, 2).setPiece(null);
        board.getSpace(1, 3).setPiece(j2);

        assertEquals(mv.validateMove(m), TurnResult.CONTINUE);
    }

    @Test
    public void downLeft() {
        Piece p = new Piece(Color.RED);
        p.king();
        Piece j = new Piece(Color.WHITE);
        Piece j2 = new Piece(Color.WHITE);

        Move m = new Move(new Position(4, 4), new Position(2, 2));
        
        board.getSpace(4, 4).setPiece(p);
        board.getSpace(3, 3).setPiece(j);
        board.getSpace(2, 2).setPiece(null);
        board.getSpace(1, 1).setPiece(j2);

        assertEquals(mv.validateMove(m), TurnResult.CONTINUE);
    }

    @Test
    public void king() {
        Piece p = new Piece(Color.RED);
        board.getSpace(6, 6).setPiece(p);
        Move m = new Move(new Position(6, 6), new Position(7, 7));
        assertEquals(mv.validateMove(m), TurnResult.KING);
    }

    @Test
    public void kingOnJump() {
        Piece p = new Piece(Color.RED);
        Piece j = new Piece(Color.WHITE);
        
        board.getSpace(5, 5).setPiece(p);
        board.getSpace(6, 6).setPiece(j);
        Move m = new Move(new Position(5, 5), new Position(7, 7));
        assertEquals(mv.validateMove(m), TurnResult.KING);
    }
    
    @Test
    public void invalidNoJumpWhenJumpPossible() {
        Piece p = new Piece(Color.RED);
        Piece p2 = new Piece(Color.RED);
        Piece j = new Piece(Color.WHITE);
        
        Move m = new Move(new Position(4, 4), new Position(5, 5));
        
        board.getSpace(4, 4).setPiece(p2);
        board.getSpace(5, 5).setPiece(null);

        board.getSpace(1, 1).setPiece(p);
        board.getSpace(2, 2).setPiece(j);
        board.getSpace(3, 3).setPiece(null);

        assertEquals(mv.validateMove(m), TurnResult.FAIL);
    }

}