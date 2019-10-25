package com.webcheckers.util;

import com.webcheckers.model.Board;
import com.webcheckers.model.Move;
import com.webcheckers.model.Position;
import com.webcheckers.model.Space;

/**
 * Utility class to validate @link{Move}s
 * 
 * @author Giovanni Melchionne
 */
public class MoveValidator {

    /**
     * Result of the move
     * COMPLETE:  The turn is over
     * CONTINUE:  The player must make another move
     * KING:      The piece has reached the end of the board
     *                (end of turn is implied)
     */
    public enum TurnResult { COMPLETE, CONTINUE, KING };

    //
    
    private Board board;

    /**
     * Initialize the MoveValidator
     * 
     * @param board Game board for checking moves
     */
    public MoveValidator(Board board) {
        this.board = board;
    }

    /**
     * Validate a move
     * 
     * No piece taken    ->  KING or COMPLETE
     * Can capture piece ->  KING or CONTINUE
     * Otherwise         ->  CONTINUE
     *  
     * @param move Move to validate
     * @return @link {MoveValidator.TurnResult} representing the state of the turn
     */
    public TurnResult validateMove(Move move) {
        // If the move takes no piece, the turn is over. Otherwise, the piece is taken
        if (!canTakePiece(move)) {
            if (shouldKing(move)) return TurnResult.KING; else return TurnResult.COMPLETE; 
        } else {
            /* TODO: TAKE PIECE */ 
        }
        
        // If no more pieces can be taken, the turn ends. Otherwise, the turn continues
        if (!isCapturePossible(move)) {
            if (shouldKing(move)) return TurnResult.KING; else return TurnResult.COMPLETE;
        } else {  // Turn continues
            return TurnResult.CONTINUE;
        }
    }
    
    /**
     * Check if a piece could be taken through the move
     * 
     * @param move Move to check
     * @return True if a piece could be taken
     */
    private boolean canTakePiece(Move move) {
        // Extract coordinates of the move
        int sr = move.getStart().getRow();
        int sc = move.getStart().getCell();
        int er = move.getEnd().getRow();
        int ec = move.getEnd().getCell();

        // Calculate the midpoint
        Position jump = new Position(
            (int) Math.floor((sr + er) / 2),
            (int) Math.floor((sc + ec) / 2));

        // No jump was made
        if (jump.equals(move.getStart()) || jump.equals(move.getEnd())) return false;

        Space jumpSpace = board.getSpace(jump);

        // If there is a piece in the space jumped it should be taken
        if (jumpSpace.hasPiece()) return true;
        else return false;
    }

    /**
     * Check if another capture is possible
     * 
     * @param move Move to check
     * @return True if another capture is possible
     */
    private boolean isCapturePossible(Move move) {
        Position endPosition = move.getEnd();

        return false; // TODO
    }

    /**
     * Check if the move should result in a kinged piece
     * 
     * @param move Move to check
     * @return True if the piece should be kinged
     */
    private boolean shouldKing(Move move) {
        Position endPosition = move.getEnd();

        return false; // TODO
    }

}