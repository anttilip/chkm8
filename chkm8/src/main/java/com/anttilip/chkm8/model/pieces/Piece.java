package com.anttilip.chkm8.model.pieces;

import com.anttilip.chkm8.model.Board;
import com.anttilip.chkm8.model.Player;
import com.anttilip.chkm8.model.Position;

import java.util.ArrayList;
import java.util.List;

public abstract class Piece {

    protected Position position;
    protected final Player player;
    private final boolean canMoveMoreThanOnce;

    public Piece(Position position, Player player, boolean canMoveMoreThanOne) {
        this.player = player;
        this.position = position;
        this.canMoveMoreThanOnce = canMoveMoreThanOne;
    }

    public void move(Position newPosition, Board board) {
        if (board.getPiece(newPosition) != null) {
            board.getPiece(newPosition).kill(board);
        }
        this.position = newPosition;
    }

    public List<Position> getPossibleMoves(Board board) {
        List<Position> getPossibleMoves = new ArrayList<>();
        for (Position direction : this.getMoveDirections()) {
            Position target = Position.add(this.position, direction);
            while (target.onBoard()) {
                if (!board.isOccupied(target)) {
                    getPossibleMoves.add(target);
                    if (this.canMoveMoreThanOnce) {
                        // If piece can move more than once, move target position to the same direction
                        target = Position.add(target, direction);
                        continue;
                    }
                } else if (board.getPiece(target).player != this.player && !(this instanceof Pawn)) {
                    // Pawn is only piece that can't attack where it moves
                    getPossibleMoves.add(target);
                }
                break;
            }
        }

        getSpecialMoves(board, getPossibleMoves);
        return getPossibleMoves;
    }

    public abstract Position[] getMoveDirections();

    protected void getSpecialMoves(Board board, List<Position> possibleMoves) {
    }

    public void kill(Board board) {
        board.getPieces().remove(this);
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Player getPlayer() {
        return this.player;
    }

    @Override
    public String toString() {
        return this.player + " " + this.getClass().getSimpleName();
    }

    @Override
    public int hashCode() {
        int hash = 13;
        hash = 5 * hash + this.position.hashCode();
        hash = 19 * hash + ((this.player == Player.WHITE) ? 11 : 21);
        return 31 * hash + this.getClass().hashCode();
    }

    public abstract boolean equals(Object o);

    public abstract Piece copy();
}
