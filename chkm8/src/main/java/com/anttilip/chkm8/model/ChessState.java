package com.anttilip.chkm8.model; 

import com.anttilip.chkm8.model.pieces.Piece;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author antti
 */
public class ChessState {
    private final Board board;
    private final List<Move> moveHistory;
    private Player currentPlayer;

    public ChessState() {
        this.board = new Board();
        this.currentPlayer = Player.WHITE;
        this.moveHistory = new ArrayList();
    }
    
    public Board getBoard() {
        return this.board;
    }

    public void move(Piece piece, Position targetPosition) {
        Move move = new Move(piece, targetPosition);
        board.movePiece(piece, targetPosition);
        moveHistory.add(move);

        // Switch players in the end of turn
        switchPlayers();
    }
    
    private void switchPlayers() {
        currentPlayer = (currentPlayer == Player.WHITE) ? Player.BLACK : Player.WHITE;
    }
    
    public int getMoveCount() {
        return this.moveHistory.size();
    }
    
    public List<Move> getMoveHistory() {
        return this.moveHistory;
    }
    
    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }
        
}
