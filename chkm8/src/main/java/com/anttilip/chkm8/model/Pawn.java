package com.anttilip.chkm8.model;

import java.util.HashMap;

public class Pawn extends Piece {

    public Pawn(Position position, Player player) {
        super(position, player);
        updatePossibleMoves(new HashMap());
    }


    @Override
    void updatePossibleMoves(HashMap<Position, Player> occupiedPositions) {
        this.possibleMoves.clear(); // Clear last turns possible moves
        
        if (this.position.getX() == Board.BOARD_SIZE - 1  && this.position.getX() == 0) {
            // Pawn is in end-zone and should turn into Queen
        }
        
        if (this.position.getY() == 1) {
            // Pawn has not yet moved
            Position target = new Position(this.position.getX(), this.position.getY() + 2);
            if (occupiedPositions.containsKey(target)) {
                if (occupiedPositions.get(target) != this.player) {
                    // If target position is occupied by opponents piece, move is allowed 
                    this.possibleMoves.add(new Position(this.position.getX(), this.position.getY() + 2));
                }
                
            } else {
                // if target position is not occupied, move is allowed
                this.possibleMoves.add(new Position(this.position.getX(), this.position.getY() + 2));
            }
            this.possibleMoves.add(new Position(this.position.getX(), this.position.getY() + 2));
        }
        
        // Regular pawn move
        Position target = new Position(this.position.getX(), this.position.getY() + 1);
        if (occupiedPositions.containsKey(target)) {
            if (occupiedPositions.get(target) != this.player) {
                // If target position is occupied by opponents piece, move is allowed 
                this.possibleMoves.add(new Position(this.position.getX(), this.position.getY() + 1));
            }

        } else {
            // if target position is not occupied, move is allowed
            this.possibleMoves.add(new Position(this.position.getX(), this.position.getY() + 1));
        }
        
        this.possibleMoves.add(new Position(this.position.getX(), this.position.getY() + 1));
    }
}
