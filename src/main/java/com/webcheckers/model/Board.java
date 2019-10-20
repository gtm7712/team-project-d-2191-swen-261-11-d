package com.webcheckers.model;

import java.util.ArrayList;
import java.util.List;

public class Board {
    public final static int BOARD_SIZE = 8;
    private Space[][] board = new Space[BOARD_SIZE][BOARD_SIZE];

    /**
     * initiates board
     */
    public Board() {
        for(int i = 0; i < BOARD_SIZE; i++) {
            for(int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = new Space(i, j, (i%2!=0) == (j%2==0));
            }
        }
        resetGameBoard();
    }

    /**
     * initiates the board with preset pieces
     * @param preset double array of spaces with pieces
     */
    public Board(Space[][] preset) {
        this.board = preset;
    }

    /**
     * @board is set to the  default boardstate
     */
    public void resetGameBoard() {
        for(int i = 0; i < BOARD_SIZE; i++) {
            for(int j = 0; j < BOARD_SIZE; j++) {
                Space space = board[i][j];
                if(space.isValid()) {
                    if(i < 3) {
                        space.setPiece(new Piece(Piece.Color.WHITE));
                    }
                    else if(i >= 5) {
                        space.setPiece(new Piece(Piece.Color.RED));
                    }
                }
            }
        }
    }

    /**
     * Flips the board so the other players pieces show at the bottom
     * @return flipped board
     */
    public Board flipped() {
        Space[][] flipped = new Space[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                flipped[BOARD_SIZE - 1 - i][BOARD_SIZE - 1 - j] = board[i][j];
            }
        }
        return new Board(flipped);
    }

    @Override
    public boolean equals (Object o) {
        if (!(o instanceof Board)) return false;

        Board other = (Board)o;
        for (int i = 0; i < BOARD_SIZE - 1; i++)
            for (int j = 0; j < BOARD_SIZE - 1; j++)
                if (!board[i][j].equals(other.getBoard()[i][j]))
                    return false;

        return true;
    }

    /**
     *
     * @return the board of spaces
     */
    public Space[][] getBoard() {
        return board;
    }


}
