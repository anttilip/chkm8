/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anttilip.chkm8.model.pieces;

import com.anttilip.chkm8.model.Board;
import com.anttilip.chkm8.model.Player;
import com.anttilip.chkm8.model.Position;
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

    public void setIsChecked(boolean checked) {
        this.isChecked = checked;
    }

    @Override
    public List<Position> getAllowedMoves(Board board, boolean selfCheckAllowed) {
        HashMap<Position, Piece> occupiedPositions = board.getPiecePositionMap();
        List<Position> allowedMoves = new ArrayList();

        Position[] directions = {
            new Position(1, 0), // Right
            new Position(0, 1), // Up
            new Position(-1, 0), // Left
            new Position(0, -1), // Down
            new Position(1, 1), // Up-Right
            new Position(-1, 1), // Up-Left
            new Position(-1, -1), // Down-Left
            new Position(1, -1) // Down-Right
        };

        for (Position direction : directions) {
            Position target = Position.add(this.position, direction);
            if (!target.onBoard()) {
                // If position is out of board, move can't be allowed
                continue;
            }
            if ((!occupiedPositions.containsKey(target) 
                    || (occupiedPositions.containsKey(target) 
                        && occupiedPositions.get(target).player != this.player)) 
                    && (selfCheckAllowed || !super.moveLeadsToSelfCheck(target, board))) {
                // Move is allowed if position doesn't have own piece
                // Move is not allowed if it causes own king to be checked
                allowedMoves.add(target);
            }
        }

        return allowedMoves;
    }

    @Override
    public Piece copy() {
        King copy = new King(this.position, this.player);
        copy.isChecked = this.isChecked();
        return copy;
    }

}
