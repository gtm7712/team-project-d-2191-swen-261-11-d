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
        if(row.size()<8) {
            return row.add(space);
        }
        else{
            return false;
        }
    }

    /**
     *
     * @param spot x position in row
     * @param space the piece being put there
     * @return
     */
    public Space set(int spot, Space space){
        if(spot<8 && spot>0)
            return row.set(spot, space);
        else return null;
    }

    /**
     * @param index The index to get
     * @return The contents at the index
     */
    public Space get(int index) {
        if(index>=0 && index<8 && index<row.size())
            return row.get(index);
        else
            return null;
    }

    /**
     * 
     * @return The index of the row
     */
    public int getIndex() {
        return index;
    }
    @Override
    public boolean equals (Object o) {
        if (!(o instanceof Row)) return false;

        Row other = (Row)o;
        if(index!=other.getIndex())
            return false;
        for (int i = 0; i <  row.size(); i++)
                if (!row.get(i).equals(other.get(i)))
                    return false;
        return true;
    }
    @Override
    public Iterator<Space> iterator() {
        return row.iterator();
    }
}
