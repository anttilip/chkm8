package com.anttilip.chkm8.model.pieces;

import com.anttilip.chkm8.model.Board;
import com.anttilip.chkm8.model.Player;
import com.anttilip.chkm8.model.Position;

import java.util.ArrayList;
import java.util.List;

public abstract class Piece {

    protected Position position;
    protected final Player player;
    private final boolean canMoveContinuously;

    public Piece(Position position, Player player, boolean canMoveMoreThanOne) {
        this.player = player;
        this.position = position;
        this.canMoveContinuously = canMoveMoreThanOne;
    }

    public void move(Position newPosition, Board board) {
        this.position = newPosition;
    }

    public List<Position> getAllowedMoves(Board board, boolean selfCheckAllowed) {
        List<Position> allowedMoves = new ArrayList<>();

        for (Position direction : this.getMoveDirections()) {
            Position target = Position.add(this.position, direction);
            while (target.onBoard()) {
                if (!board.isOccupied(target)) {
                    if (selfCheckAllowed  || !moveLeadsToSelfCheck(target, board)) {
                        allowedMoves.add(target);
                        if (this.canMoveContinuously) {
                            // If piece can move more than once, move target position to the same direction
                            target = Position.add(target, direction);
                            continue;
                        }
                    }
                } else if (board.getPiece(target).player != this.player && !(this instanceof Pawn)) {
                    // Pawn is only piece that can't attack where it moves
                    if (selfCheckAllowed ||  !moveLeadsToSelfCheck(target, board)) {
                        allowedMoves.add(target);
                    }
                }
                // Target position was not in board or was occupied by
                break;
            }
        }
        getSpecialMoves(board, selfCheckAllowed, allowedMoves);
        return allowedMoves;
    }

    abstract Position[] getMoveDirections();

    public void getSpecialMoves(Board board, boolean selfCheckAllowed, List<Position> allowedMoves) {
        return;
    }

    public void kill(Board board) {
        board.getPieces().remove(this);
    }

    public boolean moveLeadsToSelfCheck(Position target, Board board) {
        Board boardCopy = board.copy();
        boardCopy.movePiece(boardCopy.getPiece(this.position), target);
        return boardCopy.isCheck(this.player);
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
