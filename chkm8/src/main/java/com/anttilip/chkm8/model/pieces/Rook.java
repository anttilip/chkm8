/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anttilip.chkm8.model.pieces;

import com.anttilip.chkm8.model.Player;
import com.anttilip.chkm8.model.Position;

/**
 *
 * @author antti
 */
public class Rook extends Piece {
    private static final Position[] MOVE_DIRECTIONS = {
        new Position(1, 0), // Right
        new Position(0, 1), // Up
        new Position(-1, 0), // Left
        new Position(0, -1), // Down
    };

    public Rook(Position position, Player player) {
        super(position, player, true);
    }

    @Override
    Position[] getMoveDirections() {
        return MOVE_DIRECTIONS;
    }

    @Override
    public Piece copy() {
        return new Rook(this.position, this.player);
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
