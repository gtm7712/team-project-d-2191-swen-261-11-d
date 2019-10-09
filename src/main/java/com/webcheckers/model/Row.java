package com.webcheckers.model;

public class Row {

    private Space[] row;
    private int index;

    public Row(Space[] row, int index) {
        this.row = row;
    }

    public Space[] getRow() {
        return row;
    }

    public Space get(int index) {
        return row[index];
    }

    public int getIndex() {
        return index;
    }

}
