package com.webcheckers.model;

/**
 * Represents a checkers piece
 * @author Giovanni Melchionne
 */
public class Piece {

    /**
     * Represents the color of a piece
     */
    public static enum Color {
        RED, BLACK
    }

    private Color color;
    private boolean isKing;

    /**
     * Create a new piece
     * @param color  Color of the piece
     */
    public Piece(Color color) {
        this.color = color;
    }

    /**
     * 
     * @return  true if the piece is a king
     */
    public boolean isKing() {
        return isKing;
    }

    /**
     * King the piece
     */
    public void king() {
        this.isKing = true;
    }

    /**
     * 
     * @return  the color of the piece
     */
    public Color getColor() {
        return this.color;
    }

}