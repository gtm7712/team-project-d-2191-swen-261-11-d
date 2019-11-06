package com.webcheckers.model;

import java.util.ArrayList;

public class Replay {
    private ArrayList<Move> moves;

    public Replay() {
        moves = new ArrayList<Move>();
    }

    /**
     * Adds all the Move objects in newMoves to moves
     *
     * @param newMoves : full turn of moves to add
     */
    public void addTurn(ArrayList<Move> newMoves) {
        moves.addAll(newMoves);
    }

    /**
     * Adds the Move object move to moves
     *
     * @param move : move to add
     */
    public void addMove(Move move) {
        moves.add(move);
    }

    /**
     * Returns the string representation of the replay to be stored
     *  Can be used to reconstruct Replay object using Replay.create()
     *
     * Warning: Will cause errors with a board size of 11 or greater
     *
     * @return String representation of replay
     */
    public String toString() {
        String toReturn = "";
        for(Move move : moves) {
            switch (move.getType()) {
                case NORMAL:
                    Position start = move.getStart();
                    toReturn += start.getRow() + "" + start.getCell();
                    Position end = move.getEnd();
                    toReturn += end.getRow() + "" + end.getCell();
                    break;
                case RESIGN_RED:
                    toReturn += "*R--";
                    break;
                case RESIGN_WHITE:
                    toReturn += "*W--";
            }
        }
        return toReturn;
    }

    public static Replay create(String representation) {
        Replay toReturn = new Replay();
        for(int i = 0; i < representation.length(); i += 4) {
            try {
                if(representation.charAt(i) == '*') {
                    switch(representation.charAt(i+1)) {
                        case 'R':
                            toReturn.addMove(new Move(Move.MoveType.RESIGN_RED));
                            break;
                        case 'W':
                            toReturn.addMove(new Move(Move.MoveType.RESIGN_WHITE));
                            break;
                    }
                }
                else {
                    // Turns the next 4 characters of the string representation into the next move
                    // Ex. 0213 is a move of the piece at (0,2) to (1,3)
                    toReturn.addMove(new Move(new Position(Integer.parseInt(""+representation.charAt(i)), Integer.parseInt(""+representation.charAt(i+1))),
                            new Position(Integer.parseInt(""+representation.charAt(i+2)), Integer.parseInt(""+representation.charAt(i+3)))));
                }
            }
            catch(Exception e) {
                System.out.println("There was an error reading that replay string");
                return new Replay();
            }
        }
        return toReturn;
    }
}
