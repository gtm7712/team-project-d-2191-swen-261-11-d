package com.webcheckers.util;

/**
 * Result from the @link{MoveValidator}
 */
public class ValidationResult {

    /**
     * Result of the move
     * COMPLETE:  The turn is over
     * CONTINUE:  The player must make another move
     * KING:      The piece has reached the end of the board
     *                (end of turn is implied)
     * FAIL:      The move was not valid
     */
    public enum TurnResult { COMPLETE, CONTINUE, KING, FAIL };

    private final TurnResult tr;
    private final boolean jump;

    /**
     * Create a ValidationResult
     * 
     * @param turnResult Result of the turn
     * @param jump Did a jump occur
     */
    public ValidationResult(TurnResult turnResult, boolean jump) {
        this.tr = turnResult;
        this.jump = jump;
    }

    /** Get the turn result @return The result of the turn */
    public TurnResult getTurnResult() { return tr; }
    /** Get if a jump occured @return True if a jump occured */
    public boolean wasJump() { return jump; }

}