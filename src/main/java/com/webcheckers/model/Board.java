package com.webcheckers.model;

import java.util.ArrayList;
import java.util.List;

public class Board {
    public final static int BOARD_SIZE = 8;
    private Space[][] board = new Space[BOARD_SIZE][BOARD_SIZE];

    public Board() {
        for(int i = 0; i < BOARD_SIZE; i++) {
            for(int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = new Space(i, j, (i%2!=0) == (j%2==0));
            }
        }
        resetGameBoard();
    }

    public Board(Space[][] preset) {
        this.board = preset;
    }

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

    public Board flipped() {
        Space[][] flipped = new Space[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                flipped[BOARD_SIZE - 1 - i][BOARD_SIZE - 1 - j] = board[i][j];
            }
        }
        return new Board(flipped);
    }

    public Space[][] getBoard() {
        return board;
    }

    public List<Space> iterator() {
        List<Space> iter = new ArrayList<Space>();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                iter.add(board[i][j]);
            }
        }
        return iter;
    }
}
