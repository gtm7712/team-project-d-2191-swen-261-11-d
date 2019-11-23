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
        int row;

        /// Iterate through all spaces on the board
        for (int i = 0; i < BOARD_SIZE; i++) {
            for(int j = BOARD_SIZE - 1; j >= 0; j--) {

                space = board.getSpace(i, j);
                piece = space.getPiece();
                row = space.getRow();
                cell = space.getCellIdx();

                if(piece != null && piece.getColor() == playerColor) {
                        if(checkPositions(row, cell)){
                            spaces.add(space);
                        }
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
        Space space = board.getSpace(start);

        MoveValidator mv = new MoveValidator(this.game);
        if(mv.shouldMakeJump(playerColor)){
            return mv.isCapturePossible(start, space.getPiece());
        }
        return mv.canMove(start);

    }
}

