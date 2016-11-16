package com.anttilip.chkm8.model.pieces;

import com.anttilip.chkm8.model.Board;
import com.anttilip.chkm8.model.Player;
import com.anttilip.chkm8.model.Position;

import java.util.ArrayList;
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
    Position[] getMoveDirections() {
        return this.moveDirections;
    }

    @Override
    public void move(Position newPosition, Board board) {
        this.firstMove = false;
        Position originalPosition = this.getPosition();
        this.position = newPosition;
        if (isInTheEnd()) {
            // If pawn is in the end, it is promoted to Queen
            board.getPieces().add(new Queen(this.position, this.player));
            board.getPieces().remove(this);
        }
        // If move is double move, make en passant pawn
        if (Math.abs(newPosition.getY() - originalPosition.getY()) == 2) {
            int newPawnYDiff = (this.position.getY() > originalPosition.getY()) ? -1 : 1;
            Position enPassantPos = new Position(this.getPosition().getX(), this.getPosition().getY() + newPawnYDiff);
            board.setEnPassantPosition(enPassantPos);
        } else if (newPosition.equals(board.getEnPassantPosition())) {
            Piece originalPawn = board.getPiece(newPosition.getX(), newPosition.getY() + moveDirection * -1);
            originalPawn.kill(board);
        }
    }
//
//    @Override
//    public void kill(Board board, Piece killer) {
//        // If real pawn is killed, en passant pawn is also killed
//        // If pawn kills en passant pawn, real pawn is also killed
//        if (killer.getPosition() == this.position
//                || (board.getTemporaryPieces().containsValue(this) && killer instanceof Pawn)) {
//            board.getPieces().remove(this);
//            board.removeTemporaryPiece(this);
//        }
//    }

    public boolean isInTheEnd() {
        return (this.player == Player.WHITE && this.position.getY() == Board.BOARD_SIZE - 1)
                || (this.player == Player.BLACK && this.position.getY() == 0);
    }

    public boolean isFirstMove() {
        return this.firstMove;
    }

    public void doFirstMove() {
        // TODO: Remove this and fix tests using this
        this.firstMove = false;
    }

    public void getSpecialMoves(Board board, boolean selfCheckAllowed, List<Position> allowedMoves) {
        // Gather every possible special move to a list
        List<Position> possibleMoves = new ArrayList<>();

        // Double move
        Position doubleMove = Position.add(this.position, new Position(0, moveDirection * 2));
        if (isFirstMove() && allowedMoves.contains(Position.add(this.position, this.moveDirections[0]))) {
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

        // Possible moves are allowed if own king doesn't get threatened
        for (Position move : possibleMoves) {
            if (selfCheckAllowed || !super.moveLeadsToSelfCheck(move, board)) {
                allowedMoves.add(move);
            }
        }
    }

//    @Override
//    public List<Position> getAllowedMoves(Board board, boolean selfCheckAllowed) {
//        HashMap<Position, Piece> occupiedPositions = board.getPiecePositionMap();
//        List<Position> allowedMoves = new ArrayList<>();
//
//        // Pawns have only one direction and it depends on player color
//        int direction = (this.player == Player.WHITE) ? 1 : -1;
//
//        // Regular and double move
//        Position target = Position.add(this.position, new Position(0, direction));
//        if (!occupiedPositions.containsKey(target)) {
//            // Target is not occupied by any piece
//            // Move is not allowed if it causes own king to be checked
//            if (selfCheckAllowed || !super.moveLeadsToSelfCheck(target, board))  {
//                allowedMoves.add(target);
//            }
//
//            if (this.firstMove) {
//                // If pawn has not yet moved, it can move two squares
//                Position doubleMove = Position.add(this.position, new Position(0, 2 * direction));
//                if (!occupiedPositions.containsKey(doubleMove)
//                        && (selfCheckAllowed
//                            || !super.moveLeadsToSelfCheck(doubleMove, board))) {
//                    allowedMoves.add(doubleMove);
//                }
//            }
//        }
//
//        // Attacking moves
//        Position[] attackingMoves = {
//            Position.add(this.position, new Position(1, direction)),
//            Position.add(this.position, new Position(-1, direction))
//        };
//
//        // Add temporary pieces to allow en passant attacking moves
//        occupiedPositions.putAll(board.getTemporaryPieces());
//
//        for (Position attackMove : attackingMoves) {
//            if (attackMove.onBoard()) {
//                // Pawn can attack only if opponents piece occupies target position
//                if (occupiedPositions.containsKey(attackMove)
//                        && occupiedPositions.get(attackMove).player != this.player
//                        && (selfCheckAllowed || !super.moveLeadsToSelfCheck(target, board))) {
//                    // Move is not allowed if it causes own king to be checked
//                    allowedMoves.add(attackMove);
//                }
//            }
//        }
//
//        return allowedMoves;
//    }


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
