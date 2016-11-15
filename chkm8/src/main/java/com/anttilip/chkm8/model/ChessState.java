package com.anttilip.chkm8.model;

import com.anttilip.chkm8.model.pieces.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class ChessState {
    private Board board;
    private final Stack<Move> moveHistory;
    private final Set<GameState> states;
    private Player currentPlayer;

    public ChessState() {
        this.board = Board.createBoard();
        this.currentPlayer = Player.WHITE;
        this.states = new HashSet<>();
        this.moveHistory = new Stack<>();
        //this.moveHistory.add(new Move(null, null, null, board.copy()));
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
        currentPlayer = (currentPlayer == Player.WHITE) ? Player.BLACK : Player.WHITE;
    }

    public Set<GameState> getGameStates() {
        this.checkStates();
        return this.states;
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

    private void checkStates() {
        this.states.clear();
        this.updateAllowedMoves();
        this.checkMoveCountStates();
        this.checkCheckStates();
        this.checkStalemate();
        this.checkRepetitions();
        this.checkInsufficientMaterial();
        
        boolean noGameEndingStates = true; 
        for (GameState state : GameState.GAME_ENDING_STATES) {
            if (this.states.contains(state)) {
                noGameEndingStates = false;
            }
        }

        if (noGameEndingStates) {
            this.states.add(GameState.INCOMPLETE);
        }
    }

    private void checkMoveCountStates() {
        if (this.getMoveCount() >= 50) {
            this.states.add(GameState.MOVE50);
        }
        if (this.getMoveCount() >= 75) {
            this.states.add(GameState.MOVE75);
        }
    }

    private void checkCheckStates() {
        for (Player player : Player.values()) {
            if (this.board.isCheck(player)) {
                // Player is checked
                this.states.add(GameState.CHECK);
                if (!this.playerHasMoves(player)) {
                    // Player is in checkmate
                    this.states.add(GameState.CHECKMATE);
                }
            }
        }
    }

    private void checkStalemate() {
        if (!this.states.contains(GameState.CHECK)) {
            // If player is checked, game can't be a draw
            boolean atLeastOneMove = false;
            for (Piece piece : this.board.getPieces(this.currentPlayer)) {
                if (!board.getAllowedMoves(piece).isEmpty()) {
                    atLeastOneMove = true;
                }
            }
            if (!atLeastOneMove) {
                this.states.add(GameState.STALEMATE);
            }
        }
    }

    private void checkRepetitions() {
        Map<Integer, Integer> moveCounter = new HashMap<>();
        // Include also this board configuration
        moveCounter.put(this.board.hashCode(), 1);
        for (Move move : this.moveHistory) {
            if (moveCounter.containsKey(move.getBoardHash())) {
                moveCounter.put(move.getBoardHash(), moveCounter.get(move.getBoardHash()) + 1);
            } else {
                moveCounter.put(move.getBoardHash(), 1);
            }
        }

        if (moveCounter.values().size() > 0 && Collections.max(moveCounter.values()) >= 5) {
            this.states.add(GameState.REPETITION5);
        }
        if (moveCounter.values().size() > 0 && Collections.max(moveCounter.values()) >= 3) {
            this.states.add(GameState.REPETITION3);
        }
    }

    private void checkInsufficientMaterial() {
        // Simplified to few most common scenarios
        if (this.states.contains(GameState.CHECK)) {
            // Insufficient material can't be declared when game is in check
            return;
        }
        for (Player player : Player.values()) {
            Player other = (player == Player.WHITE) ? Player.BLACK : Player.WHITE;
            if (this.board.getPieces(player).size() != 1) {
                // Player with less pieces must have only king
                continue;
            }

            // Check piece requirements
            boolean alreadyOneBishopOrKnight = false;
            List<Piece> pawns = new ArrayList<>();
            for (Piece piece : this.board.getPieces(other)) {
                if (piece instanceof Queen || piece instanceof Rook) {
                    return;
                } else if (piece instanceof Bishop || piece instanceof Knight) {
                    if (alreadyOneBishopOrKnight) {
                        return;
                    } else {
                        alreadyOneBishopOrKnight = true;
                    }
                } else if (piece instanceof Pawn) {
                    if (alreadyOneBishopOrKnight) {
                        return;
                    }
                    pawns.add(piece);
                }
            }

            // If player has only pawns, they can't have any moves
            for (Piece pawn : pawns) {
                if (!this.board.getAllowedMoves(pawn).isEmpty()) {
                    return;
                }
            }
            this.states.add(GameState.INSUFFICIENT);
        }
    }

    private void updateAllowedMoves() {
        // After piece is moved, checks may occur
        for (Piece piece : board.getPieces()) {
            board.getAllowedMoves(piece);
        }
    }

    private boolean playerHasMoves(Player p) {
        for (Piece piece : this.board.getPieces(p)) {
            if (!this.board.getAllowedMoves(piece).isEmpty()) {
                return true;
            }
        }
        return false;
    }
}
