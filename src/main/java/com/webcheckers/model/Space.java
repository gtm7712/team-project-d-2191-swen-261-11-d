package com.webcheckers.model;

/**
 * Represents a space on the game board
 */
public class Space {

    private int row;
    private int cellIdx;
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
        this.cellIdx = col;
        this.isValid = isValid;  //dark or white square
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
     * removes a piece from this space
     *
     */
    public void removePiece(){
        piece=null;
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
        return isValid && !hasPiece();
    }

    /**
     * Check if the space is a valid spot
     * @return true if a piece can be there, false otherwise
     */
    public boolean isSpaceValid() {
        return isValid;
    }

    /**
     * 
     * @return The row this space is in
     */
    public int getRow(){
        return row;
    }

    /**
     * Equals check without x,y check
     * @param o Object to compare to
     * @return
     */
    public boolean isCongruent(Object o) {
        if (!(o instanceof Space)) return false;

        Space other = (Space)o;
        if (other.hasPiece() != hasPiece()) {
            return false;
        }
        if (hasPiece()) {
            if (other.getPiece().getColor() != getPiece().getColor()) { return false; }
            if (other.getPiece().isKing() != getPiece().isKing()) { return false; }
        }
        if (other.isValid != isValid) return false;

        return true;
    }

    /**
     * checks if this space has a king
     * @return
     */
    public boolean hasKing(){
        if(hasPiece())
            return piece.isKing();
        return false;
    }
    /**
     * Kings the piece on this space
     */
    public void kingPiece(){
        if(hasPiece())
            piece.king();
    }
    /**
     *
     * @return The column the space is in
     */
    public int getCellIdx(){ return cellIdx;}
    @Override
    public boolean equals(Object o) {
        if (!isCongruent(o)) return false;

        Space other = (Space)o;
        if(getCellIdx()!=other.getCellIdx())
            return false;
        if(getRow()!=other.getRow())
            return false;
        return true;
    }

    /**
     * unking a piece
     */
    public void unKingPiece() {
        if(piece.isKing()){
            piece.unKing();
        }
    }

    /**
     * Get a copy of a space
     * @return
     *  the space copy
     */
    public Space getCopy() {
        Space copy = new Space(row, cellIdx, isValid);
        if(hasPiece()) {
            copy.setPiece(new Piece(getPiece().getColor()));
            if(hasKing()) {
                copy.kingPiece();
            }
        }
        return copy;
    }
}
