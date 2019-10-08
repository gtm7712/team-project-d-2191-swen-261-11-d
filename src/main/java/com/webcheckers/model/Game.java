package com.webcheckers.model;

public class Game {
    private Board board;

    public Game() {
        this.board = new Board();
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

    public Space[][] getBoard(){
        return board;
    }

    public static void main(String[] args) {
        // Stuff for testing
        Game game = new Game();

        System.out.println(game);
    }
}
