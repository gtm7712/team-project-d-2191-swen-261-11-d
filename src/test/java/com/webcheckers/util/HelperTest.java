package com.webcheckers.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import com.webcheckers.model.Board;
import com.webcheckers.model.Game;
import com.webcheckers.model.Move;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Player;
import com.webcheckers.model.Position;
import com.webcheckers.model.Space;
import com.webcheckers.model.Piece.Color;

import org.junit.jupiter.api.Test;

public class HelperTest {

    @Test
    public void redDnRight() {
        Board board = new Board();

        Player p1 = new Player("p1");
        Player p2 = new Player("p2");
        Game game = new Game();
        game.setRedPlayer(p1);
        game.setWhitePlayer(p2);
        game.setBoard(board);
        p1.setGame(game);
        p1.setBoard(game.getBoardRed());

        Move m = new Move(new Position(5,4), new Position(4,3));
        game.makeMove(m);
        game.endTurn();
        Move m2 = new Move(new Position(2,5), new Position(3,4));
        game.makeMove(m2);
        game.endTurn(); 
        
        Helper help = new Helper(p1);
        ArrayList<Space> ret = help.validPieces();
        assertTrue(ret.size() == 1);
    }

    @Test
    public void redDnLeft() {
        Board board = new Board();

        Player p1 = new Player("p1");
        Player p2 = new Player("p2");
        Game game = new Game();
        game.setRedPlayer(p1);
        game.setWhitePlayer(p2);
        game.setBoard(board);
        p1.setGame(game);
        p1.setBoard(game.getBoardRed());
        
        Helper help = new Helper(p1);
        ArrayList<Space> ret = help.validPieces();
        assertTrue(ret.size() == 4);
    }


    @Test
    public void whiteDnRight() {
        Board board = new Board();

        Player p1 = new Player("p1");
        Player p2 = new Player("p2");
        Game game = new Game();
        game.setRedPlayer(p1);
        game.setWhitePlayer(p2);
        game.setBoard(board);
        p2.setGame(game);
        p2.setBoard(game.getBoardWhite());

        Move m = new Move(new Position(5,6), new Position(4,7));
        game.makeMove(m);
        game.endTurn();
        
        Helper help = new Helper(p2);
        ArrayList<Space> ret = help.validPieces();
        assertTrue(ret.size() == 4);
    }

    @Test
    public void redKing() {
        Board board = new Board();

        Player p1 = new Player("p1");
        Player p2 = new Player("p2");
        Game game = new Game();
        game.setRedPlayer(p1);
        game.setWhitePlayer(p2);
        game.setBoard(board);
        p1.setGame(game);
        p1.setBoard(game.getBoardRed());

        game.getBoardRed().getSpace(1, 2).setPiece(null);
        Piece p = new Piece(Piece.Color.RED);
        p.king();
        game.getBoardRed().getSpace(1,2).setPiece(p);
               
        Helper help = new Helper(p1);
        ArrayList<Space> ret = help.validPieces();
        assertTrue(ret.size() == 1);
    }

    @Test
    public void whiteKing() {
        Board board = new Board();

        Player p1 = new Player("p1");
        Player p2 = new Player("p2");
        Game game = new Game();
        game.setRedPlayer(p1);
        game.setWhitePlayer(p2);
        game.setBoard(board);
        p2.setGame(game);
        p2.setBoard(game.getBoardWhite());

        game.getBoardRed().getSpace(6, 3).setPiece(null);
        Piece p = new Piece(Piece.Color.WHITE);
        p.king();
        game.getBoardRed().getSpace(6,3).setPiece(p);

               
        Helper help = new Helper(p2);
        ArrayList<Space> ret = help.validPieces();
        assertTrue(ret.size() == 1);
    }

    @Test
    public void redKingSimple() {
        Board board = new Board();

        Player p1 = new Player("p1");
        Player p2 = new Player("p2");
        Game game = new Game();
        game.setRedPlayer(p1);
        game.setWhitePlayer(p2);
        game.setBoard(board);
        p1.setGame(game);
        p1.setBoard(game.getBoardRed());

        game.getBoardRed().getSpace(2,5).setPiece(null);
        Piece p = new Piece(Piece.Color.RED);
        p.king();
        game.getBoardRed().getSpace(2,5).setPiece(p);

               
        Helper help = new Helper(p1);
        ArrayList<Space> ret = help.validPieces();
        assertTrue(ret.size() == 5);
    }
    
    @Test
    public void whiteKingSimple() {
        Board board = new Board();

        Player p1 = new Player("p1");
        Player p2 = new Player("p2");
        Game game = new Game();
        game.setRedPlayer(p1);
        game.setWhitePlayer(p2);
        game.setBoard(board);
        p2.setGame(game);
        p2.setBoard(game.getBoardWhite());

        game.getBoardRed().getSpace(5, 4).setPiece(null);
        Piece p = new Piece(Piece.Color.WHITE);
        p.king();
        game.getBoardRed().getSpace(5, 4).setPiece(p);
               
        Helper help = new Helper(p2);
        ArrayList<Space> ret = help.validPieces();
        assertTrue(ret.size() == 5);
    }
}