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
    private Player theirTurn;     //says who's turn it is
    private Board clonedBoard;
    private boolean isComplete=false;
    private boolean wasKinged=false;
    /**
     * Create a new Game
     */
    public Game() {
        this.board = new Board();
        this.clonedBoard=new Board(board.getBoard());
    }

    /**
     *
     * @return if movevalidator has returned complete or not
     */
    public boolean isComplete(){
        return isComplete;
    }

    /**
     * called when move validator returns a complete , sets complete to true
     */
    public void setComplete(){
        isComplete=true;
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
        this.redPlayer.setBoard(getBoardRed());
        theirTurn=p;
    }

    /**
     * Set the white player
     * @param p New white player
     */
    public void setWhitePlayer(Player p){
        this.whitePlayer = p;
        this.whitePlayer.setBoard(getBoardWhite());
    }

    /**
     * 
     * @return The board oriented for the Red player 
     */
    public Board getBoardRed() {
        return board;
    }

    /**
     * get clone board that updates with validated moves
     * @return the cloned board
     */
    public Board getClonedBoard(){
        return clonedBoard;
    }

    /**
     * 
     * @return The board oriented for the White player
     */
    public Board getBoardWhite() {
        return board.flipped();
    }

    /**
     * Adds a move to your turn, to be ready to be submitted when your turn is swapped
     * @param madeMove move that went through validation
     */
    public void makeMove(Move madeMove){
        turn.add(madeMove);
        clonedBoard.makeMove(madeMove);
    }

    /**
     * handles if revert turn button is clicked.
     * 
     * @return Error message, if applicaple, or Null if successful 
     */

    public String revertTurn(){
        isComplete=false;
//        this.clonedBoard=new Board(board.getBoard());
        for(int i=0; i<turn.size(); i++){
            Move move=turn.get(i);
            if(wasKinged) {
                if (move.getEnd().getRow() == board.BOARD_SIZE - 1 && theirTurn.equals(whitePlayer)) {
                    board.getSpace(move.getEnd()).unKingPiece();
                } else if (move.getEnd().getRow() == 0 && theirTurn.equals(redPlayer)) {
                    board.getSpace(move.getEnd()).unKingPiece();
                }
            }
            clonedBoard.makeMove(new Move(move.getEnd(), move.getStart()));
        }
        wasKinged=false;
        try {
            turn=new ArrayList<>();
        } catch (Exception e) {
            return e.getMessage();
        }
        return null;

    }

    /**
     * handles when End Turn button is clicked.
     */
    public void endTurn(){
        board=clonedBoard;
        turn=new ArrayList<>();
        isComplete=false;
        wasKinged=false;
        if(theirTurn.equals(redPlayer))
            theirTurn=whitePlayer;
        else
            theirTurn=redPlayer;
    }

    public Player whoseTurn(){
        return theirTurn;
    }

    /**
     * @return The board represented in String format
     */
    public String toString() {
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
    }


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
