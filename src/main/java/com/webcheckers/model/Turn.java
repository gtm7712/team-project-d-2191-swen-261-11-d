package com.webcheckers.model;

import java.util.ArrayList;

public class Turn {
    private ArrayList<Move> turn= new ArrayList<>();
    private boolean wasKinged=false;
    private ArrayList<Piece>graveyard=new ArrayList<>();  //pieces removed this turn

    public void graveyardAdd(Piece piece){
        graveyard.add(piece);
    }

    public void add(int index, Move madeMove){
        turn.add(index, madeMove);
    }

    public void add(Move madeMove){
        turn.add(madeMove);
    }

    public int size(){
        return turn.size();
    }

    public Move get(int index){
        return turn.get(index);
    }

    public boolean wasKinged(){
        return wasKinged;
    }

    public void setWasKinged(boolean wasKinged){
        this.wasKinged=wasKinged;
    }

    public Move remove(int index){
        return turn.remove(index);
    }

    public Piece graveyardGet(int index){
        return graveyard.get(index);
    }
    
    public Piece graveyardRemove(int index){
        return graveyard.remove(index);
    }
    
    public int graveyardSize(){
        return graveyard.size();
    }
}
