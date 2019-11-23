package com.webcheckers.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webcheckers.model.Board;
import com.webcheckers.model.Game;
import com.webcheckers.model.Move;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Player;
import com.webcheckers.model.Position;
import com.webcheckers.model.Piece.Color;
import com.webcheckers.util.ValidationResult.TurnResult;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test suite for the @link{MoveValidator}
 */
public class MoveValidatorTest {

    MoveValidator mv;
    Board board;
    Game game;
    Player player1;
    Player player2;

    /**
     * Set up a new board amd MoveValidator instance for each test
     */
    @BeforeEach
    public void setup() {
        player1 = new Player("Thomas");
        player2 = new Player("Mitsuha");

        game = new Game();
        game.setRedPlayer(player1);
        game.setWhitePlayer(player2);
        // game.__test_set_white_turn();

        board = game.getBoardRed();
        mv = new MoveValidator(game);
    }

    /**
     * Test a move where no jump is made
     */
    @Test
    public void noJump() {
        board.getSpace(5, 1).setPiece(new Piece(Color.WHITE));
        Move m = new Move(new Position(5, 1), new Position(4, 2));
        assertEquals(mv.validateMove(m).getTurnResult(), TurnResult.COMPLETE);
    }

    /**
     * Test a backwards jump, not kinged
     */
    @Test
    public void backwards() {
        board.getSpace(4, 2).setPiece(new Piece(Color.WHITE));
        Move m = new Move(new Position(4, 2), new Position(5, 1));
        assertEquals(mv.validateMove(m).getTurnResult(), TurnResult.FAIL);
    }

    /**
     * Test a backwards jump, kinged
     */
    @Test
    public void backwardsKing() {
        Piece p = new Piece(Color.WHITE);
        p.king();
        board.getSpace(4, 2).setPiece(p);
        Move m = new Move(new Position(4, 2), new Position(5, 1));
        assertEquals(mv.validateMove(m).getTurnResult(), TurnResult.COMPLETE);
    }

    /**
     * Test a move where an invalid jump is made (nothing to jump)
     */
    @Test
    public void invalidJumpNoPiece() {
        board.getSpace(1, 1).setPiece(new Piece(Color.RED));
        Move m = new Move(new Position(1, 1), new Position(3, 3));
        assertEquals(mv.validateMove(m).getTurnResult(), TurnResult.FAIL);
    }

    /**
     * Test a move where an invalid jump is made (same color jumped)
     */
    @Test
    public void invalidJumpWrongPiece() {
        Piece p = new Piece(Color.RED);
        Piece j = new Piece(Color.RED);

        Move m = new Move(new Position(3, 3), new Position(1, 1));
        
        board.getSpace(3, 3).setPiece(p);
        board.getSpace(2, 2).setPiece(j);
        board.getSpace(1, 1).setPiece(null);

        assertEquals(mv.validateMove(m).getTurnResult(), TurnResult.FAIL);
    }

    /**
     * Test a valid 1-jump move
     */
    @Test
    public void validSingleJump() {
        Piece p = new Piece(Color.RED);
        Piece j = new Piece(Color.WHITE);

        Move m = new Move(new Position(3, 3), new Position(1, 1));
        
        board.getSpace(3, 3).setPiece(p);
        board.getSpace(2, 2).setPiece(j);
        board.getSpace(1, 1).setPiece(null);

        assertEquals(mv.validateMove(m).getTurnResult(), TurnResult.COMPLETE);
    }

    /**
     * Test a valid jump to the up-right direction
     */
    @Test
    public void upRight() {
        Piece p = new Piece(Color.WHITE);
        Piece j = new Piece(Color.RED);
        Piece j2 = new Piece(Color.RED);

        game.__test_set_white_turn();

        Move m = new Move(new Position(1, 1), new Position(3, 3));
        
        board.getSpace(1, 1).setPiece(p);
        board.getSpace(2, 2).setPiece(j);
        board.getSpace(3, 3).setPiece(null);
        board.getSpace(4, 4).setPiece(j2);

        assertEquals(mv.validateMove(m).getTurnResult(), TurnResult.CONTINUE);
    }

    /**
     * Test a valid jump to the up-left direction
     */
    @Test
    public void upLeft() {
        Piece p = new Piece(Color.WHITE);
        Piece j = new Piece(Color.RED);
        Piece j2 = new Piece(Color.RED);

        game.__test_set_white_turn();

        Move m = new Move(new Position(1, 1), new Position(3, 3));
        
        board.getSpace(1, 1).setPiece(p);
        board.getSpace(2, 2).setPiece(j);
        board.getSpace(3, 3).setPiece(null);
        board.getSpace(4, 2).setPiece(j2);

        assertEquals(mv.validateMove(m).getTurnResult(), TurnResult.CONTINUE);
    }

