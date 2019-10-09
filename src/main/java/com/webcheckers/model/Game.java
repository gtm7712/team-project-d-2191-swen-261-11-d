package com.webcheckers.model;

public class Game {
    private Board board;
    private Player redPlayer;
    private Player whitePlayer;

    public Game() {
        this.board = new Board();
    }

    public Player getWhitePlayer(){
        return whitePlayer;
    }

    public Player getRedPlayer(){
        return redPlayer;
    }

    public void setRedPlayer(Player p){
        this.redPlayer = p;
    }

    public void setWhitePlayer(Player p){
        this.whitePlayer = p;
    }

    public Board getBoardRed() {
        return board;
    }

    public Board getBoardWhite() {
        return board.flipped();
    }

    public String toString() {
        String toReturn = "";
        Space[][] boardArray = board.getBoard();
        for(int i = 0; i < Board.BOARD_SIZE; i++) {
            for(int j = 0; j < Board.BOARD_SIZE; j++) {
                if(boardArray[i][j].isValid()) {
                    if(boardArray[i][j].hasPiece()) {
                        if(boardArray[i][j].isPieceRed()) {
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


    public static void main(String[] args) {
        // Stuff for testing
        Game game = new Game();

        System.out.println(game);
    }
}
