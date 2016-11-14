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
public class Knight extends Piece {

    public Knight(Position position, Player player) {
        super(position, player);
    }

    @Override
    public List<Position> getAllowedMoves(Board board, boolean selfCheckAllowed) {
        HashMap<Position, Piece> occupiedPositions = board.getPiecePositionMap();
        List<Position> allowedMoves = new ArrayList();
        List<Position> potentialMoves = new ArrayList();

        // Up Right
        potentialMoves.add(Position.add(this.position, new Position(1, 2)));

        // Up Left
        potentialMoves.add(Position.add(this.position, new Position(-1, 2)));

        // Left Up
        potentialMoves.add(Position.add(this.position, new Position(-2, 1)));

        // Left Down
        potentialMoves.add(Position.add(this.position, new Position(-2, -1)));

        // Down Right
        potentialMoves.add(Position.add(this.position, new Position(1, -2)));

        // Down Left
        potentialMoves.add(Position.add(this.position, new Position(-1, -2)));

        // Right Up
        potentialMoves.add(Position.add(this.position, new Position(2, 1)));

        // Right Down
        potentialMoves.add(Position.add(this.position, new Position(2, -1)));

        for (Position target : potentialMoves) {
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
        return new Knight(this.position, this.player);
    }

}
