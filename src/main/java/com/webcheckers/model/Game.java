package com.webcheckers.model;

import com.webcheckers.model.Piece.Color;
import com.webcheckers.ui.PostValidateMoveRoute;
import com.webcheckers.util.MoveValidator;
import com.webcheckers.util.ReplayHelper;

import java.util.ArrayList;

/**
 * The Game
 */
public class Game {
    private Board board;
    private Player redPlayer;
    private Player whitePlayer;
    //private ArrayList<Move>turn= new ArrayList<>();
    private Player theirTurn;     //says who's turn it is
    private Player winner;
    private boolean gameOver;
    private HeldGame heldGame;
    private boolean isComplete=false;
    private Turn turn1;
    private int gameID;
    private ReplayHelper replayHelper;
    private String replayString; // Set when the game ends

    //private boolean wasKinged=false;
    //private ArrayList<Piece>graveyard=new ArrayList<>();  //pieces removed this turn

    private Replay replay;

    /**
     * Create a new Game
     * Note: The ReplayHelper is initialized with bogus data
     */
    public Game() {
        this.board = new Board();
        this.gameOver = false;
        this.replay = new Replay();
        this.turn1= new Turn();
        this.replayHelper = new ReplayHelper(
            "Mitsuha", "Samantha", board
        );
    }

    public Game(Player redPlayer, Player whitePlayer) {
        this.board = new Board();
        this.gameOver = false;
        this.replay = new Replay();
        this.turn1= new Turn();

        this.replayHelper = new ReplayHelper(
            redPlayer.getName(), whitePlayer.getName(), board
        );
    }

    /**
     *
     * @return the games id
     */
    public int getID(){
        return gameID;
    }

