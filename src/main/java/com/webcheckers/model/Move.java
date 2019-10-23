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
     * 
     * @return  The start position
     */
    public Position getStart() {
        return start;
    }

    /**
     * 
     * @return  The end position
     */
    public Position getEnd() {
        return end;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Move)) return false;

        Move other = (Move)o;

        if (other.getStart() != getStart()) return false;
        if (other.getEnd() != getEnd()) return false;

        return true;
    }

}