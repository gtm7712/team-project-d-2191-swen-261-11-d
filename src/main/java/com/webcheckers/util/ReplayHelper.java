package com.webcheckers.util;

import java.util.ArrayList;
import java.util.List;

import com.webcheckers.model.Board;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Space;
import com.webcheckers.model.Piece.Color;

/**
 * Helper class to assist in the reading and writing of WebCheckers replays
 */
public class ReplayHelper {
    
    private static final String CHNG_DELIM = ":";
    private static final String MOVE_DELIM = ";";

    private static final String RED_PAWN = "r";
    private static final String RED_KING = "R";
    private static final String WHT_PAWN = "w";
    private static final String WHT_KING = "W";

    private static final String ADDITION = "+";
    private static final String REMOVAL = "-";

    private static final String RESIGN = "y";
    private static final String WINNER = "z";

    private String redPlayer;
    private String whitePlayer;

    private GameResult gameResult;
    private List<Board> boards;
    private String replayOut;
    private String curMove;
    private Board prevBrd;
    private int index;

    /**
     * Create a new ReplayHelper
     * This constructor **MUST** be used if planning on creating a replay.
     * @param red Red player's name
     * @param white White player's name
     * @param boardIn Starter board
     */
    public ReplayHelper(String red, String white, Board boardIn) {
        this.prevBrd = new Board(boardIn.getPreset());
        boards = new ArrayList<Board>();
        this.replayOut = "";
        this.curMove = "";
        this.index = 0;
        recordPlayers(red, white);
    }

    /**
     * Create a new ReplayHelper
     * This constructor should only be used when reading a replay.
     */
    public ReplayHelper() {
        boards = new ArrayList<Board>();
        this.replayOut = "";
        this.curMove = "";
        this.index = -1;
    }

    /**
     * Record a move
     * @param boardIn Updated board *after* the move was made.
     */
    public void record(Board boardIn) {
        for (int row = 0; row < Board.BOARD_SIZE; row++) {
            for (int col = 0; col < Board.BOARD_SIZE; col++) {

                Space newSpace = boardIn.getSpace(row, col);
                Space oldSpace = prevBrd.getSpace(row, col);

                if (!newSpace.isCongruent(oldSpace)) { // Something changed
                    if (oldSpace.hasPiece() && !newSpace.hasPiece()) { // Removal
                        remove(oldSpace);
                    }
                    if (!oldSpace.hasPiece() && newSpace.hasPiece()) { // addition
                        add(newSpace);
                    }
                }

            }
        }
        releaseChanges();
        prevBrd = new Board(boardIn.getPreset());
    }

    /**
     * Record a player's resignation
     * @param c Color of the player that resigned
     */
    public void recordResign(Color c) {
        replayOut += RESIGN + ((c == Color.RED) ? "R" : "W") + MOVE_DELIM;
    }

    /**
     * Record a player's win
     * @param c Color of the player that resigned
     */
    public void recordWin(Color c) {
        replayOut += WINNER + ((c == Color.RED) ? "R" : "W") + MOVE_DELIM;
    }

    /**
     * Load a replay
     * @param rpy Replay string
     */
    public void loadReplay(String rpy) {
        List<String> moves = new ArrayList<String>();
        String[] msplits = rpy.split(MOVE_DELIM);
        redPlayer = msplits[0];
        whitePlayer = msplits[1];
        for (int i = 2; i < msplits.length; i++) {
            String move = msplits[i];
            moves.add(move);
        }

        //

        boards.add(new Board()); // initial board

        for (int i = 0; i < moves.size(); i++) {
            Board newboard = new Board(boards.get(i).getPreset()); // make new board from previous board
            String[] csplits = moves.get(i).split(CHNG_DELIM);            
            
            for (String move : csplits) {
                String[] tokens = move.split("(?!^)");

                if (move.startsWith(ADDITION)) {
                    // tokens[0] is the type
                    Piece piece = getPieceFromId(tokens[1]);
                    int row = Integer.parseInt(tokens[2]);
                    int col = Integer.parseInt(tokens[3]);
                    newboard.getSpace(row, col).setPiece(piece);
                } else if (move.startsWith(REMOVAL)) {
                    // tokens[0] is the type
                    int row = Integer.parseInt(tokens[1]);
                    int col = Integer.parseInt(tokens[2]);
                    newboard.getSpace(row, col).removePiece();
                } else if (move.startsWith(RESIGN)) {
                    switch(tokens[1]){
                        case "R":
                            this.gameResult = GameResult.RED_RESIGN;
                            break;
                        case "W":
                            this.gameResult = GameResult.WHITE_RESIGN;
                            break;
                    }
                } else if (move.startsWith(WINNER)) {
                    switch(tokens[1]){
                        case "R":
                            this.gameResult = GameResult.RED_WIN;
                            break;
                        case "W":
                            this.gameResult = GameResult.WHITE_WIN;
                            break;
                    }
                }
            }
            boards.add(newboard);
        }
    }

