package com.webcheckers.model;

/**
 * Hold the Position of a piece
 */
public class Position {

    private final int row;
    private final int cell;

    /**
     * Create a new Position
     * 
     * @param row  The row the piece is in
     * @param cell  The cell of the row (column) the piece is in
     */
    public Position(int row, int cell) {
        this.row = row;
        this.cell = cell;
    }

    /**
     * 
     * @return  The row 
     */
    public int getRow() {
        return row;
    }

    /**
     * 
     * @return  The cell of the row (column)
     */
    public int getCell() {
        return cell;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Position)) return false;

        Position other = (Position)o;

        if (other.getRow() != getRow()) return false;
        if (other.getCell() != getCell()) return false;

        return true;
    }

}