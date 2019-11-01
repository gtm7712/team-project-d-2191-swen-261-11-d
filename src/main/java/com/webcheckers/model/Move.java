package com.webcheckers.model;

/**
 * Represents the Move of a piece from one position to another
 */
public class Move {

    private final Position start;
    private final Position end;

    /**
     * Create a new Move
     * 
     * @param start  Start position
     * @param end  End position
     */
    public Move(Position start, Position end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Get the start position
     * @return  The start position
     */
    public Position getStart() {
        return start;
    }

    /**
     * Get the end position
     * @return  The end position
     */
    public Position getEnd() {
        return end;
    }

    /**
     * Check for equality of two moves
     * @param o the move that is checked
     * @return true if equal, else false
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Move)) return false;

        Move other = (Move)o;

        if (other.getStart() != getStart()) return false;
        if (other.getEnd() != getEnd()) return false;

        return true;
    }

    /**
     * string method for a move
     * @return the string representation of a move
     */
    @Override
    public String toString(){
        return "Start : " + getStart() + " End : " + getEnd();
    }
}