    /**
     * Get the next board
     * @return the next board
     */
    public Board next() {
        index++;
        return boards.get(index);
    }

    /**
     * Get the previous board
     * @return the previous board
     */
    public Board previous() {
        index--;
        return boards.get(index);
    }

    /**
     * Get the board at the index.
     */
    public Board getAtIndex(int i) {
        this.index = i;
        return boards.get(i);
    }

    /**
     * Record the player's names
     * @param red Red player's name
     * @param white White player's name
     */
    private void recordPlayers(String red, String white) {
        replayOut += red + MOVE_DELIM + white + MOVE_DELIM;
    }

    /**
     * Record the removal of a piece
     * @param oldSpace Space the piece was removed from
     */
    private void remove(Space oldSpace) {
        appendChange(REMOVAL + oldSpace.getRow() + oldSpace.getCellIdx());
    }

    /**
     * Record the addition of a piece
     * @param newSpace Space the piece was added to
     */
    private void add(Space newSpace) {
        Piece p = newSpace.getPiece();

        appendChange(ADDITION + getIdFromPiece(p) + 
            newSpace.getRow() + newSpace.getCellIdx());
    }

    /**
     * Append to the changes for the current move
     * @param change Change to add
     */
    private void appendChange(String change) {
        curMove += change + CHNG_DELIM;
    }

    /**
     * Release all changes for the current move to the replay string
     */
    private void releaseChanges() {
        if (curMove.length() > 0)
            curMove = curMove.substring(0, curMove.length() - 1); // Remove CHNG_DELIM

        curMove = curMove + MOVE_DELIM;
        replayOut += curMove;
        curMove = "";
    }

    /**
     * Get the ID from a piece
     * @param p Piece to identify
     * @return String representation of the piece
     */
    private String getIdFromPiece(Piece p) {
        if (p.isRed()) {
            if (p.isKing()) return RED_KING;
            else return RED_PAWN;
        } else {
            if (p.isKing()) return WHT_KING;
            else return WHT_PAWN;
        }
    }

    /**
     * Get the piece from an ID
     * @param id String representation of the piece
     * @return Piece represented by the ID
     */
    private Piece getPieceFromId(String id) {
        Piece result = null;
        switch (id) {
            case RED_KING:
                result = new Piece(Color.RED);
                result.king();
                break;
            case RED_PAWN:
                result = new Piece(Color.RED);
                break;
            case WHT_KING:
                result = new Piece(Color.WHITE);
                result.king();
                break;
            case WHT_PAWN:
                result = new Piece(Color.WHITE);
                break;
            default:
                break;
        }
        return result;
    }

    /**
     * Static method to get the Red player's name
     * @param replay Replay string to extract from
     * @return the Red player's name
     */
    public static String getRedName(String replay) {
        String[] msplits = replay.split(MOVE_DELIM);
        return msplits[0];
    }

    /**
     * Static method to get the White player's name
     * @param replay Replay string to extract from
     * @return the White player's name
     */
    public static String getWhiteName(String replay) {
        String[] msplits = replay.split(MOVE_DELIM);
        return msplits[1];
    }

    /** @return the replay */
    public String getReplay() { return replayOut.substring(0, replayOut.length() - 1); }
    /** @return True if you can go back in the board list */
    public boolean canGoBack() { return index > 0; }
    /** @return True if you can go forward in the board list */
    public boolean canGoForward() { return index < boards.size() - 1; }
    /** @return the name of the White player */
    public String getWhite() { return whitePlayer; }
    /** @return the name of the Red player */
    public String getRed() { return redPlayer; }
    /** @return the current index. */
    public int getIndex() { return index; }
    /** @return the number of boards stored. */
    public int getBoards() { return boards.size(); }

    /** @return the result of the game */
    public GameResult getResult() { return gameResult; }

    public enum GameResult {
        RED_WIN, WHITE_WIN, RED_RESIGN, WHITE_RESIGN
    };

}