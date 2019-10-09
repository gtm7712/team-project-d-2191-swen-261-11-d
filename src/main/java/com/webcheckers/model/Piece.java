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
        RED, WHITE
    }
    public enum Type {
        KING, SINGLE
    }

    public Color color;
    public Type type;

    /**
     * Create a new piece
     * @param color  Color of the piece
     */
    public Piece(Color color) {
        this.color = color;
        this.type=Type.SINGLE;
    }

    /**
     * 
     * @return  true if the piece is a king
     */
    public boolean isKing() {
        return type==Type.KING;
    }

    /**
     * King the piece
     */
    public void king() {
        type = Type.KING;
    }

    /**
     * 
     * @return  the color of the piece
     */
    public Color getColor() {
        return this.color;
    }

    /**
     *
     * @return  if the color of the piece is red
     */
    public boolean isRed() { return this.color == Color.RED; }

    /**
     *
     * @return the pieces type
     */
    public Type getType() {
        return type;
    }
}