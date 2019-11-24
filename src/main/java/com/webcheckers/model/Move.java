package com.webcheckers.model;

/**
 * Represents the Move of a piece from one position to another
 */
public class Move {

    /**
     * the different types of moves
     */
    enum MoveType {
        NORMAL,
        RESIGN_RED,
        RESIGN_WHITE
    }

    private final MoveType type;
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
        this.type = MoveType.NORMAL;

    }

    /**
     * Create a new special Move
     *  Used by replay to designate things like resignation
     *
     * @param type  type of special move
     */
    public Move(MoveType type) {
        this.type = type;
        this.start = null;
        this.end = null;
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
     *  Gets move type
     * @return  type of move
     */
    public MoveType getType() { return type; }

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
}