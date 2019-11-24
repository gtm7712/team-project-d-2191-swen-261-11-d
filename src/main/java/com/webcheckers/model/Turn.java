package com.webcheckers.model;

import java.util.ArrayList;

public class Turn {
    private ArrayList<Move> turn= new ArrayList<>();
    private boolean wasKinged=false;
    private ArrayList<Piece>graveyard=new ArrayList<>();  //pieces removed this turn

    /**
     * Adds a captured piece to the graveyard
     * @param piece
     *  the piece that was captured
     */
    public void graveyardAdd(Piece piece){
        graveyard.add(piece);
    }

    /**
     * add a move to the turn array
     * @param index
     *  the index of the turn
     * @param madeMove
     *  the move that was made
     */
    public void add(int index, Move madeMove){
        turn.add(index, madeMove);
    }

    /**
     * add the move to the array
     * @param madeMove
     *  the move that was made
     */
    public void add(Move madeMove){
        turn.add(madeMove);
    }

    /**
     * get the size of the turn
     * @return
     *  the size of the turn
     */
    public int size(){
        return turn.size();
    }

    /**
     * get the move corresponding to the index
     * @param index
     *  the index of the move
     * @return
     *  the move at a certain index
     */
    public Move get(int index){
        return turn.get(index);
    }

    /**
     * check if a piece was kinged during a move
     * @return
     *  true if a piece was kinged, false otherwise
     */
    public boolean wasKinged(){
        return wasKinged;
    }

    /**
     * set the boolean of kinged to true or false
     * @param wasKinged
     *  true or false
     */
    public void setWasKinged(boolean wasKinged){
        this.wasKinged=wasKinged;
    }

    /**
     * removing a move from a turn
     * @param index
     *  the index at which the move was made
     * @return 
     *  the move that is removed
     */
    public Move remove(int index){
        return turn.remove(index);
    }

    /**
     * get a piece from the captured
     * @param index
     *  the index it was captured
     * @return
     *  the piece that was captured
     */
    public Piece graveyardGet(int index){
        return graveyard.get(index);
    }
    
    /**
     * Removing a piece from the graveyard
     * @param index
     *  the index that the piece is at
     * @return
     *  the piece that is removed from the graveyard
     */
    public Piece graveyardRemove(int index){
        return graveyard.remove(index);
    }
    
    /**
     * get the size of the graveyard
     * @return
     *  the size of the graveyard
     */
    public int graveyardSize(){
        return graveyard.size();
    }
}
