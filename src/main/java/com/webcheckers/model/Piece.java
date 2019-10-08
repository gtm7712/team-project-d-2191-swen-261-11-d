package com.webcheckers.model;

public class Piece {

    public static enum Color {
        RED, BLACK
    }

    private Color color;
    private boolean isKing;

    public Piece(Color color) {
        this.color = color;
    }

    public boolean isKing() {
        return isKing;
    }

    public void setKing(boolean kingState) {
        this.isKing = kingState;
    }

    public Color getColor() {
        return this.color;
    }

}