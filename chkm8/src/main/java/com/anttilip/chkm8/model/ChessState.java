package com.anttilip.chkm8.model;

import com.anttilip.chkm8.model.pieces.Piece;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 *
 * @author antti
 */
enum State {
    CHECK, CHECKMATE, STALEMATE, REPETITION3, REPETITION5,
    INSUFFICIENT, MOVE50, MOVE75, INCOMPLETE;
}

public class ChessState {

    private Board board;
    private final Stack<Move> moveHistory;
    private final Set<State> states;
    private Player currentPlayer;

    public ChessState() {
        this.board = Board.createBoard();
        this.currentPlayer = Player.WHITE;
        this.moveHistory = new Stack();
        this.states = new HashSet();
    }

    public Board getBoard() {
        return this.board;
    }

    public void move(Piece piece, Position targetPosition) {
        this.checkStates();
        Move move = new Move(piece, piece.getPosition(), targetPosition, board.hashCode(), board.copy());
        moveHistory.push(move);
        board.movePiece(piece, targetPosition);
        //moveHistory.add(move);

        // Switch players in the end of turn
        this.checkStates();
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
        currentPlayer = (currentPlayer == Player.WHITE) ? Player.BLACK : Player.WHITE;
    }

    public Set<State> getGameStates() {
        return this.states;
    }

    private void checkStates() {
        this.updateAllowedMoves();
        List<State> currentStates = new ArrayList();
        this.checkMoveCountStates();
        this.checkCheckStates();
        this.checkStalemate();
        this.checkRepetitions(currentStates);

        // TODO: Insufficient material
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

    private void checkMoveCountStates() {
        if (this.getMoveCount() >= 75) {
            this.states.add(State.MOVE75);
        }
        if (this.getMoveCount() >= 50) {
            this.states.add(State.MOVE50);
        }
    }

    private void checkCheckStates() {
        for (Player player : Player.values()) {
            if (this.board.isCheck(player)) {
                // Player is checked
                this.states.add(State.CHECK);
                for (Piece piece : this.board.getPieces(player)) {
                    if (!piece.getAllowedMoves(this.board, false).isEmpty()) {
                        break;
                    }
                }
                // Player is in checkmate
                this.states.add(State.CHECKMATE);
            }
        }
    }

    private void checkStalemate() {
        if (!this.states.contains(State.CHECK)) {
            // If player is checked, game can't be a draw
            boolean atLeastOneMove = false;
            for (Piece piece : this.board.getPieces()) {
                if (piece.getPlayer() == this.currentPlayer && !board.getAllowedMoves(piece).isEmpty()) {
                    atLeastOneMove = true;
                }
            }
            if (!atLeastOneMove) {
                this.states.add(State.STALEMATE);
            }
        }
    }

    private void checkRepetitions(List<State> currentStates) {
        Map<Integer, Integer> moveCounter = new HashMap();
        for (Move move : this.moveHistory) {
            if (moveCounter.containsKey(move.getBoardHash())) {
                moveCounter.put(move.getBoardHash(), moveCounter.get(move.getBoardHash()) + 1);
            } else {
                moveCounter.put(move.getBoardHash(), 1);
            }
        }

        if (moveCounter.values().size() > 0 && Collections.max(moveCounter.values()) >= 5) {
            currentStates.add(State.REPETITION5);
        }
        if (moveCounter.values().size() > 0 && Collections.max(moveCounter.values()) >= 3) {
            currentStates.add(State.REPETITION3);
        }
    }

    private void updateAllowedMoves() {
        // After piece is moved, checks may occur
        for (Piece piece : board.getPieces()) {
            board.getAllowedMoves(piece);
        }
    }

}
