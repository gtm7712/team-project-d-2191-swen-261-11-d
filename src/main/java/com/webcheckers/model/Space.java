package com.webcheckers.model;

public class Space {

    private int row;
    private int col;
    private boolean isValid;

    private Piece piece = null;

    public Space(int row, int col, boolean isValid) {
        this.row = row;
        this.col = col;
        this.isValid = isValid;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Piece getPiece() {
        return piece;
    }

    public boolean hasPiece() {
        return piece != null;
    }

    public boolean isPieceRed() {
        return hasPiece() && piece.isRed();
    }

    public boolean isValid() {
        return isValid;
    }

    public int getRow(){
        return row;
    }
}
