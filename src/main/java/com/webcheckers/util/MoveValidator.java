package com.webcheckers.util;

import com.webcheckers.model.*;
import com.webcheckers.model.Piece.Color;

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
     * FAIL:      The move was not valid
     */
    public enum TurnResult { COMPLETE, CONTINUE, KING, FAIL, JUMP };

    //
    
    private Board board;
    private Game game;

    /**
     * Initialize the MoveValidator
     * 
     * @param game for checking moves and getting the board
     */
    public MoveValidator(Game game) {
        this.game=game;
        this.board = game.getClonedBoard();
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
        if (simpleMove(move)) {
            if(shouldKing(move)){

            }
            return TurnResult.COMPLETE;
        } else if( madeJump(move)) {
            if (isJumpValid(move)) {

                return TurnResult.JUMP;
                /* TODO: TAKE PIECE */ 
            } else {
                return TurnResult.FAIL; // Not a valid jump
            }
        }
        
        // If no more pieces can be taken, the turn ends. Otherwise, the turn continues
        if (!isCapturePossible(move.getEnd(), board.getSpace(move.getStart()).getPiece())) {
            if (shouldKing(move)) return TurnResult.KING; else return TurnResult.COMPLETE;
        } else {  // Turn continues
            return TurnResult.CONTINUE;
        }
    }
    
    /**
     * Check if a jump was made
     * 
     * @param move Move to check
     * @return True if a piece could be taken
     */
    private boolean madeJump(Move move) {
        Position jump = getMidpoint(move);

        if (jump.equals(move.getStart()) || jump.equals(move.getEnd())) return false; 
        else return true;
    }

    /**
     * Check if the jump made was valic
     * 
     * @param move Move to check
     * @return True if the jump was valid
     */
    private boolean isJumpValid(Move move) {        
        Position jump = getMidpoint(move);

        Space jumpSpace = board.getSpace(jump);

        if (jumpSpace.hasPiece()) { // Cannot jump own piece
            if (jumpSpace.getPiece().getColor() != board.getSpace(move.getStart()).getPiece().getColor()) {
                return true;
            } else {
                return false;
            }

        } else return false; // No piece to jump
    }
    public boolean simpleMove(Move move){
        if(board.getSpace(move.getStart()).getPiece().isKing()){
            if(move.getEnd().getRow()==move.getStart().getRow()+1 || move.getEnd().getRow()==move.getStart().getRow()-1){
                if(move.getEnd().getCell()==move.getStart().getCell()+1||move.getEnd().getCell()==move.getStart().getCell()-1)
                    return true;
                else
                    return false;
            }
            else
                return false;

        }
        else{
            if(game.whoseTurn().equals(game.getRedPlayer())){
                if(move.getEnd().getRow()==move.getStart().getRow()+1){
                    if(move.getEnd().getCell()==move.getStart().getCell()+1||move.getEnd().getCell()==move.getStart().getCell()-1)
                        return true;
                    else
                        return false;
                }
                else
                    return false;

            }
            else{  //white player

                if(move.getEnd().getRow()==move.getStart().getRow()-1){
                    if(move.getEnd().getCell()==move.getStart().getCell()+1||move.getEnd().getCell()==move.getStart().getCell()-1)
                        return true;
                    else
                        return false;
                }
                else
                    return false;
            }
        }
    }

    /**
     * Get the midpoint of the move
     * 
     * @param move Move to check
     * @return The midpoint of the move
     */
    private Position getMidpoint(Move move) {
        // Extract coordinates of the move
        int sr = move.getStart().getRow();
        int sc = move.getStart().getCell();
        int er = move.getEnd().getRow();
        int ec = move.getEnd().getCell();

        // Calculate the midpoint
        return new Position(
            (int) Math.floor((sr + er) / 2),
            (int) Math.floor((sc + ec) / 2));
    }

    /**
     * Check if another capture is possible
     * 
     * @param pos Position to check from
     * @param piece Piece to check against
     * @return True if another capture is possible
     */
    private boolean isCapturePossible(Position pos, Piece piece) {
        Color c = piece.getColor();
        boolean king = piece.isKing();

        Space upRight = board.getSpace(new Position(
            pos.getRow() + 1, pos.getCell() + 1));
        Space upLeft = board.getSpace(new Position(
            pos.getRow() + 1, pos.getCell() - 1));
        Space dnRight = board.getSpace(new Position(
            pos.getRow() - 1, pos.getCell() + 1));
        Space dnLeft = board.getSpace(new Position(
            pos.getRow() - 1, pos.getCell() + 1));

        if (upRight != null)
            if (upRight.hasPiece() && upRight.getPiece().getColor() != c) return true;
        if (upLeft != null)
            if (upLeft.hasPiece() && upLeft.getPiece().getColor() != c) return true;
        
        if (king) { // No need to check down if the piece is not a king
            if (dnRight != null)
                if (dnRight.hasPiece() && dnRight.getPiece().getColor() != c) return true;
            if (dnLeft != null)
                if (dnLeft.hasPiece() && dnLeft.getPiece().getColor() != c) return true;
        }
        return false;
    }

    /**
     * Check if the move should result in a kinged piece
     * 
     * @param move Move to check
     * @return True if the piece should be kinged
     */
    private boolean shouldKing(Move move) {
        Position endPos = move.getEnd();
        Piece p = board.getSpace(move.getStart()).getPiece();
        if(game.whoseTurn().equals(game.getRedPlayer())) {

            if (!p.isKing()) {
                if (endPos.getRow() == Board.BOARD_SIZE - 1)
                    return true;
            }
        }
        else{//white player
            if(!p.isKing()){
                if(endPos.getRow()==0)
                    return true;
            }
        }
        return false;
    }

}