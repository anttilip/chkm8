package com.anttilip.chkm8.model.pieces;

import com.anttilip.chkm8.model.Player;
import com.anttilip.chkm8.model.Position;

/**
 * Represents a queen, extends the abstract Piece class.
 */
public class Queen extends Piece {
    /**
     * Queens allowed move directions.
     */
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

    /**
     * Constructor for queen.
     * @param position Position of the piece.
     * @param player Player who controls the piece.
     */
    public Queen(Position position, Player player) {
        super(position, player, true);
    }

    @Override
    public Position[] getMoveDirections() {
        return MOVE_DIRECTIONS;
    }

    @Override
    public Piece copy() {
        return new Queen(this.position.copy(), this.player);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Queen)) {
            return false;
        }
        Queen other = (Queen) o;
        return other.position.equals(this.position) && other.player == this.player;
    }
}
