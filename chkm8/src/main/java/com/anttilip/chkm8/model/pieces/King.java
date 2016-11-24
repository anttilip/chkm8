/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anttilip.chkm8.model.pieces;

import com.anttilip.chkm8.model.Board;
import com.anttilip.chkm8.model.Player;
import com.anttilip.chkm8.model.Position;

import java.util.List;

/**
 * Represents a king, extends the abstract Piece class.
 */
public class King extends Piece {
    /**
     * Kings allowed move directions.
     */
    public static final Position[] MOVE_DIRECTIONS = {
        new Position(1, 0), // Right
        new Position(0, 1), // Up
        new Position(-1, 0), // Left
        new Position(0, -1), // Down
        new Position(1, 1), // Up-Right
        new Position(-1, 1), // Up-Left
        new Position(-1, -1), // Down-Left
        new Position(1, -1) // Down-Right
    };
    private boolean firstMove;

    public King(Position position, Player player) {
        super(position, player, false);
        firstMove = true;
    }

    /**
     * Move king to a given position.
     *
     * King is a special piece and can castle, so moving is a bit different than regular pieces movement.
     * @param newPosition Position that piece is moved to.
     * @param board Board that piece belongs to.
     */
    @Override
    public void move(Position newPosition, Board board) {
        this.firstMove = false;
        // Check if castling
        if (board.getPiece(newPosition) != null && board.getPiece(newPosition).getPlayer() == this.player) {
            // Since eating own pieces is not allowed, piece must be rook
            Piece rook = board.getPiece(newPosition);
            if (this.position.getX() < rook.position.getX()) {
                // King side castling
                this.position.add(2, 0);
                board.movePiece(rook, rook.position.copy().add(-2, 0));
            } else {
                // Queen side castling
                this.position.add(-2, 0);
                board.movePiece(rook, rook.position.copy().add(3, 0));
            }
        } else {
            // No castling, so regular movement
            if (board.getPiece(newPosition) != null) {
                board.getPiece(newPosition).kill(board);
            }
            this.position = newPosition;
        }
    }

    /**
     * Adds kings castling moves to the given list.
     *
     * Checks from board if castling is allowed and adds castling moves accordingly.
     * @param board Board that piece belongs to.
     * @param possibleMoves List of possible positions that piece can move to.
     */
    @Override
    public void getSpecialMoves(Board board, List<Position> possibleMoves) {
        // Castling
        for (Rook rook : board.getRooks(this.player)) {
            if (board.isCastlingAllowed(this, rook)) {
                possibleMoves.add(rook.getPosition());
            }
        }
    }

    public boolean isFirstMove() {
        return this.firstMove;
    }

    @Override
    public Position[] getMoveDirections() {
        return MOVE_DIRECTIONS;
    }


    @Override
    public Piece copy() {
        return new King(this.position, this.player);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof King)) {
            return false;
        }
        King other = (King) o;
        return other.position.equals(this.position) && other.player == this.player;
    }

}
