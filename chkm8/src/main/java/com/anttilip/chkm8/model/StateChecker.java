package com.anttilip.chkm8.model;

import com.anttilip.chkm8.model.pieces.*;

import java.util.*;

/**
 * Class that checks game states for a given ChessState object.
 */
final class StateChecker {
    /**
     * Static method that checks all game states, e.g. check and stalemate for the given ChessState
     * @param chessState ChessState for which game states are calculated
     * @return EnumSet of GameStates that given chessState is in.
     */
    static EnumSet<GameState> checkStates(ChessState chessState) {
        EnumSet<GameState> gameStates = EnumSet.noneOf(GameState.class);
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

    /**
     * Checks if game has had 50 and 75 turns.
     * @param chessState
     * @param gameStates
     */
    private static void checkMoveCountStates(ChessState chessState, Set<GameState> gameStates) {
        if (chessState.getMoveCount() >= 50) {
            gameStates.add(GameState.MOVE50);
        }
        if (chessState.getMoveCount() >= 75) {
            gameStates.add(GameState.MOVE75);
        }
    }

    /**
     * Checks if any players king is in check or checkmate
     * @param chessState
     * @param gameStates
     */
    private static void checkCheckStates(ChessState chessState, Set<GameState> gameStates) {
        for (Player player : Player.values()) {
            if (chessState.getBoard().isPlayerChecked(player)) {
                // Player is checked
                gameStates.add(GameState.CHECK);
                if (!playerHasAllowedMoves(chessState, player)) {
                    // Player is in checkmate
                    gameStates.add(GameState.CHECKMATE);
                }
            }
        }
    }

    /**
     * Checks if game is in stalemate.
     * @param chessState
     * @param gameStates
     */
    private static void checkStalemate(ChessState chessState, Set<GameState> gameStates) {
        if (!gameStates.contains(GameState.CHECK)) {
            // If player is checked, game can't be a draw
            boolean atLeastOneMove = false;
            for (Piece piece : chessState.getPlayersPieces(chessState.getCurrentPlayer())) {
                if (!chessState.getGetPiecesAllowedMoves(piece).isEmpty()) {
                    atLeastOneMove = true;
                }
            }
            if (!atLeastOneMove) {
                gameStates.add(GameState.STALEMATE);
            }
        }
    }

    /**
     * Checks if same board configuration has happened 3 and 5 times already.
     * @param chessState
     * @param gameStates
     */
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

        if (Collections.max(moveCounter.values()) >= 5) {
            gameStates.add(GameState.REPETITION5);
        }
        if (Collections.max(moveCounter.values()) >= 3) {
            gameStates.add(GameState.REPETITION3);
        }
    }

    /**
     * Check if both players have enough pieces for a checkmate.
     *
     * Simplified to only a few most common scenarios.
     * @param chessState
     * @param gameStates
     */
    private static void checkInsufficientMaterial(ChessState chessState, Set<GameState> gameStates) {
        if (gameStates.contains(GameState.CHECK)) {
            // Insufficient material can't be declared when game is in check
            return;
        }
        for (Player player : Player.values()) {
            Player other = Player.getOther(player);
            if (chessState.getPlayersPieces(player).size() != 1) {
                // Player with less pieces must have only king
                continue;
            }

            // Check piece requirements
            boolean alreadyOneBishopOrKnight = false;
            List<Piece> pawns = new ArrayList<>();
            for (Piece piece : chessState.getPlayersPieces(other)) {
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
                if (!chessState.getGetPiecesAllowedMoves(pawn).isEmpty()) {
                    return;
                }
            }
            gameStates.add(GameState.INSUFFICIENT);
        }
    }

    /**
     * Check if any piece for given player has any allowed moves.
     * @param chessState
     * @param player Player whose allowed moves are calculated.
     * @return Boolean value of player having any allowed moves.
     */
    private static boolean playerHasAllowedMoves(ChessState chessState, Player player) {
        for (Piece piece : chessState.getPlayersPieces(player)) {
            if (!chessState.getGetPiecesAllowedMoves(piece).isEmpty()) {
                return true;
            }
        }
        return false;
    }
}
