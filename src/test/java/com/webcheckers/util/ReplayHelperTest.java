package com.webcheckers.util;

import com.webcheckers.model.Board;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Piece.Color;

import org.junit.jupiter.api.Test;

public class ReplayHelperTest {

    @Test
    public void the_test() {
        Board board = new Board();
        board.getSpace(1, 0).setPiece(new Piece(Color.RED));
        board.getSpace(0, 1).setPiece(null);
        board.getSpace(0, 3).setPiece(null);
        ReplayHelper rpl = new ReplayHelper("John", "Mary", board);

        // Make a move
        board.getSpace(1, 0).setPiece(null);
        board.getSpace(0, 1).setPiece(new Piece(Color.WHITE));
        rpl.record(board);

        // Something else
        board.getSpace(0, 1).setPiece(null);
        Piece wk = new Piece(Color.RED);
        wk.king();
        board.getSpace(0, 3).setPiece(wk);
        rpl.record(board);

        // Get the replay
        String replay = rpl.getReplay();
        System.out.println(replay);

        // Load the replay
        ReplayHelper rpl2 = new ReplayHelper();
        rpl2.loadReplay(replay);

        // scrub to the 2nd move
        Board boardBegin = rpl2.next();
        
        Board move1 = rpl2.next();
        
        Board move2 = rpl2.next();
        
        System.out.println(move2.getSpace(0, 3).isCongruent(board.getSpace(0, 3)));

        // System.out.println("Start: \n" + boardBegin.toString());
        // System.out.println();
        // System.out.println("1st move: \n" + move1.toString());
        // System.out.println();
        // System.out.println();
        // System.out.println();

        // System.out.println("Previous: \n" + rpl2.previous().toString());
        // System.out.println("2nd move: \n" + move2.toString());
        
    }

}