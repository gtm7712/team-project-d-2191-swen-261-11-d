package com.webcheckers.util;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webcheckers.model.Board;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Piece.Color;

import org.junit.jupiter.api.Test;

/**
 * Testing suite for the Replay Helper
 */
public class ReplayHelperTest {

    /**
     * Test the replay helper
     */
    @Test
    public void replayTest() {
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
        rpl2.next();
        
        Board move2 = rpl2.next();
        assertTrue(move2.getSpace(0, 3).equals(board.getSpace(0, 3)));
        
    }

}