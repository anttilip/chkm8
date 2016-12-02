package com.anttilip.chkm8.model.pieces;

import com.anttilip.chkm8.model.Board;
import com.anttilip.chkm8.model.Player;
import com.anttilip.chkm8.model.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * An abstract representation of a piece.
 */
public abstract class Piece {

    protected Position position;
    protected final Player player;
    private final boolean canMoveMoreThanOnce;

    /**
     * Constructs a new piece with given parameters.
     * @param position Position of the piece.
     * @param player Player that piece belongs to.
     * @param canMoveMoreThanOne Boolean value of piece being able to move more than
     *                           one square to one direction, i.e. can slide move
     */
    public Piece(Position position, Player player, boolean canMoveMoreThanOne) {
        this.player = player;
        this.position = position;
        this.canMoveMoreThanOnce = canMoveMoreThanOne;
    }

    /**
     * Moves piece to new position.
     * @param newPosition Position that piece is moved to.
     * @param board Board that piece belongs to.
     */
    public void move(Position newPosition, Board board) {
        if (board.getPiece(newPosition) != null) {
            board.getPiece(newPosition).kill(board);
        }
        this.position = newPosition;
    }

    /**
     * Checks all positions that piece can move to, including moves that might lead to own king getting checked.
     * @param board Board that piece is in.
     * @return List of positions that piece can move to.
     */
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

    /**
     * Returns directions, or move patterns that piece can move to.
     * @return return position array of allowed move directions for the piece.
     */
    public abstract Position[] getMoveDirections();

    /**
     * If piece has some special moves, e.g. kings castling, those to the given list
     * @param board Board that piece belongs to.
     * @param possibleMoves List of possible positions that piece can move to.
     */
    protected void getSpecialMoves(Board board, List<Position> possibleMoves) {
    }

    /**
     * "Kills" the piece removes it from the board.
     * @param board Board on which the piece is.
     */
    public void kill(Board board) {
        board.getPieces().remove(this);
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        //TODO: this should be private. Change the tests.
        this.position = position;
    }

    public Player getPlayer() {
        return this.player;
    }

    /**
     * Check if piece can move more than once, i.e. can slide
     * @return Returns boolean value of piece being able to move more than once.
     */
    public boolean canMoveMoreThanOnce() {
        return canMoveMoreThanOnce;
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

    /**
     * Check if some other object is identical to this piece.
     * @param o Object which is checked against this piece.
     * @return Boolean value of other object equaling this piece.
     */
    public abstract boolean equals(Object o);

    /**
     * Creates an identical copy of the piece.
     * @return Identical copy of the piece.
     */
    public abstract Piece copy();
}
