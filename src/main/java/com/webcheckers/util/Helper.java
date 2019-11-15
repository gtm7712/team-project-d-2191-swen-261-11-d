package com.webcheckers.util;

import java.util.ArrayList;
import com.webcheckers.model.*;
import com.webcheckers.model.Piece.Color;

/**
 * Utility class for the helper enhancement
 * 
 * @author Kyle Collins
 */
public class Helper {

    public final static int BOARD_SIZE = 8;
    
    private Player player;
    private Board board;
    private Game game;
    private Color playerColor;


    /**
     * Create a new helper
     */
    public Helper (Player player) {

        this.player = player;
        this.board = player.getBoard();

        this.game = player.getGame();

        if(game.getRedPlayer() == player) {
            this.playerColor = Color.RED;
        }
        else {
            this.playerColor = Color.WHITE;
        }
    }

    /**
     * Find Pieces that can make a valid move
     * @return ArrayList<Piece> pieces
     */
    public ArrayList<Space> validPieces(){

        ArrayList<Space> spaces = new ArrayList<>();
        Space space;
        Piece piece;
        int cell;

        /// Iterate through all spaces on the board
        for (int i = 0; i < BOARD_SIZE; i++) {
            for(int j = BOARD_SIZE - 1; j >= 0; j--) {

                space = board.getSpace(i, j);
                piece = space.getPiece();
                cell = space.getCellIdx();

                if(piece != null && piece.getColor() == playerColor) {
                        checkPositions(i, cell);
                        spaces.add(space);
                }
            }
        }

        return spaces;
    }

    /**
     * Check to see if a valid move exist in the spaces that surrounding 
     * the position of a given piece
     * @param row
     * @param cell
     * @return boolean
     */
    private boolean checkPositions(int row, int cell ) {

        // Corresponding position
        Position start = new Position(row, cell);

        MoveValidator mv = new MoveValidator(this.game);

        return mv.canMove(start, this.board);

    }
<<<<<<< HEAD
=======

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
                if(endRow-origRow > 2 || endRow-origRow < -2){
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

>>>>>>> 7a5e8daee3952edb1f81868032dd5d2adea5de72
}

