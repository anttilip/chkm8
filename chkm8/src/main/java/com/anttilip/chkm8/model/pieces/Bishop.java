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
public class Bishop extends Piece {
    private static final Position[] MOVE_DIRECTIONS = {
        new Position(1, 1), // Up-Right
        new Position(-1, 1), // Up-Left
        new Position(-1, -1), // Down-Left
        new Position(1, -1) // Down-Right
    };

    public Bishop(Position position, Player player) {
        super(position, player, true);
    }

    @Override
     public Position[] getMoveDirections() {
        return MOVE_DIRECTIONS;
    }

    @Override
    public Piece copy() {
        return new Bishop(this.position, this.player);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Bishop)) {
            return false;
        }
        Bishop other = (Bishop) o;
        return other.position.equals(this.position) && other.player == this.player;
    }

}
