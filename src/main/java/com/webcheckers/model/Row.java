package com.webcheckers.model;

/**
 * Represents a Row on the checkers board
 */
public class Row {

    private Space[] row;
    private int index;

    /**
     * Create a new Row
     * @param row Contents of the Row
     */
    public Row(Space[] row) {
        this.row = row;
    }

    /**
     * 
     * @return The contents of the Row
     */
    public Space[] getRow() {
        return row;
    }

    /**
     * @param index The index to get
     * @return The contents at the index
     */
    public Space get(int index) {
        return row[index];
    }

    /**
     * Set the index
     * @param index
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * 
     * @return The index
     */
    public int getIndex() {
        return index;
    }

}
