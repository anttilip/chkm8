/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anttilip.chkm8.model.pieces;

import com.anttilip.chkm8.model.Board;
import com.anttilip.chkm8.model.Player;
import com.anttilip.chkm8.model.Position;

/**
 * Represents a rook, extends the abstract Piece class.
 */
public class Rook extends Piece {
    /**
     * Rooks allowed move directions.
     */
    private static final Position[] MOVE_DIRECTIONS = {
        new Position(1, 0), // Right
        new Position(0, 1), // Up
        new Position(-1, 0), // Left
        new Position(0, -1), // Down
    };
    private boolean firstMove;

    /**
     * Constructor for rook.
     * @param position Position of the piece.
     * @param player Player who controls the piece.
     */
    public Rook(Position position, Player player) {
        super(position, player, true);
        this.firstMove = true;
    }

    /**
     * Moves rook to the given position.
     *
     * Rook is special piece since it is used for castling.
     * @param newPosition Position that piece is moved to.
     * @param board Board that piece belongs to.
     */
    @Override
    public void move(Position newPosition, Board board) {
        this.firstMove = false;
        if (board.getPiece(newPosition) != null) {
            board.getPiece(newPosition).kill(board);
        }
        this.position = newPosition;
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
        return new Rook(this.position.copy(), this.player);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Rook)) {
            return false;
        }
        Rook other = (Rook) o;
        return other.position.equals(this.position) && other.player == this.player;
    }
}
