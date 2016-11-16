package com.anttilip.chkm8.model;

import com.anttilip.chkm8.model.pieces.*;

import java.util.List;
import java.util.Set;
import java.util.Stack;

public class ChessState {
    private Board board;
    private final Stack<Move> moveHistory;
    private Player currentPlayer;

    public ChessState() {
        this.board = Board.createBoard();
        this.currentPlayer = Player.WHITE;
        this.moveHistory = new Stack<>();
    }

    public Board getBoard() {
        return this.board;
    }

    public void move(Piece piece, Position targetPosition) {
        Move move = new Move(piece, piece.getPosition(), targetPosition, board.copy());
        moveHistory.push(move);
        board.movePiece(piece, targetPosition);

        // Switch players in the end of turn
        switchPlayers();
    }

    public void undoLastMove() {
        if (this.moveHistory.isEmpty()) {
            return;
        }
        Move lastMove = this.moveHistory.pop();
        this.board = lastMove.getBoard();
        this.switchPlayers();
    }

    private void switchPlayers() {
        currentPlayer = Player.getOther(currentPlayer);
    }

    public Set<GameState> getGameStates() {
        return StateChecker.checkStates(this);
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
