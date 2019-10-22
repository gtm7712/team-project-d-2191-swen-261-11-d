package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Board implements Iterable<Row> {
    public final static int BOARD_SIZE = 8;
    //private Space[][] board = new Space[BOARD_SIZE][BOARD_SIZE];
    private ArrayList<Row>board;
    /**
     * initiates board
     */
    public Board() {
        board=new ArrayList<>(8);
        for(int i = 0; i < BOARD_SIZE; i++) {
            board.set(i, new Row(i));
            for(int j = 0; j < BOARD_SIZE; j++) {
                board.get(i).set(j, new Space(i, j, (i%2!=0) == (j%2==0)));
            }
        }
        resetGameBoard();
    }

    /**
     * initiates the board with preset pieces
     * @param preset double array of spaces with pieces
     */
    public Board(ArrayList<Row> preset) {
        this.board = preset;
    }

    /**
     * @board is set to the  default boardstate
     */
    public void resetGameBoard() {
        for(int i = 0; i < BOARD_SIZE; i++) {
            for(int j = 0; j < BOARD_SIZE; j++) {
                Space space = board.get(i).get(j);
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
        ArrayList<Row> flipped = new ArrayList<>(8);
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                flipped.set(BOARD_SIZE-1-i, board.get(i));
                flipped.get(BOARD_SIZE-1-i).set(BOARD_SIZE-1-j, board.get(i).get(j));
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
                if (!board.get(i).get(j).equals(other.getBoard().get(i).get(j)))
                    return false;

        return true;
    }

    /**
     *
     * @return the board of spaces
     */
    public ArrayList<Row> getBoard() {
        return board;
    }
    public Space getSpace(int row, int col){
        return board.get(row).get(col);
    }

    @Override
    public Iterator<Row> iterator() {
        return board.iterator();
    }
}