    /**
     * sets the game id
     * @param id
     */
    public void setID(int id){
        this.gameID=id;
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
     * sets the held game
     * @param heldGame new held game
     */
    public void setHeldGame(HeldGame heldGame){
        this.heldGame=heldGame;
    }
    public HeldGame getHeldGame(){
        return heldGame;
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
     * Adds a move to your turn, to be ready to be submitted when your turn is swapped
     * @param madeMove move that went through validation
     */
    public void makeMove(Move madeMove){

        if(madeMove.getEnd().getRow()==-1 && madeMove.getEnd().getCell()==-1) {
            turn1.graveyardAdd(board.getSpace(madeMove.getStart()).getPiece());
            //graveyard.add(board.getSpace(madeMove.getStart()).getPiece());
            turn1.add(0, madeMove);
            board.getSpace(madeMove.getStart()).removePiece();
        }else {
            turn1.add(madeMove);
            board.makeMove(madeMove);
        }
    }

    /**
     * handles if revert turn button is clicked.
     * 
     * @return Error message, if applicaple, or Null if successful 
     */

    public String revertTurn(){
        isComplete=false;
        // this.clonedBoard=new Board(board.getBoard());
        int i=turn1.size()-1;
        Move move=turn1.get(i);
        if(turn1.wasKinged()) {
            if (move.getEnd().getRow() == board.BOARD_SIZE - 1 && theirTurn.equals(whitePlayer)) {
                board.getSpace(move.getEnd()).unKingPiece();
                turn1.setWasKinged(false);
            } else if (move.getEnd().getRow() == 0 && theirTurn.equals(redPlayer)) {
                board.getSpace(move.getEnd()).unKingPiece();
                turn1.setWasKinged(false);
            }
        }
        board.makeMove(new Move(move.getEnd(), move.getStart()));
            if(turn1.get(0).getEnd().getCell()==-1 && turn1.get(0).getEnd().getRow()==-1){
                board.getSpace(turn1.get(0).getStart()).setPiece(turn1.graveyardGet(turn1.graveyardSize()-1));
                turn1.graveyardRemove(turn1.graveyardSize()-1);
                turn1.remove(0);
                        i--;
            }


        turn1.remove(i);
        return null;
    }
        
    /**
     * handles when End Turn button is clicked.
     */
    public void endTurn(){
        // heldGame.add(turn1);
        turn1 = new Turn();
        //replay.updateReplay(clonedBoard);
        //System.out.println("Replay:\n" + replay.getEncoding() + "\n");
        // board=clonedBoard;
        isComplete=false;
        if(theirTurn.equals(redPlayer))
        theirTurn=whitePlayer;
        else
        theirTurn=redPlayer;
        
        replayHelper.record(board);
    }

    /**
     * Get whose turn it is
     * @return whose turn it is
     */
    public Player whoseTurn(){
        return theirTurn;
    }

    /**
     * Gets the winner of the game
     * @return the player who won
     */
    public Player getWinner(){
        return winner;
    }

    /**
     * sets the winner
     * @param a the player who won
     */
    public void setWinner(Player a){
        winner = a;

        replayString = replayHelper.getReplay();
        replayHelper.loadReplay(replayString);
    }

    /**
     * changes the boolean of the gamestatus
     * @param a true or false depending on if the game is over
     */
    public void setGameOver(boolean a){
        gameOver = a;
    }

    /**
     * gets the status of the game
     * @return true if the game is over else, false
     */
    public boolean getGameStatus(){
        return gameOver;
    }

    /**
     * Gets replay for game
     * @return Replay object for game
     */
    public Replay getReplay() {
        return replay;
    }

    /**
     * check if there are any more pieces in the game for a color
     * @return true if any color has no more pieces
     */
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
     * Get this game's ReplayHelper instance
     * @return the ReplayHelper
     */
    public ReplayHelper getReplayHelper() {
        return this.replayHelper;
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
    
    /**
     * Sets a piece to king
     * @param p the position of where the piece is
     */
    public void kingPiece(Position p){
        board.getSpace(p).kingPiece();
        turn1.setWasKinged(true);
    }

    /**
     * Test method to force WHITE's turn
     */
    public void __test_set_white_turn() {
        theirTurn = whitePlayer;
    }

    /**
     * Test method to remove all color pieces
     * @param color Color to remove
     */
    public void __test_remove_all_pieces(Color color) {
        for (int r = 0; r <= Board.BOARD_SIZE - 1; r++) {
            for (int c = 0; c <= Board.BOARD_SIZE - 1; c++) {
                Space space = board.getSpace(r, c);
                
                if (space.hasPiece()) {
                    if (space.getPiece().getColor() == color) {
                        space.removePiece();
                    }
                }
            }
        }
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
                            if(!checkAllSimpleMoves(new Position(i,j), piece))
                                return false;
                        }
                    }
                }
            }
            if (!validate.shouldMakeJump(Piece.Color.RED)) {
                this.winner=whitePlayer;
                return true;
            }
        }
        else {//white player
            for (int i = 0; i < Board.BOARD_SIZE; i++) {
                for (int j = 0; j < Board.BOARD_SIZE; j++) {
                    Space space = board.getSpace(i, j);
                    if (space.hasPiece()){
                        Piece piece=space.getPiece();
                        if (piece.getColor() == Piece.Color.WHITE) {
                            if(!checkAllSimpleMoves(new Position(i, j), piece)) {
                                return false;
                            }
                        }
                    }
                }
            }
            if (!validate.shouldMakeJump(Piece.Color.WHITE)) {
                this.winner=redPlayer;
                return true;
            }
        }

        return false;
    }

    /**
     * helper method for hasNoMoves that checks all simple moves of a specific piece
     * @return true if a move can NOT happen
     */
    public boolean checkAllSimpleMoves(Position pos, Piece piece) {

        Position upL = null, upR = null, downL = null, downR = null;
        if (pos.getRow() != 0 && pos.getCell() != 0) {
            upL = new Position(pos.getRow() - 1, pos.getCell() - 1);
        }
        if (pos.getRow() != 0 && pos.getCell() != 7) {
            upR = new Position(pos.getRow() - 1, pos.getCell() + 1);
        }
        if (pos.getRow() != 7 && pos.getCell() != 0) {
            downL = new Position(pos.getRow() + 1, pos.getCell() - 1);
        }
        if (pos.getRow() != 7 && pos.getCell() != 7) {
            downR = new Position(pos.getRow() + 1, pos.getCell() + 1);
        }
        if (piece.getColor() == Piece.Color.RED) {
            if (!piece.isKing()) {
                if (upL != null) {
                    if (!board.getSpace(upL).hasPiece()) {
                        return false;
                    }
                }
                if (upR != null) {
                    if (!board.getSpace(upR).hasPiece()) {
                        return false;
                    }
                }
            }
        } else {
            if (!piece.isKing()) {
                if (downL != null) {
                    if (!board.getSpace(downL).hasPiece()) {
                        return false;
                    }
                }
                if (downR != null) {

                    if (!board.getSpace(downR).hasPiece()) {
                        return false;
                    }
                }
            }
        }
        if(!piece.isKing()) {
            return true;
        }
        if (downL != null) {
            if (!board.getSpace(downL).hasPiece()) {
                return false;
            }
        }
        if (downR != null) {
            if (!board.getSpace(downR).hasPiece()) {
                return false;
            }
        }
        if (upL != null) {
            if (!board.getSpace(upL).hasPiece()) {
                return false;
            }
        }
        if (upR != null) {
            if (!board.getSpace(upR).hasPiece()) {
                return false;
            }
        }
        return true;
    }
    public  boolean hasJumped(){
        return turn1.size()>1;
    }
    /**
     * return the loser of the game
     * @return the loser of the game
     */
    public Player getLoser() {
        if(winner.equals(redPlayer))
            return whitePlayer;
        else
            return redPlayer;
    }

    /**
     * Get the Replay String.
     * Note: The replay string will be *null* until the game ends.
     * @return the replay string
     */
    public String getReplayString() {
        return replayString;
    }

    public void setBoard(Board board){
        this.board = board;
    }

    public void setReplay(ReplayHelper helper){
        this.replayHelper = helper;
    }
}
