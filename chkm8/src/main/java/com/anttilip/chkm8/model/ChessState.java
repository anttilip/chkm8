package com.anttilip.chkm8.model; 

/**
 *
 * @author antti
 */
public class ChessState {
    private final Board board;
    private Player currentPlayer;
    private int moveCount;

    public ChessState() {
        this.board = new Board();
        this.currentPlayer = Player.WHITE;
        this.moveCount = 0;
    }
    
    public Board getBoard() {
        return this.board;
    }

    public void run() {
        moveCount++;
        // Do moves ()
        // Switch players in the end of turn
        if (this.currentPlayer == Player.WHITE) {
            this.currentPlayer = Player.BLACK;
        } else {
            this.currentPlayer = Player.WHITE;
        }
    }
    
    public int getMoveCount() {
        return this.moveCount;
    }
    
    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }
        
}
