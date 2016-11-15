package com.anttilip.chkm8.model.pieces;

import com.anttilip.chkm8.model.Board;
import com.anttilip.chkm8.model.Player;
import com.anttilip.chkm8.model.Position;
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

    public boolean isFirstMove() {
        return this.firstMove;
    }

    public void doFirstMove() {
        this.firstMove = false;
    }

    @Override
    public List<Position> getAllowedMoves(Board board, boolean selfCheckAllowed) {
        HashMap<Position, Piece> occupiedPositions = board.getPiecePositionMap();
        List<Position> allowedMoves = new ArrayList<>();

        // If pawn is in the end line, it can't move and will be promoted to queen
        if (this.isInTheEnd()) {
            return allowedMoves;
        }

        // Pawns have only one direction and it depends on player color
        int direction = (this.player == Player.WHITE) ? 1 : -1;

        // Regular and double move
        Position target = Position.add(this.position, new Position(0, direction));
        if (!occupiedPositions.containsKey(target)) {
            // Target is not occupied by any piece
            // Move is not allowed if it causes own king to be checked
            if (selfCheckAllowed || !super.moveLeadsToSelfCheck(target, board))  {
                allowedMoves.add(target);
            }

            if (this.firstMove) {
                // If pawn has not yet moved, it can move two squares
                Position doubleMove = Position.add(this.position, new Position(0, 2 * direction));
                if (!occupiedPositions.containsKey(doubleMove) 
                        && (selfCheckAllowed 
                            || !super.moveLeadsToSelfCheck(doubleMove, board))) {
                    allowedMoves.add(doubleMove);
                }
            }

        }

        // Attacking moves
        Position[] attackingMoves = {
            Position.add(this.position, new Position(1, direction)),
            Position.add(this.position, new Position(-1, direction))
        };

        for (Position attackMove : attackingMoves) {
            if (attackMove.onBoard()) {
                // Pawn can attack only if opponents piece occupies target position
                if (occupiedPositions.containsKey(attackMove)
                        && occupiedPositions.get(attackMove).player != this.player
                        && (selfCheckAllowed || !super.moveLeadsToSelfCheck(target, board))) {
                    // Move is not allowed if it causes own king to be checked
                    allowedMoves.add(attackMove);

                }
            }
        }

        return allowedMoves;
    }

    @Override
    public Piece copy() {
        Pawn copy = new Pawn(this.position, this.player);
        copy.firstMove = this.firstMove;
        return copy;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Pawn)) {
            return false;
        }
        Pawn other = (Pawn) o;
        return other.position.equals(this.position) && other.player == this.player && other.isFirstMove() == this.firstMove;
    }
}
