package com.webcheckers.model;

public class Space {

    private int row;
    private int col;
    private boolean isValid;

    public Space(int row, int col, boolean isValid) {
        this.row = row;
        this.col = col;
        this.isValid = isValid;
    }
}
