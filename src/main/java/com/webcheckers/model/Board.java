package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;

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
            board.add(new Row(i));
            for(int j = 0; j < BOARD_SIZE; j++) {
                board.get(i).add(new Space(i, j, (i%2!=0) == (j%2==0)));
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
                if(space.isSpaceValid()) {
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
        ArrayList<Row> flipped = new ArrayList<>();
        for (int i = 0; i < BOARD_SIZE; i++) {
            flipped.add(new Row(i));
            for(int j = BOARD_SIZE - 1; j >= 0; j--) {
                flipped.get(i).add(board.get(BOARD_SIZE - 1 - i).get(j));
            }
        }
        return new Board(flipped);
    }

    /**
     *
     * @return the board of spaces
     */
    public ArrayList<Row> getBoard() {
        return board;
    }

    /**
     * Get the Space at the position specified
     * @param p Position to get
     * @return The space
     */
    public Space getSpace(Position p) {
        try {
            return board.get(p.getRow()).get(p.getCell());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     *  Get the Space at the position specified
     * @param row spot in board array
     * @param col spot in row array
     * @return The space
     */
    public Space getSpace(int row, int col){
        try {
            return board.get(row).get(col);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean equals (Object o) {
        if (!(o instanceof Board)) return false;

        Board other = (Board)o;
        for (int i = 0; i < BOARD_SIZE - 1; i++)
            for (int j = 0; j < BOARD_SIZE - 1; j++)
                if (!getSpace(i ,j).equals(other.getSpace(i,j)))
                    return false;

        return true;
    }

    @Override
    public Iterator<Row> iterator() {
        return board.iterator();
    }

   /*public String toString() {
        String toReturn = "";
        for(int i = 0; i < BOARD_SIZE; i++) {
            for(int j = 0; j < BOARD_SIZE; j++) {
                Space sp = getSpace(i, j);
                if(sp.isSpaceValid()) {
                    if(sp.hasPiece()) {
                        if(sp.isPieceRed()) {
                            toReturn += "[R]";
                        }
                        else {
                            toReturn += "[W]";
                        }
                    }
                    else {
                        toReturn += "[X]";
                    }
                }
                else {
                    if(sp.hasPiece()) {
                        if(sp.isPieceRed()) {
                            toReturn += "!R!";
                        }
                        else {
                            toReturn += "!W!";
                        }
                    }
                    else {
                        toReturn += "{ }";
                    }
                }
            }
            toReturn += "\n";
        }
        return toReturn;
    }*/
}
