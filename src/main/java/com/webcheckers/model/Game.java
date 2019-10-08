package com.webcheckers.model;

public class Game {
    private final static int BOARD_SIZE = 8;
    private Space[][] board = new Space[BOARD_SIZE][BOARD_SIZE];

    public Game() {
        for(int i = 0; i < BOARD_SIZE; i++) {
            for(int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = new Space(i, j, (i%2==0) == (j%2==0));
            }
        }
        resetGameBoard();
    }

    public void resetGameBoard() {
        for(int i = 0; i < BOARD_SIZE; i++) {
            for(int j = 0; j < BOARD_SIZE; j++) {
                Space space = board[i][j];
                if(space.isValid()) {
                    if(i < 3) {
                        space.setPiece(new Piece(Piece.Color.WHITE));
                    }
                    else if(i >= 5) {
                        space.setPiece(new Piece(Piece.Color.RED));
                    }
                }
            }
        }
    }

    public Space[][] getBoardRed() {
        return board;
    }

    public Space[][] getBoardWhite() {
        Space[][] flipped = new Space[BOARD_SIZE][BOARD_SIZE];
        for(int i = 0; i < BOARD_SIZE; i++) {
            for(int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = flipped[BOARD_SIZE - i][BOARD_SIZE - j];
            }
        }
        return flipped;
    }

    public String toString() {
        String toReturn = "";
        for(int i = 0; i < BOARD_SIZE; i++) {
            for(int j = 0; j < BOARD_SIZE; j++) {
                if(board[i][j].isValid()) {
                    if(board[i][j].hasPiece()) {
                        if(board[i][j].isPieceRed()) {
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
        Game game = new Game();

        System.out.println(game);
    }
}
