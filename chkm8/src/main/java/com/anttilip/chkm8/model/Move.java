package com.anttilip.chkm8.model;

import com.anttilip.chkm8.model.pieces.Piece;

/**
 * Represents one move and board state before the move.
 */
public class Move {

    private final Piece piece;
    private final Position target;
    private final Position origin;
    private final Board board;

    /**
     * Constructs the move.
     * @param piece Piece that is moved.
     * @param origin Position from which piece is moved.
     * @param target Position to which piece is moved.
     * @param board Board before piece is moved
     */
    Move(Piece piece, Position origin, Position target, Board board) {
        this.piece = piece;
        this.origin = origin;
        this.target = target;
        this.board = board;
    }

    public Piece getPiece() {
        return this.piece;
    }

    Position getTarget() {
        return this.target;
    }

    Position getOrigin() {
        return this.origin;
    }

    int getBoardHash() {
        return this.board.hashCode();
    }
    
    public Board getBoard() {
        return this.board;
    }

    @Override
    public String toString() {
        return piece.toString() + " from " + origin.toString() + " to " + target.toString();
    }

}
