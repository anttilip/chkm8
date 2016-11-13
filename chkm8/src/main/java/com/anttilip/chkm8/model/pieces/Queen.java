/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anttilip.chkm8.model.pieces;

import com.anttilip.chkm8.model.Player;
import com.anttilip.chkm8.model.Position;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author antti
 */
public class Queen extends Piece {

    public Queen(Position position, Player player) {
        super(position, player);
    }

    @Override
    public List<Position> getAllowedMoves(HashMap<Position, Piece> occupiedPositions) {
        List<Position> allowedMoves = new ArrayList();

        // Use Positions as a directions to simplify move checking
        Position[] directions = {
            new Position(1, 0),   // Right
            new Position(0, 1),   // Up
            new Position(-1, 0),  // Left
            new Position(0, -1),  // Down
            new Position(1, 1),   // Up-Right
            new Position(-1, 1),  // Up-Left
            new Position(-1, -1), // Down-Left
            new Position(1, -1)   // Down-Right
        };

        for (Position direction : directions) {
            Position target = Position.add(this.position, direction);
            while (target.onBoard()) {
                if (occupiedPositions.containsKey(target))  {
                    if (occupiedPositions.get(target).player != this.player) {
                        // If target is occupied by opponents piece, move is allowed
                        allowedMoves.add(target);
                    }
                    // If target contains a piece, checking must be stopped 
                    // since "jumping" over pieces is not allowed on Queen
                    break;
                } else {
                    // If target is not occupied, mvoe is allowed
                    allowedMoves.add(target);
                }
                // Lets move one more to the target direction
                target = Position.add(target, direction);
            }
        }
        return allowedMoves;
    }
}
