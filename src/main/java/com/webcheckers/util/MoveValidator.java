package com.webcheckers.util;

import com.webcheckers.model.*;
import com.webcheckers.model.Piece.Color;
import com.webcheckers.util.ValidationResult.TurnResult;

/**
 * Utility class to validate @link{Move}s
 * 
 * @author Giovanni Melchionne
 */
public class MoveValidator {
    
    private Board board;
    private Game game;
    public String msg;
    public boolean didJump;

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
    public ValidationResult validateMove(Move move) {
        Piece pce = board.getSpace(move.getStart()).getPiece();
        didJump = false;

        if (simpleMove(move)) { // Simple move (no jump)
            if(shouldMakeJump(pce.getColor())){
                msg = "You must make the jump!";
                return new ValidationResult(TurnResult.FAIL, false);
            }
            else if (shouldKing(move)){
                return new ValidationResult(TurnResult.KING, false);
            }    
            else {
                return new ValidationResult(TurnResult.COMPLETE, false);
            }   

        } else if (madeJump(move)) {  // Jump made, is it valid?
            if (isJumpValid(move)) { didJump = true; }
            else {
                msg = "That is an illegal jump!";              
                return new ValidationResult(TurnResult.FAIL, false);
            }
        } else {  
            if (shouldMakeJump(pce.getColor())){    // Need to make a jump
                msg = "You must make the jump!";
                return new ValidationResult(TurnResult.FAIL, false);
            }
            else{
                msg = "Invalid move!";
                return new ValidationResult(TurnResult.FAIL, false);
            }
        }

        if(shouldKing(move)) return new ValidationResult(TurnResult.KING, didJump);
        
        // If no more pieces can be taken, the turn ends. Otherwise, the turn continues
        if (!isCapturePossible(move.getEnd(), pce)) {
            if   (shouldKing(move)) return new ValidationResult(TurnResult.KING,     didJump); 
            else                    return new ValidationResult(TurnResult.COMPLETE, didJump);

        } else {  // Turn continues
            return new ValidationResult(TurnResult.CONTINUE, didJump);
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
        System.out.println(jump.getRow() + " " + jump.getCell());
        System.out.println(move.getStart().getRow() + " " + move.getStart().getCell());
        System.out.println(move.getEnd().getRow() + " " + move.getEnd().getCell());
        if (move.getEnd().getCell() - move.getStart().getCell() != 2 && 
            move.getEnd().getCell() - move.getStart().getCell() != -2){
            return false;
        }
        if (jump.equals(move.getStart()) || jump.equals(move.getEnd())) return false; 
        else return true;
    }

    /**
     * Check if the jump made was valid
     * 
     * @param move Move to check
     * @return True if the jump was valid
     */
    private boolean isJumpValid(Move move) {        
        Position jump      = getMidpoint(move);
        Space    jumpSpace = board.getSpace(jump);
        
        Space space = board.getSpace(move.getStart());
        Space endSpace = board.getSpace(move.getEnd());

        int origRow = space.getRow();
        int origCol = space.getCellIdx();
        int endRow = endSpace.getRow();
        int endCol = endSpace.getCellIdx();

        if(space.getPiece().getColor() == Color.WHITE){
            if(space.getPiece().isKing()){
                if(endRow-origRow > 2 && endRow-origRow < -2){
                    return false;
                }
            }
            else{
                if(endRow-origRow > 2 || endRow-origRow < 0){
                    return false;
                }
            }
        }
        else{
            if(space.getPiece().isKing()){
                if(endRow-origRow > 2 && endRow-origRow < -2){
                    return false;
                }
            }
            else{
                if(endRow-origRow < -2 || endRow-origRow > 0){
                    return false;
                }
            }
        }

        if (jumpSpace.hasPiece()) { // Cannot jump own piece
            if (jumpSpace.getPiece().getColor() != space.getPiece().getColor()) {
                return true;
            } else {
                return false;
            }

        } else return false; // No piece to jump
    }

    /**
     * Get the midpoint of the move
     * 
     * @param move Move to check
     * @return The midpoint of the move
     */
    public Position getMidpoint(Move move) {
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
     * Check if the player should've made a jump, rather than a single move
     * 
     * @param color  Color of the piece
     * @return  True if there was a valid jump
     */
    public boolean shouldMakeJump(Color color) {
        for (int r = 0; r <= Board.BOARD_SIZE - 1; r++) {
            for (int c = 0; c <= Board.BOARD_SIZE - 1; c++) {
                Position pos = new Position(r, c);
                Piece pce = board.getSpace(pos).getPiece();

                if (pce != null && pce.getColor() == color) {
                    if (isCapturePossible(pos, pce)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Check if another capture is possible
     * 
     * @param pos Position to check from
     * @param piece Piece to check against
     * @return True if another capture is possible
     */
    private boolean isCapturePossible(Position pos, Piece piece) {
        Color   c    = piece.getColor();
        boolean king = piece.isKing();
        if(c == Color.WHITE) {
            Space upRight = board.getSpace(new Position(
            pos.getRow() + 1, pos.getCell() - 1));
            Space upRightRight = board.getSpace(new Position(
                pos.getRow() + 2, pos.getCell() - 2));
            Space upLeft = board.getSpace(new Position(
                pos.getRow() + 1, pos.getCell() + 1));
            Space upLeftLeft = board.getSpace(new Position(
                pos.getRow() + 2, pos.getCell() + 2));
            Space dnRight = board.getSpace(new Position(
                pos.getRow() - 1, pos.getCell() - 1));
            Space dnRightRight = board.getSpace(new Position(
                pos.getRow() - 2, pos.getCell() - 2));
            Space dnLeft = board.getSpace(new Position(
                pos.getRow() - 1, pos.getCell() + 1));
            Space dnLeftLeft = board.getSpace(new Position(
                pos.getRow() - 2, pos.getCell() + 2));

            if (upRight != null && upRightRight != null)
                if (upRight.hasPiece() && upRight.getPiece().getColor() != c && !upRightRight.hasPiece()){
                    return true;
                }
            if (upLeft != null && upLeftLeft != null)
                if (upLeft.hasPiece() && upLeft.getPiece().getColor() != c && !upLeftLeft.hasPiece()){
                    return true;
                }
            if (king) { // No need to check down if the piece is not a king
                if (dnRight != null && dnRightRight != null)
                    if (dnRight.hasPiece() && dnRight.getPiece().getColor() != c && !dnRightRight.hasPiece()) return true;
                if (dnLeft != null && dnLeftLeft != null)
                    if (dnLeft.hasPiece() && dnLeft.getPiece().getColor() != c && !dnLeftLeft.hasPiece()) return true;
            }
            return false;
        }
        Space upRight = board.getSpace(new Position(
            pos.getRow() - 1, pos.getCell() + 1));
        Space upRightRight = board.getSpace(new Position(
            pos.getRow() - 2, pos.getCell() + 2));
        Space upLeft = board.getSpace(new Position(
            pos.getRow() - 1, pos.getCell() - 1));
        Space upLeftLeft = board.getSpace(new Position(
            pos.getRow() - 2, pos.getCell() - 2));
        Space dnRight = board.getSpace(new Position(
            pos.getRow() + 1, pos.getCell() + 1));
        Space dnRightRight = board.getSpace(new Position(
            pos.getRow() + 2, pos.getCell() + 2));
        Space dnLeft = board.getSpace(new Position(
            pos.getRow() + 1, pos.getCell() - 1));
        Space dnLeftLeft = board.getSpace(new Position(
            pos.getRow() + 2, pos.getCell() - 2));

        if (upRight != null && upRightRight != null)
            if (upRight.hasPiece() && upRight.getPiece().getColor() != c && !upRightRight.hasPiece()){
                return true;
            }
        if (upLeft != null && upLeftLeft != null)
            if (upLeft.hasPiece() && upLeft.getPiece().getColor() != c && !upLeftLeft.hasPiece()){
                return true;
            }
        if (king) { // No need to check down if the piece is not a king
            if (dnRight != null && dnRightRight != null)
                if (dnRight.hasPiece() && dnRight.getPiece().getColor() != c && !dnRightRight.hasPiece()) return true;
            if (dnLeft != null && dnLeftLeft != null)
                if (dnLeft.hasPiece() && dnLeft.getPiece().getColor() != c && !dnLeftLeft.hasPiece()) return true;
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
        Piece    p      = board.getSpace(move.getStart()).getPiece();
        System.out.println(endPos.getRow());

        if(game.whoseTurn().equals(game.getRedPlayer())) {
            if (!p.isKing()) {
                if (endPos.getRow() == 0)
                    return true;
            }
        } else {//white player
            if(!p.isKing()){
                if(endPos.getRow()==Board.BOARD_SIZE - 1)
                    return true;
            }
        }
        return false;
    }

    /**
     * checks if a move is a simple 1 tile move
     * @param move
     * @return if it is or not
     */
    private boolean simpleMove(Move move){
        if(board.getSpace(move.getStart()).getPiece().isKing()) {
            return _simpleMove_king(move);
        } else {
            if(game.whoseTurn().equals(game.getRedPlayer())) {
                return _simpleMove_red(move);
            } else { //white player
                return _simpleMove_white(move);
            }
        }
    }

    /**
     * Helper function for SimpleMove
     * 
     * (Irregular formatting is for readibility purposes)
     */
    private boolean _simpleMove_king(Move move) {
        if(move.getEnd().getRow() == move.getStart().getRow() + 1 || 
        move.getEnd().getRow() == move.getStart().getRow() - 1) 
        {
            if(move.getEnd().getCell() == move.getStart().getCell() + 1 ||
            move.getEnd().getCell() == move.getStart().getCell() - 1) 
            {
                return true;
            } else 
            {
                return false;
            }
        } else 
        {
            return false;
        }
    }
    
    /**
     * Helper function for SimpleMove
     * 
     * (Irregular formatting is for readibility purposes)
     */
    private boolean _simpleMove_red(Move move) {
        if(move.getEnd().getRow() == move.getStart().getRow() - 1)
        {
            if(move.getEnd().getCell() == move.getStart().getCell() + 1 ||
            move.getEnd().getCell() == move.getStart().getCell() - 1)
            {
                return true;
            } else
            {
                return false;
            }
        } else
        {
            return false;
        }
    }

    /**
     * Helper function for SimpleMove
     * 
     * (Irregular formatting is for readibility purposes)
     */
    private boolean _simpleMove_white(Move move) {
        if(move.getEnd().getRow() == move.getStart().getRow() + 1)
        {
            if(move.getEnd().getCell() == move.getStart().getCell() + 1 ||
            move.getEnd().getCell() == move.getStart().getCell() - 1)
            {
                return true;
            } else
            {
                return false;
            }
        } else 
        {
            return false;
        }
    }

}