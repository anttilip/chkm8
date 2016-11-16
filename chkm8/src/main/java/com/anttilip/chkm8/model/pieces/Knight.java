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
public class Knight extends Piece {
    private static final Position[] MOVE_DIRECTIONS = {
        new Position(1, 2), // Up Right
        new Position(-1, 2), // Up Left
        new Position(-2, 1), // Left Up
        new Position(-2, -1), // Left Down
        new Position(1, -2), // Down Right
        new Position(-1, -2),  // Down Left
        new Position(2, 1), // Right Up
        new Position(2, -1) // Right Down
    };

    public Knight(Position position, Player player) {
        super(position, player, false);
    }

    @Override
    Position[] getMoveDirections() {
        return MOVE_DIRECTIONS;
    }

    @Override
    public Piece copy() {
        return new Knight(this.position, this.player);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Knight)) {
            return false;
        }
        Knight other = (Knight) o;
        return other.position.equals(this.position) && other.player == this.player;
    }
}
