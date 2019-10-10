package com.webcheckers.model;

public class Row {

    private Space[] row;
    private int index;

    public Row(Space[] row) {
        this.row = row;
    }

    public Space[] getRow() {
        return row;
    }

    public Space get(int index) {
        return row[index];
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

}
