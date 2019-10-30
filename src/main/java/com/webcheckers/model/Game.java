package com.webcheckers.model;

import com.webcheckers.ui.PostValidateMoveRoute;
import com.webcheckers.util.MoveValidator;
import javafx.geometry.Pos;

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
    private Player winner;
    private boolean gameOver;

    private boolean isComplete=false;
    private boolean wasKinged=false;
    private ArrayList<Piece>graveyard=new ArrayList<>();  //pieces removed this turn
    /**
     * Create a new Game
     */
    public Game() {
        this.board = new Board();
        this.clonedBoard=board;
        this.gameOver = false;
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

        if(madeMove.getEnd().getRow()==-1 && madeMove.getEnd().getCell()==-1) {
            System.out.println("Grave Yard");
            graveyard.add(board.getSpace(madeMove.getStart()).getPiece());
            turn.add(0, madeMove);
            board.getSpace(madeMove.getStart()).removePiece();
        }else {
            turn.add(madeMove);
            clonedBoard.makeMove(madeMove);
        }
    }

    /**
     * handles if revert turn button is clicked.
     * 
     * @return Error message, if applicaple, or Null if successful 
     */

    public String revertTurn(){
        isComplete=false;
//        this.clonedBoard=new Board(board.getBoard());
        int i=turn.size()-1;
        System.out.println(turn.size());
        Move move=turn.get(i);
        if(wasKinged) {
            if (move.getEnd().getRow() == board.BOARD_SIZE - 1 && theirTurn.equals(whitePlayer)) {
                clonedBoard.getSpace(move.getEnd()).unKingPiece();
                wasKinged=false;
            } else if (move.getEnd().getRow() == 0 && theirTurn.equals(redPlayer)) {
                clonedBoard.getSpace(move.getEnd()).unKingPiece();
                wasKinged=false;
            }
        }
        clonedBoard.makeMove(new Move(move.getEnd(), move.getStart()));
            if(turn.get(0).getEnd().getCell()==-1 && turn.get(0).getEnd().getRow()==-1){
                clonedBoard.getSpace(turn.get(0).getStart()).setPiece(graveyard.get(graveyard.size()-1));
                graveyard.remove(graveyard.size()-1);
                turn.remove(0);
                        i--;
            }


        turn.remove(i);
        System.out.println(turn);
        try {
        } catch (Exception e) {
            return e.getMessage();
        }
        return null;

    }

    /**
     * handles when End Turn button is clicked.
     */
    public void endTurn(){
        graveyard=new ArrayList<>();
        board=clonedBoard;
        turn=new ArrayList<>();
        isComplete=false;
        wasKinged=false;
        if(theirTurn.equals(redPlayer))
            theirTurn=whitePlayer;
        else
            theirTurn=redPlayer;
    }

    /**
     * Get whose turn it is
     * @return whose turn it is
     */
    public Player whoseTurn(){
        return theirTurn;
    }

    public Player getWinner(){
        return winner;
    }

    public void setWinner(Player a){
        winner = a;
    }

    public void setGameOver(boolean a){
        gameOver = a;
    }

    public boolean getGameStatus(){
        return gameOver;
    }

    public boolean noMorePieces(){
        int countRed = 0;
        int countWhite = 0;
                
        for (int r = 0; r <= Board.BOARD_SIZE - 1; r++) {
            for (int c = 0; c <= Board.BOARD_SIZE - 1; c++) {
                Position pos = new Position(r, c);
                Piece pce = board.getSpace(pos).getPiece();
                if(pce != null){
                    if(pce.getColor() == Piece.Color.RED){
                        countRed++;
                    }
                    if(pce.getColor() == Piece.Color.WHITE){
                        countWhite++;
                    }
                }
            }
        }
        if(countRed == 0){
            this.winner = this.whitePlayer;
            return true;
        }
        if(countWhite == 0){
            this.winner = this.redPlayer;
            return true;
        }
        return false;
    }

    /**
     * @return The board represented in String format
     */
/*    public String toString() {
        String toReturn = "";
        //ArrayList<Row> boardArray = board.getBoard();
        for(int i = 0; i < Board.BOARD_SIZE; i++) {
            for(int j = 0; j < Board.BOARD_SIZE; j++) {
                if(board.getSpace(i, j).isSpaceValid()) {
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
    */
    
    public void kingPiece(Position p){
        board.getSpace(p).kingPiece();
        wasKinged=true;
    }

    /**
     * determines if a player has moves or not
     * @return true if player has no moves
     */
    public boolean hasNoMoves() {
        MoveValidator validate = new MoveValidator(this);
        if(theirTurn.equals(redPlayer)) {
            for (int i = 0; i < Board.BOARD_SIZE; i++) {
                for (int j = 0; j < Board.BOARD_SIZE; j++) {
                    Space space = board.getSpace(i, j);
                    if (space.hasPiece()) {
                        Piece piece = space.getPiece();
                        if (piece.getColor() == Piece.Color.RED) {
                            if (!validate.shouldMakeJump(Piece.Color.RED) && checkAllSimpleMoves(new Position(i,j), piece)) {
                                this.winner=whitePlayer;
                                return true;
                            }
                        }
                    }
                }
            }
        }
        else {//white player
            for (int i = 0; i < Board.BOARD_SIZE; i++) {
                for (int j = 0; j < Board.BOARD_SIZE; j++) {
                    Space space = board.getSpace(i, j);
                    if (space.hasPiece()){
                        Piece piece=space.getPiece();
                        if (piece.getColor() == Piece.Color.WHITE) {
                            if (!validate.shouldMakeJump(Piece.Color.WHITE) && checkAllSimpleMoves(new Position(i, j), piece)) {
                                this.winner=redPlayer;
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * helper method for hasNoMoves that checks all simple moves of a specific piece
     * @return true if a move can NOT happen
     */
    public boolean checkAllSimpleMoves(Position pos, Piece piece){
        Position upL=null, upR=null, downL=null, downR=null;
        if(pos.getRow()!=0 && pos.getCell()!=0) {
            upL = new Position(pos.getRow() - 1, pos.getCell() - 1);
        }
        if(pos.getRow()!=0 && pos.getCell()!=7) {
            upR=new Position(pos.getRow()-1, pos.getCell()+1);
        }
        if(pos.getRow()!=7 && pos.getCell()!=0) {
            downL = new Position(pos.getRow() + 1, pos.getCell() - 1);
        }
        if(pos.getRow()!=7 && pos.getCell()!=7) {
            downR = new Position(pos.getRow() + 1, pos.getCell() - 1);
        }
        if(piece.getColor()== Piece.Color.RED) {
            if (!piece.isKing()) {
                if ((upL == null || board.getSpace(upL).hasPiece()) && (upR == null || board.getSpace(upR).hasPiece())) {
                    return true;
                }
            }
        }
        else {
            if((downR==null || board.getSpace(downR).hasPiece()) && (downL==null ||board.getSpace(downL).hasPiece())){
                return true;
            }
        }
        if((upL==null || board.getSpace(upL).hasPiece()) && (upR==null ||board.getSpace(upR).hasPiece())&&
                (downR==null || board.getSpace(downR).hasPiece()) && (downL==null ||board.getSpace(downL).hasPiece())){
         //king pieces
               return true;
        }
        return false;
    }

    public Player getLoser() {
        if(winner.equals(redPlayer))
            return whitePlayer;
        else
            return redPlayer;
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
