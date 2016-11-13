/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anttilip.chkm8.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author antti
 */
public class King extends Piece {
    private boolean isChecked;
    
    public King(Position position, Player player) {
        super(position, player);
        this.isChecked = false;
    }
    
    public boolean isChecked() {
        return this.isChecked;
    }

    @Override
    public List<Position> getAllowedMoves(HashMap<Position, Piece> occupiedPositions) {
        List<Position> allowedMoves = new ArrayList();
        List<Position> potentialMoves = new ArrayList();
              
        // Up
        potentialMoves.add(Position.add(this.position, new Position(0, 1)));

        // Left
        potentialMoves.add(Position.add(this.position, new Position(-1, 0)));

        // Down
        potentialMoves.add(Position.add(this.position, new Position(0, -1)));

        // Right
        potentialMoves.add(Position.add(this.position, new Position(1, 0)));

        for (Position target : potentialMoves) {
            if (!target.onBoard()) {
                // If position is out of board, move can't be allowed
                continue;
            }
            if (!occupiedPositions.containsKey(target)
                    || (occupiedPositions.containsKey(target)
                    && occupiedPositions.get(target).player != this.player)) {
                // Move is allowed if position doesn't have own piece
                allowedMoves.add(target);
            }
        }

        return allowedMoves;
    }


    
}
