/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anttilip.chkm8.model.pieces;

import com.anttilip.chkm8.model.Board;
import com.anttilip.chkm8.model.MoveLimitation;
import com.anttilip.chkm8.model.Player;
import com.anttilip.chkm8.model.Position;

import java.util.EnumSet;
import java.util.List;

/**
 *
 * @author antti
 */
public class King extends Piece {
    private static final Position[] MOVE_DIRECTIONS = {
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

    @Override
    public void move(Position newPosition, Board board) {
        this.firstMove = false;
        // Check if castling
        if (board.getPiece(newPosition) != null && board.getPiece(newPosition).getPlayer() == this.player) {
            // Since eating own pieces is not allowed, piece must be rook
            Piece rook = board.getPiece(newPosition);
            if (this.position.getX() < rook.position.getX()) {
                // King side castling
                this.position = Position.add(this.position, new Position(2, 0));
                board.movePiece(rook, Position.add(rook.position, new Position(-2, 0)));
            } else {
                // Queen side castling
                this.position = Position.add(this.position, new Position(-2, 0));
                board.movePiece(rook, Position.add(rook.position, new Position(3, 0)));
            }
        } else {
            // No castling, so regular movement
            if (board.getPiece(newPosition) != null) {
                board.getPiece(newPosition).kill(board);
            }
            this.position = newPosition;
        }
    }

    @Override
    public void getSpecialMoves(Board board, EnumSet<MoveLimitation> limit, List<Position> allowedMoves) {
        // Castling
        if (limit.contains(MoveLimitation.IGNORE_CASTLING)) {
            return;
        }
        for (Rook rook : board.getRooks(this.player)) {
            if (board.isCastlingAllowed(this, rook)) {
                allowedMoves.add(rook.getPosition());
            }
        }
    }

    public boolean isFirstMove() {
        return this.firstMove;
    }

    @Override
    Position[] getMoveDirections() {
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
