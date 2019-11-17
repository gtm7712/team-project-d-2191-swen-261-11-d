package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;

import com.webcheckers.util.MoveValidator;

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
        board = new ArrayList<>(8);
        for(int i = 0; i < BOARD_SIZE; i++) {
            board.add(new Row(i));
            for(int j = 0; j < BOARD_SIZE; j++) {
                board.get(i).add(preset.get(i).get(j).getCopy());
            }
        }
    }

    /**
     * initiates the board from a string representation
     * @param repr String representation of board
     */
    public Board(String repr) {
        board=new ArrayList<>(8);
        for(int i = 0; i < BOARD_SIZE; i++) {
            board.add(new Row(i));
            for(int j = 0; j < BOARD_SIZE; j++) {
                board.get(i).add(new Space(i, j, (i%2!=0) == (j%2==0)));
            }
        }
        int row = 0;
        int col = 0;
        for(char ch : repr.toCharArray()) {
            switch(ch) {
                case '|':
                    row++;
                    col = 0;
                    break;
                case 'r':
                    board.get(row).get(col).setPiece(new Piece(Piece.Color.RED));
                    col++;
                    break;
                case 'R':
                    getSpace(row, col).setPiece(new Piece(Piece.Color.RED));
                    getSpace(row, col).kingPiece();
                    col++;
                    break;
                case 'w':
                    board.get(row).get(col).setPiece(new Piece(Piece.Color.WHITE));
                    col++;
                    break;
                case 'W':
                    getSpace(row, col).setPiece(new Piece(Piece.Color.WHITE));
                    getSpace(row, col).kingPiece();
                    col++;
                    break;
                case '-':
                    col++;
                    break;
            }
        }
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
     * Returns a new Board object that is a copy of this
     * @return Copy of this
     */
    public Board getCopy() {
        return new Board(board);
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

    /**
     * updates the board with the move that was made
     * @param move validated move
     */
    public void makeMove(Move move){
        if(move.getEnd().getCell()==-1 && move.getEnd().getRow()==-1){
            getSpace(move.getStart()).removePiece();
        }
        else {
            getSpace(move.getEnd()).setPiece(getSpace(move.getStart()).getPiece());
            getSpace(move.getStart()).removePiece();
        }
    }

    public void makeMoveDebug(int row1, int col1, int row2, int col2) {
        makeMove(new Move(new Position(row1, col1), new Position(row2, col2)));
    }

    /**
     * Equals method for Boards to check if they are the same.
     * @param o The board that is hecked
     */
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

    /**
     * Overide the iterator function
     * @param void
     */
    @Override
    public Iterator<Row> iterator() {
        return board.iterator();
    }

    /**
     *
     * @return Simple string encoding of board
     */
    public String replayEncode() {
        String toReturn = "";
        for(int i = 0; i < BOARD_SIZE; i++) {
            for(int j = 0; j < BOARD_SIZE; j++) {
                Space sp = getSpace(i, j);
                if(sp.hasPiece()) {
                    if(sp.hasKing()) {
                        if(sp.isPieceRed()) {
                            toReturn += "R";
                        }
                        else {
                            toReturn += "W";
                        }
                    }
                    else {
                        if(sp.isPieceRed()) {
                            toReturn += "r";
                        }
                        else {
                            toReturn += "w";
                        }
                    }
                }
                else {
                    toReturn += "-";
                }
            }
            toReturn += "|";
        }
        return toReturn;
    }

   public String toString() {
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
    }

    /**
     * @return The list of rows
     */
    public ArrayList<Row> getPreset() {
        return this.board;
    }
}
