package com.webcheckers.model;


import java.util.ArrayList;
import java.util.InputMismatchException;

public class Replay {

    private static final String encoder = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_";

    private Board state;
    private ArrayList<ArrayList<String>> diffs = new ArrayList<ArrayList<String>>();
    private int pointer = 0;

    public Replay() {
        state = new Board();
    }

    public static String diffEncode(Space before, Space after) {
        if(before.getRow() != after.getRow() || before.getCellIdx() != after.getCellIdx()) { return "{invalid diff}"; }
        int pos_value = (before.getRow() * 8) + before.getCellIdx();
        int else_value = (!before.hasPiece() && after.hasPiece() ? 1 : 0) + (after.isPieceRed() ? 2 : 0) + (after.hasKing() ? 4 : 0);
        return "" + encoder.charAt(pos_value) + encoder.charAt(else_value);
    }

    private Space diffDecode(String diff) {
        char pos_value = diff.charAt(0);
        char else_value = diff.charAt(1);
        int row = (int)(encoder.indexOf(pos_value)/8);
        int col = (int)(encoder.indexOf(pos_value)%8);
        Space space = new Space(row, col, state.getSpace(row, col).isValid());
        return null;
    }

    public void updateReplay(Board update) {ArrayList<String> newDiffs = new ArrayList<String>();
        for(int i = 0; i < Board.BOARD_SIZE; i++) {
            for(int j = 0; j < Board.BOARD_SIZE; j++) {
                if(!state.getSpace(i, j).isCongruent(update.getSpace(i, j))) {

                    newDiffs.add(diffEncode(state.getSpace(i, j), update.getSpace(i, j)));
                }
            }
        }
        diffs.add(newDiffs);
        state = update.getCopy();
        pointer++;
    }

    public Board scrub(int amt) {
        int target = pointer;
        target += amt;
        if(target < 0) { target = 0; }
        if(target >= diffs.size()) { target = diffs.size() - 1; }
        int directionOffset = (target < pointer ? -1 : 1);
        while(target != pointer) {
            if(directionOffset < 0) {

            }
            else {

            }
            pointer += directionOffset;
        }

        return state;
    }

    public String getEncoding() {
        String toReturn = "";
        for(ArrayList<String> diff : diffs) {
            toReturn += "" + encoder.charAt(diff.size());
            for(String single : diff) {
                toReturn += single;
            }
        }
        return toReturn;
    }

    public static void main(String[] args) {
        Replay r = new Replay();
        Board b = new Board();

        System.out.println("E: " + r.getEncoding());
        System.out.println(b);

        b.makeMoveDebug(5,0,4,1);
        r.updateReplay(b);
        System.out.println("E: " + r.getEncoding());
        System.out.println(b);

        b.makeMoveDebug(4,1,3,0);
        r.updateReplay(b);

        System.out.println("E: " + r.getEncoding());
        System.out.println(b);
    }
}
