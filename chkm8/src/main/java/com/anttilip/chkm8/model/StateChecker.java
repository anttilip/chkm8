package com.anttilip.chkm8.model;

import com.anttilip.chkm8.model.pieces.*;

import java.util.*;

final class StateChecker {
    static Set<GameState> checkStates(ChessState chessState) {
        Set<GameState> gameStates = new HashSet<>();
        checkMoveCountStates(chessState, gameStates);
        checkCheckStates(chessState, gameStates);
        checkStalemate(chessState, gameStates);
        checkRepetitions(chessState, gameStates);
        checkInsufficientMaterial(chessState, gameStates);

        boolean noGameEndingStates = true;
        for (GameState state : GameState.GAME_ENDING_STATES) {
            if (gameStates.contains(state)) {
                noGameEndingStates = false;
            }
        }
        if (noGameEndingStates) {
            gameStates.add(GameState.INCOMPLETE);
        }

        return gameStates;
    }

    private static void checkMoveCountStates(ChessState chessState, Set<GameState> gameStates) {
        if (chessState.getMoveCount() >= 50) {
            gameStates.add(GameState.MOVE50);
        }
        if (chessState.getMoveCount() >= 75) {
            gameStates.add(GameState.MOVE75);
        }
    }

    private static void checkCheckStates(ChessState chessState, Set<GameState> gameStates) {
        for (Player player : Player.values()) {
            if (chessState.getBoard().isCheck(player)) {
                // Player is checked
                gameStates.add(GameState.CHECK);
                if (!playerHasAllowedMoves(chessState, player)) {
                    // Player is in checkmate
                    gameStates.add(GameState.CHECKMATE);
                }
            }
        }
    }

    private static void checkStalemate(ChessState chessState, Set<GameState> gameStates) {
        if (!gameStates.contains(GameState.CHECK)) {
            // If player is checked, game can't be a draw
            boolean atLeastOneMove = false;
            for (Piece piece : chessState.getBoard().getPieces(chessState.getCurrentPlayer())) {
                if (!chessState.getBoard().getAllowedMoves(piece).isEmpty()) {
                    atLeastOneMove = true;
                }
            }
            if (!atLeastOneMove) {
                gameStates.add(GameState.STALEMATE);
            }
        }
    }

    private static void checkRepetitions(ChessState chessState, Set<GameState> gameStates) {
        Map<Integer, Integer> moveCounter = new HashMap<>();
        // Include also this board configuration
        moveCounter.put(chessState.getBoard().hashCode(), 1);
        for (Move move : chessState.getMoveHistory()) {
            if (moveCounter.containsKey(move.getBoardHash())) {
                moveCounter.put(move.getBoardHash(), moveCounter.get(move.getBoardHash()) + 1);
            } else {
                moveCounter.put(move.getBoardHash(), 1);
            }
        }

        if (moveCounter.values().size() > 0 && Collections.max(moveCounter.values()) >= 5) {
            gameStates.add(GameState.REPETITION5);
        }
        if (moveCounter.values().size() > 0 && Collections.max(moveCounter.values()) >= 3) {
            gameStates.add(GameState.REPETITION3);
        }
    }

    private static void checkInsufficientMaterial(ChessState chessState, Set<GameState> gameStates) {
        // Simplified to few most common scenarios
        if (gameStates.contains(GameState.CHECK)) {
            // Insufficient material can't be declared when game is in check
            return;
        }
        for (Player player : Player.values()) {
            Player other = (player == Player.WHITE) ? Player.BLACK : Player.WHITE;
            if (chessState.getBoard().getPieces(player).size() != 1) {
                // Player with less pieces must have only king
                continue;
            }

            // Check piece requirements
            boolean alreadyOneBishopOrKnight = false;
            List<Piece> pawns = new ArrayList<>();
            for (Piece piece : chessState.getBoard().getPieces(other)) {
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
                if (!chessState.getBoard().getAllowedMoves(pawn).isEmpty()) {
                    return;
                }
            }
            gameStates.add(GameState.INSUFFICIENT);
        }
    }

    private static boolean playerHasAllowedMoves(ChessState chessState, Player p) {
        for (Piece piece : chessState.getBoard().getPieces(p)) {
            if (!chessState.getBoard().getAllowedMoves(piece).isEmpty()) {
                return true;
            }
        }
        return false;
    }
}
