package com.anttilip.chkm8.model.pieces;

import com.anttilip.chkm8.model.Board;
import com.anttilip.chkm8.model.Player;
import com.anttilip.chkm8.model.Position;

import java.util.List;

/**
 * Represents a pawn, extends the abstract Piece class.
 */
public class Pawn extends Piece {
    private boolean firstMove;
    private final Position[] moveDirections;
    private final int moveDirection;

    /**
     * Constructor for pawn.
     * @param position Position of the piece.
     * @param player Player who controls the piece.
     */
    public Pawn(Position position, Player player) {
        super(position, player, false);
        this.firstMove = true;
        this.moveDirection = (this.player == Player.WHITE) ? 1 : -1;
        this.moveDirections = new Position[]{new Position(0, moveDirection)};
    }

    /**
     * Returns pawns allowed move direction.
     *
     * Pawns move direction depends on the player who controls the pawn.
     * @return Position array containing the move direciton.
     */
    @Override
    public Position[] getMoveDirections() {
        return this.moveDirections;
    }

    /**
     * Moves pawn to the given position.
     *
     * Pawn is special piece being able to do double moves, attacking moves and en passant moves.
     * @param newPosition Position that piece is moved to.
     * @param board Board that piece belongs to.
     */
    @Override
    public void move(Position newPosition, Board board) {
        this.firstMove = false;

        // If move is double move, make en passant pawn
        if (Math.abs(newPosition.getY() - this.position.getY()) == 2) {
            int newPawnYDiff = (this.position.getY() > newPosition.getY()) ? -1 : 1;
            Position enPassantPos = new Position(this.getPosition().getX(), this.getPosition().getY() + newPawnYDiff);
            board.setEnPassantPosition(enPassantPos);
        } else if (newPosition.equals(board.getEnPassantPosition())) {
            // Attacked en passant
            Piece originalPawn = board.getPiece(newPosition.getX(), newPosition.getY() + moveDirection * -1);
            originalPawn.kill(board);
            board.setEnPassantPosition(null);
        } else if (board.isOccupied(newPosition)) {
            // Regular attack move
            board.getPiece(newPosition).kill(board);
        }

        this.position = newPosition;
        if (isInTheEnd()) {
            // If pawn is in the end, it is promoted to Queen
            // If promotion move was attacking move, kill the attacked piece
            if (board.getPiece(newPosition) != null) {
                board.getPiece(newPosition).kill(board);
            }
            board.getPieces().add(new Queen(this.position, this.player));
            this.kill(board);
        }
    }

    /**
     * Checks is piece is in the highest rank.
     * @return Boolean value of pawn being in the highest rank.
     */
    private boolean isInTheEnd() {
        return (this.player == Player.WHITE && this.position.getY() == Board.BOARD_SIZE - 1)
                || (this.player == Player.BLACK && this.position.getY() == 0);
    }

    public boolean isFirstMove() {
        return this.firstMove;
    }


    /**
     * Adds pawns special double, attacking and en passant moves to the given list.
     *
     * @param board Board that piece belongs to.
     * @param possibleMoves List of possible positions that piece can move to.
     */
    @Override
    public void getSpecialMoves(Board board, List<Position> possibleMoves) {
        // Double move
        Position doubleMove = this.position.copy().add(0, moveDirection * 2);
        if (this.firstMove && possibleMoves.contains(this.position.copy().add(0, moveDirection))) {
            if (!board.isOccupied(doubleMove)) {
                possibleMoves.add(doubleMove);
            }
        }

        // Attacking moves
        Position[] attackingMoves = {
            this.position.copy().add(1, moveDirection),
            this.position.copy().add(-1, moveDirection)
        };
        for (Position attackMove : attackingMoves) {
            // Regular attack move
            if (board.isOccupied(attackMove) && board.getPiece(attackMove).player != this.player) {
                possibleMoves.add(attackMove);
            }
            // En passant
            if (board.getEnPassantPosition() != null && board.getEnPassantPosition().equals(attackMove)) {
                possibleMoves.add(attackMove);
            }
        }
    }

    @Override
    public Piece copy() {
        Pawn copy = new Pawn(this.position.copy(), this.player);
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
