package com.webcheckers.model;

/**
 * Represents a space on the game board
 */
public class Space {

    private int row;
    private int col;
    private boolean isValid;

    private Piece piece = null;

    /**
     * Create a new Space
     * @param row The row this space is in
     * @param col The column this space is in
     * @param isValid Can a piece be placed on this Space?
     */
    public Space(int row, int col, boolean isValid) {
        this.row = row;
        this.col = col;
        this.isValid = isValid;
    }

    /**
     * Set the piece
     * @param piece The piece on this space
     */
    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    /**
     * 
     * @return The piece on this Space
     */
    public Piece getPiece() {
        return piece;
    }

    /**
     * 
     * @return True if the space has a piece
     */
    public boolean hasPiece() {
        return piece != null;
    }

    /**
     * @return True if there is a piece and it is red
     */
    public boolean isPieceRed() {
        return hasPiece() && piece.isRed();
    }

    /**
     * 
     * @return True if a piece can be placed on this Space
     */
    public boolean isValid() {
        return isValid;
    }

    /**
     * 
     * @return The row this space is in
     */
    public int getRow(){
        return row;
    }
}
