package com.webcheckers.model;

public class Game {
    private final static int BOARD_SIZE = 8;
    private Space[][] board;

    public Game() {
        for(int i = 0; i < BOARD_SIZE; i++) {
            for(int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = new Space(i, j, (i%2==0) == (j%2==0));
            }
        }
    }
}
