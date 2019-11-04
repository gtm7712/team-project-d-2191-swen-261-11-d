package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit testing suite for {@link Game}
 * 
 * @author Giovanni Melchionne
 * @author Kyle Collins
 */
@Tag ("Model-tier")
public class GameTest {

    private Game game;
    private Player pRed;
    private Player pWhite;
    
    /**
     * Set up a generic Game for each test
     */
    @BeforeEach
    public void setup() {
        game = new Game();
        pRed= new Player("John");
        pWhite = new Player("Mary");
        game.setRedPlayer(pRed);
        game.setWhitePlayer(pWhite);
    }

    /**
     * Test that the board is not null
     */
    @Test
    public void board_not_null_r() {
        assertNotNull(game.getBoardRed(),
            "Board shouldn't be null");
    }

    /**
     * Test that the flipped board is not null
     */
    @Test
    public void board_not_null_w() {
        assertNotNull(game.getBoardWhite(),
            "Board shouldn't be null");
    }

    /**
     * Test that the red and white boards aren't equal
     */
    @Test
    public void boards_not_equal() {
        assertFalse(game.getBoardRed().equals(game.getBoardWhite()),
            "Red board & White board shouldn't be equal");
    }

    /**
     * Null testing on the Player objects
     */
    @Test
    public void players_null() {
        assertNotNull(game.getRedPlayer(),
            "Red player should not be null");
        assertNotNull(game.getWhitePlayer(),
            "White player should not be null");

        Game g = new Game();
        assertNull(g.getRedPlayer(),
            "Red player should be null");
        assertNull(g.getWhitePlayer(),
            "White player should be null");
    }

    /**
     * Test isComplete 
     */
    @Test
    public void isCompleteTest() {
        assertTrue(game.isComplete() == false);
    }

    /**
     * Test setComplete 
     */
    @Test
    public void setCompleteTest() {
        game.setComplete();
        assertTrue(game.isComplete() == true);
    }

    /**
     * Check simplemove failed
     */
    @Test
    public void checkSimpleMoves(){
        Board board = game.getBoardRed();
        Move m = new Move(new Position(5,4), new Position(4,5));
        game.makeMove(m);
        game.endTurn();
        Move m2 = new Move(new Position(2,5), new Position(3,4));
        game.makeMove(m2);
        game.endTurn(); 
        assertFalse(game.checkAllSimpleMoves(new Position(4,5), board.getSpace(4,5).getPiece()));
   
    }
  
    /**
     * Check simplemove failed
     */
    @Test
    public void KingChecks(){
        Board board = game.getBoardRed();
        Piece piece = board.getSpace(5,4).getPiece();
        piece.king();
        assertFalse(game.checkAllSimpleMoves(new Position(5,4), piece));
   
    }  

    /**
     * Check simplemove failed
     */
    @Test
    public void getLoserRed(){
        pRed.setGame(game);
        pWhite.setGame(game);
        game.setWinner(pWhite);

        pRed.resign();
        assertEquals(game.getLoser(), pRed);
    }

    /**
     * Check simplemove failed
     */
    @Test
    public void getLoserWhite(){
        pRed.setGame(game);
        pWhite.setGame(game);
        game.setWinner(pRed);

        pWhite.resign();
        assertEquals(game.getLoser(), pWhite);
    }

    /**
     * Check simplemove failed
     */
    @Test
    public void kingRevert(){
        pRed.setGame(game);
        pWhite.setGame(game);
        
        Move m = new Move(new Position(5,4), new Position(4,5));
        game.makeMove(m);
        game.endTurn();
        Move m2 = new Move(new Position(2,5), new Position(3,4));
        game.makeMove(m2);
        game.endTurn(); 
        Move m3 = new Move(new Position(5,0), new Position(4,1));
        game.makeMove(m3);
        game.endTurn();
        Move m4 = new Move(new Position(1,4), new Position(2,5));
        game.makeMove(m4);
        game.endTurn(); 
        Move m5 = new Move(new Position(6,1), new Position(5,0));
        game.makeMove(m5);
        game.endTurn();
        Move m6 = new Move(new Position(0,5), new Position(1,4));
        game.makeMove(m6);
        game.endTurn(); 
        Move m7 = new Move(new Position(7,2), new Position(6,1));
        game.makeMove(m7);
        game.endTurn();
        Move m8 = new Move(new Position(2,7), new Position(3,6));
        game.makeMove(m8);
        game.endTurn(); 
        Move m9 = new Move(new Position(4,5), new Position(2,7));
        game.makeMove(m9);
        Move m10 = new Move(new Position(2,7), new Position(0,5));
        game.makeMove(m10);
        game.kingPiece(m10.getEnd());
        String move = game.revertTurn();
        assertEquals(null, move);
    }

    
    
}