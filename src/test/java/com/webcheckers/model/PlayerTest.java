package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


/**
 * The unit testing suite for {@link Player}
 * 
 * @author Kyle Collins
 */
@Tag ("Model-tier")
public class PlayerTest {


    private Player player1;
    private Player player2;
    private String playerName;
    private Game game;
    private Board board;
    private Board flipped;
    
    /**
     * Creates two players and a game
     */
    @BeforeEach
    public void setup(){

        this.player1 = new Player("Player_1");
        this.player2 = new Player("Player_2");

        this.playerName = "Player_1";

        this.game = new Game();

        player1.setGame(game);
        player2.setGame(game);
        game.setRedPlayer(player1);
        game.setWhitePlayer(player2);
        
        this.board = this.game.getBoardRed();
        this.flipped = this.game.getBoardWhite();
    }

    /**
     * Test getName 
     */
    @Test
    public void getNameTest() {
        assertTrue(player1.getName() == playerName);
        assertTrue(player1.toString() == playerName);
    }

    /**
     * Test the inGame and isInGame functions
     */
    @Test
    public void inGameTest() {

        this.player1.inGame(true);
        assertTrue(player1.isInGame()== true);
        assertTrue(player2.isInGame() == false);

    }

    /**
     * Test the getter and setter for opponent
     */
    @Test
    public void opponentTest() {

        this.player1.setOpponent(player2);
        assertTrue(player1.getOpponent() == player2);

        this.player2.setOpponent(player1);
        assertTrue(player2.getOpponent() == player1);

    }

    /**
     * Test the getBoard function
     */
    @Test
    public void getBoardTest() {

        assertTrue(player1.getBoard().equals(this.board));

    }

    /**
     * Test the getFlippedBoard
     */
    @Test
    public void getFlippedBoardTest() {
        assertTrue(player1.getFlippedBoard().equals(this.flipped), this.flipped.toString());

    }

    /**
     * Test getter and setter for Game
     */
    @Test
    public void gameTest() {
        
        this.player1.setGame(game);
        assertTrue(player1.getGame() == game);

    }

    /**
     * Test hash code
     */
    @Test
    public void hashCodeTest() {

        assertTrue(player1.hashCode() == "Player_1".hashCode());

    }

    /**
     * Test equals function
     */
    @Test
    public void equalsTest() {

        assertTrue(player1.equals(player1) == true);
        assertTrue(player1.equals(null) == false);

    }

    /**
     * Test to string
     */
    @Test
    public void toStringTest() {
        
        assertTrue(player1.toString() == "Player_1");

    }






    


    


}