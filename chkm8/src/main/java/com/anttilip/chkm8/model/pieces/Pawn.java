package com.anttilip.chkm8.model.pieces;

import com.anttilip.chkm8.model.Board;
import com.anttilip.chkm8.model.MoveLimitation;
import com.anttilip.chkm8.model.Player;
import com.anttilip.chkm8.model.Position;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class Pawn extends Piece {
    private boolean firstMove;
    private final Position[] moveDirections;
    private final int moveDirection;

    public Pawn(Position position, Player player) {
        super(position, player, false);
        this.firstMove = true;
        this.moveDirection = (this.player == Player.WHITE) ? 1 : -1;
        this.moveDirections = new Position[]{new Position(0, moveDirection)};
    }

    @Override
    public Position[] getMoveDirections() {
        return this.moveDirections;
    }

    @Override
    public void move(Position newPosition, Board board) {
        this.firstMove = false;
        Position originalPosition = this.getPosition();
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
        // If move is double move, make en passant pawn
        if (Math.abs(newPosition.getY() - originalPosition.getY()) == 2) {
            int newPawnYDiff = (this.position.getY() > originalPosition.getY()) ? -1 : 1;
            Position enPassantPos = new Position(this.getPosition().getX(), this.getPosition().getY() + newPawnYDiff);
            board.setEnPassantPosition(enPassantPos);
        } else if (newPosition.equals(board.getEnPassantPosition())) {
            // Attacked en passant
            Piece originalPawn = board.getPiece(newPosition.getX(), newPosition.getY() + moveDirection * -1);
            originalPawn.kill(board);
        }
    }

    private boolean isInTheEnd() {
        return (this.player == Player.WHITE && this.position.getY() == Board.BOARD_SIZE - 1)
                || (this.player == Player.BLACK && this.position.getY() == 0);
    }

    public boolean isFirstMove() {
        return this.firstMove;
    }


    @Override
    public void getSpecialMoves(Board board, List<Position> possibleMoves) {
        // Double move
        Position doubleMove = Position.add(this.position, new Position(0, moveDirection * 2));
        if (this.firstMove && possibleMoves.contains(Position.add(this.position, this.moveDirections[0]))) {
            if (!board.isOccupied(doubleMove)) {
                possibleMoves.add(doubleMove);
            }
        }

        // Attacking moves
        Position[] attackingMoves = {
            Position.add(this.position, new Position(1, moveDirection)),
            Position.add(this.position, new Position(-1, moveDirection))
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