    /**
     * Test a valid jump to the down-right direction
     */
    @Test
    public void downRight() {
        Piece p = new Piece(Color.WHITE);
        p.king();
        Piece j = new Piece(Color.RED);
        Piece j2 = new Piece(Color.RED);

        game.__test_set_white_turn();

        Move m = new Move(new Position(4, 4), new Position(2, 2));
        
        board.getSpace(4, 4).setPiece(p);
        board.getSpace(3, 3).setPiece(j);
        board.getSpace(2, 2).setPiece(null);
        board.getSpace(1, 3).setPiece(j2);

        assertEquals(mv.validateMove(m).getTurnResult(), TurnResult.CONTINUE);
    }

    /**
     * Test a valid jump to the down-left direction
     */
    @Test
    public void downLeft() {
        Piece p = new Piece(Color.WHITE);
        p.king();
        Piece j = new Piece(Color.RED);
        Piece j2 = new Piece(Color.RED);

        game.__test_set_white_turn();

        Move m = new Move(new Position(4, 4), new Position(2, 2));
        
        board.getSpace(4, 4).setPiece(p);
        board.getSpace(3, 3).setPiece(j);
        board.getSpace(2, 2).setPiece(null);
        board.getSpace(1, 1).setPiece(j2);

        assertEquals(mv.validateMove(m).getTurnResult(), TurnResult.CONTINUE);
    }

    /**
     * Test kinging on a simple move
     */
    @Test
    public void king() {
        game.__test_set_white_turn();
        Piece p = new Piece(Color.WHITE);
        board.getSpace(6, 6).setPiece(p);
        Move m = new Move(new Position(6, 6), new Position(7, 7));
        assertEquals(mv.validateMove(m).getTurnResult(), TurnResult.KING);
    }

    /**
     * Test the kinging of a red piece
     */
    @Test
    public void kingRed() {
        Piece p = new Piece(Color.RED);
        board.getSpace(1, 1).setPiece(p);
        Move m = new Move(new Position(1, 1), new Position(0, 2));
        assertEquals(mv.validateMove(m).getTurnResult(), TurnResult.KING);
    }

    @Test
    public void kingKing() {
        Piece p = new Piece(Color.RED);
        p.king();
        board.getSpace(1, 1).setPiece(p);
        Move m = new Move(new Position(1, 1), new Position(0, 2));
        assertEquals(mv.validateMove(m).getTurnResult(), TurnResult.COMPLETE);
    }

    /**
     * Test kinging on a jump
     */
    @Test
    public void kingOnJump() {
        game.__test_set_white_turn();
        Piece p = new Piece(Color.WHITE);
        Piece j = new Piece(Color.RED);
        
        board.getSpace(5, 5).setPiece(p);
        board.getSpace(6, 6).setPiece(j);
        Move m = new Move(new Position(5, 5), new Position(7, 7));
        assertEquals(mv.validateMove(m).getTurnResult(), TurnResult.KING);
    }
    
    /**
     * Test the case where the user tries to make a simple move when a jump is possible
     */
    @Test
    public void invalidNoJumpWhenJumpPossible() {
        game.__test_set_white_turn();
        Piece p = new Piece(Color.WHITE);
        Piece p2 = new Piece(Color.WHITE);
        Piece j = new Piece(Color.RED);
        
        Move m = new Move(new Position(4, 4), new Position(5, 5));
        
        board.getSpace(4, 4).setPiece(p2);
        board.getSpace(5, 5).setPiece(null);

        board.getSpace(1, 1).setPiece(p);
        board.getSpace(2, 2).setPiece(j);
        board.getSpace(3, 3).setPiece(null);

        assertEquals(mv.validateMove(m).getTurnResult(), TurnResult.FAIL);
    }

    @Test
    public void jumpValidEdgeCases() {
        Piece p = new Piece(Color.RED);
        Piece j = new Piece(Color.WHITE);

        Move m = new Move(new Position(4, 4), new Position(1, 1));
        Move m2 = new Move(new Position(4, 4), new Position(4, 4));

        
        board.getSpace(4, 4).setPiece(p);
        board.getSpace(2, 2).setPiece(j);
        board.getSpace(1, 1).setPiece(null);

        assertEquals(mv.validateMove(m).getTurnResult(), TurnResult.FAIL);
        assertEquals(mv.validateMove(m2).getTurnResult(), TurnResult.FAIL);
    }

}