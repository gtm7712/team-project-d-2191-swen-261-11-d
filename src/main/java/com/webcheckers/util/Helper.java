package com.webcheckers.util;
import java.util.ArrayList;
import com.webcheckers.model.*;

/**
 * Utility class for the helper enhancement
 * 
 * @author Kyle Collins
 */
public class Helper {
    private Game game;

    /**
     * Create a new helper
     */
    public Helper (Game game) {
        this.game = game;
    }

    /**
     * Highlight Pieces on the board that can make a valid move
     */
    public void indicateValidPieces() {
        System.out.println("Valid piece");
    }

    /**
     * Find Pieces that can make a valid move
     * @return ArrayList<Piece> pieces
     */
    public ArrayList<Piece> validPieces(){
        ArrayList<Piece> pieces = new ArrayList<>();
        return pieces;
    }
    

}

