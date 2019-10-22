package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Represents a Row on the checkers board
 */
public class Row implements Iterable<Space>{

    private ArrayList<Space> row;
    private int index;     //position in board

    /**
     * Create a new Row
     */
    public Row(int index) {
        this.index=index;
        row = new ArrayList<>(8);
    }

    /**
     * adds a space to the row
     * @param space space being added
     * @return
     */
    public boolean add(Space space){
        return row.add(space);
    }

    /**
     *
     * @param spot x position in row
     * @param space the piece being put there
     * @return
     */
    public Space set(int spot, Space space){
        return row.set(spot, space);
    }

    /**
     * @param index The index to get
     * @return The contents at the index
     */
    public Space get(int index) {
        return row.get(index);
    }

    /**
     * 
     * @return The index of the row
     */
    public int getIndex() {
        return index;
    }

    @Override
    public Iterator<Space> iterator() {
        return row.iterator();
    }
}
