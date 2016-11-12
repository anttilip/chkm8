package com.anttilip.chkm8.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Pawn extends Piece {

    private boolean firstMove;

    public Pawn(Position position, Player player) {
        super(position, player);
        this.firstMove = true;
    }

    public boolean isInTheEnd() {
        return (this.player == Player.WHITE && this.position.getY() == Board.BOARD_SIZE - 1)
                || (this.player == Player.BLACK && this.position.getY() == 0);
    }

    @Override
    public List<Position> getAllowedMoves(HashMap<Position, Piece> occupiedPositions) {
        List<Position> allowedMoves = new ArrayList();

        // If pawn is in the end line, it can't move and will be promoted to queen
        if (this.isInTheEnd()) {
            return allowedMoves;
        }

        // Pawns have only one direction and it depends on player color
        int direction = (this.player == Player.WHITE) ? 1 : -1;

        // Regular and double move
        Position target = Position.add(this.position, new Position(0, 1 * direction));
        if (!occupiedPositions.containsKey(target)) {
            // Target is not occupied by any piece
            allowedMoves.add(target);
            if (this.firstMove) {
                // If pawn has not yet moved, it can move two squares
                this.firstMove = false;
                Position doubleMove = Position.add(this.position, new Position(0, 2 * direction));
                if (!occupiedPositions.containsKey(doubleMove)) {
                    allowedMoves.add(doubleMove);
                }
            }

        }

        // Atacking moves
        Position[] attackingMoves = {
            Position.add(this.position, new Position(1, 1 * direction)),
            Position.add(this.position, new Position(-1, 1 * direction))
        };

        for (Position attackMove : attackingMoves) {
            if (attackMove.onBoard()) {
                // Pawn can attack only if opponents piece occupies target position
                if (occupiedPositions.containsKey(attackMove)
                        && occupiedPositions.get(attackMove).player != this.player) {
                    allowedMoves.add(attackMove);
                }
            }
        }

        return allowedMoves;
    }
}
