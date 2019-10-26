package com.webcheckers.model;

import java.util.ArrayList;

/**
 * The Game
 */
public class Game {
    private Board board;
    private Player redPlayer;
    private Player whitePlayer;
    private ArrayList<Move>turn= new ArrayList<>();
    /**
     * Create a new Game
     */
    public Game() {
        this.board = new Board();
    }

    /**
     * 
     * @return The white player
     */
    public Player getWhitePlayer(){
        return whitePlayer;
    }

    /**
     * 
     * @return The red player
     */
    public Player getRedPlayer(){
        return redPlayer;
    }

    /**
     * Set the red player
     * @param p New red player
     */
    public void setRedPlayer(Player p){
        this.redPlayer = p;
    }

    /**
     * Set the white player
     * @param p New white player
     */
    public void setWhitePlayer(Player p){
        this.whitePlayer = p;
    }

    /**
     * 
     * @return The board oriented for the Red player 
     */
    public Board getBoardRed() {
        return board;
    }

    /**
     * 
     * @return The board oriented for the White player
     */
    public Board getBoardWhite() {
        return board.flipped();
    }

    /**
     * @return The board represented in String format
     */
/*    public String toString() {
        String toReturn = "";
        //ArrayList<Row> boardArray = board.getBoard();
        for(int i = 0; i < Board.BOARD_SIZE; i++) {
            for(int j = 0; j < Board.BOARD_SIZE; j++) {
                if(board.getSpace(i, j).isValid()) {
                    if(board.getSpace(i,j).hasPiece()) {
                        if(board.getSpace(i,j).isPieceRed()) {
                            toReturn += "[R]";
                        }
                        else {
                            toReturn += "[W]";
                        }
                    }
                    else {
                        toReturn += "[ ]";
                    }
                }
                else {
                    toReturn += "{-}";
                }
            }
            toReturn += "\n";
        }
        return toReturn;
    }*/


    /**
     * Main entry point for the Game
     * @param args
     */
/*    public static void main(String[] args) {
        // Stuff for testing
        Game game = new Game();

        System.out.println(game);
    }*/
}
