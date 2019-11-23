package com.webcheckers.model;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The unit testing suite for {@link Row}
 * 
 */
public class RowTest {
    private Board board;
    private Row row1;
    private Row row2;

    /**
     * Creates a board and assigns row attributes
     */
    @BeforeEach
    public void setup(){
        board=new Board();
        row1=board.getBoard().get(0);
        row2=new Row(0);
    }

    /**
     * test the add function of row
     */
    @Test
    public void add(){
        assertTrue(row2.add(board.getSpace(0,0)));
        for(int i=1; i<Board.BOARD_SIZE;i++){
            row2.add(board.getSpace(0,i));
        }
        assertFalse(row2.add(board.getSpace(0,0)));
    }
    
    /**
     * Test setter for row
     */
    @Test
    public void set(){
        Space held=board.getSpace(0,2);
        assertTrue(row1.set(2, board.getSpace(4,4)).equals(held));
        assertTrue(row1.get(2).equals(board.getSpace(4,4)));
        assertNull(row1.set(-1,board.getSpace(4,4)));
        assertNull(row1.set(9,board.getSpace(4,4)));
    }

    /**
     * Test getter for row
     */
    @Test 
    public void get(){
        row2.add(new Space(0,0,true));
        assertTrue(row2.get(0).equals(new Space(0,0,true)));
        assertNull(row2.get(1));
        assertNull(row1.get(9));
        assertNull(row1.get(-1));
    }

    /**
     * Test the get index method on row
     */
    @Test
    public void getIndex(){
        assertTrue(board.getBoard().indexOf(row1)==row1.getIndex());
    }

    /**
     * Test for the row equals
     */
    @Test
    public void equals(){
        assertTrue(row1.equals(row1));
        assertFalse(row1.equals(row2));
        assertFalse(board.getBoard().get(2).equals(row1));
        assertFalse(row1.equals("hello"));
    }

    /**
     * Test for interator method on row
     */
    @Test
     public void iterable(){
        assertNotNull(row1.iterator());
    }

}
