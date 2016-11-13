package com.anttilip.chkm8.model;

import com.anttilip.chkm8.model.pieces.Queen;
import com.anttilip.chkm8.model.pieces.Bishop;
import com.anttilip.chkm8.model.pieces.Rook;
import com.anttilip.chkm8.model.pieces.Piece;
import com.anttilip.chkm8.model.pieces.Pawn;
import com.anttilip.chkm8.model.pieces.King;
import com.anttilip.chkm8.model.pieces.Knight;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    public static final int BOARD_SIZE = 8;
    private final List<Piece> pieces;

    public Board() {
        this.pieces = createBoard();
    }

    public List<Position> getAllowedMoves(Piece piece) {
        return piece.getAllowedMoves(getPiecePositions());
    }

    public Piece getPiece(Position position) {
        for (Piece piece : this.pieces) {
            if (piece.getPosition().equals(position)) {
                return piece;
            }
        }
        return null;
    } 

    public Piece getPiece(int x, int y) {
        return getPiece(new Position(x, y));
    }

    public List<Piece> getPieces() {
        return this.pieces;
    }
    
    public void movePiece(Piece piece, Position target) {
        // If target position contains a piece, it will be eaten and removed
        Map<Position, Piece> positions = getPiecePositions();
        if (positions.containsKey(target)) {
            this.pieces.remove(positions.get(target));
        }
        
        // Move piece to its new position
        piece.setPosition(target);
    }

    private HashMap<Position, Piece> getPiecePositions() {
        // Get piece positions on board
        HashMap<Position, Piece> piecePositions = new HashMap();

        for (Piece piece : pieces) {
            piecePositions.put(piece.getPosition(), piece);
        }

        return piecePositions;
    }

    public static List<Piece> createBoard() {
        List<Piece> pieces = new ArrayList();

        for (Player player : Player.values()) {
            // Create pawns
            for (int x = 0; x < BOARD_SIZE; x++) {
                pieces.add(new Pawn(new Position(x, 1 + (BOARD_SIZE - 3) * player.getValue()), player));
            }

            // Create Rooks
            pieces.add(new Rook(new Position(0, 7 * player.getValue()), player));
            pieces.add(new Rook(new Position(BOARD_SIZE - 1, (BOARD_SIZE - 1) * player.getValue()), player));

            // Create Knights
            pieces.add(new Knight(new Position(1, 7 * player.getValue()), player));
            pieces.add(new Knight(new Position(BOARD_SIZE - 2, (BOARD_SIZE - 1) * player.getValue()), player));

            // Create Bishops
            pieces.add(new Bishop(new Position(2, 7 * player.getValue()), player));
            pieces.add(new Bishop(new Position(BOARD_SIZE - 3, (BOARD_SIZE - 1) * player.getValue()), player));

            // Create Queen
            pieces.add(new Queen(new Position(3, (BOARD_SIZE - 1) * player.getValue()), player));

            // Create King
            pieces.add(new King(new Position(4, (BOARD_SIZE - 1) * player.getValue()), player));
        }

        return pieces;
    }

}
