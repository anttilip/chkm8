package com.anttilip.chkm8.model;

import com.anttilip.chkm8.model.pieces.*;

import java.util.EnumSet;
import java.util.List;
import java.util.Stack;

/**
 * Represents state of the game.
 *
 * Works as an API for manipulating the model e.g. moving pieces.
 */
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

    /**
     * Returns a piece at given x and y coordinates.
     * @param x x coordinate (zero-based index)
     * @param y y coordinate (zero-based index)
     * @return Piece in given coordinates, null if position is not occupied
     */
    public Piece getPieceAt(int x, int y) {
        return this.board.getPiece(x, y);
    }

    /**
     * Searches and returns players pieces
     * @param player
     * @return List of players pieces
     */
    public List<Piece> getPlayersPieces(Player player) {
        return this.board.getPieces(player);
    }

    /**
     * Searches and returns all allowed moves for a piece in given coordinate position.
     * @param x
     * @param y
     * @return List of positions that piece can move into.
     */
    public List<Position> getGetPiecesAllowedMoves(int x, int y) {
        return this.board.getAllowedMoves(this.board.getPiece(x, y));
    }

    /**
     * Moves given piece to a given position.
     * @param piece Piece that is moved
     * @param targetPosition Position that piece is moved into.
     */
    public void move(Piece piece, Position targetPosition) {
        Move move = new Move(piece, piece.getPosition(), targetPosition, board.copy());
        moveHistory.push(move);
        board.movePiece(piece, targetPosition);

        // Switch players in the end of turn
        switchPlayers();
    }

    /**
     * Returns game to a state that it was in before latest move.
     *
     * Pulls last board configuration from history stack and sets it as a current configuration.
     * Changes current player to what it was before the latest move.
     */
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

    /**
     * Calculates and returns all current GameStates e.g. check and stalemate.
     *
     * Passes itself to a static StateChecker that calculates and returns current game states.
     * @return EnumSet of current GameStates
     */
    public EnumSet<GameState> getGameStates() {
        return StateChecker.checkStates(this);
    }

    /**
     * Returns how many turns has passed.
     * @return Size of move history stack
     */
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
