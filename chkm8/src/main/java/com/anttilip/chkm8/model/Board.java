
package com.anttilip.chkm8.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Board {
    public static final int BOARD_SIZE = 8;
    private final List<Piece> pieces;

    public Board() {
        this.pieces = createBoard();
    }
    
    
    public void update() {
        HashMap<Position, Player> occupiedPositions = getOccupiedPositions();
        
        for (Piece piece : pieces) {
            piece.updatePossibleMoves(occupiedPositions);
        }
    }
    
    public List<Piece> getPieces() {
        return this.pieces;
    }

   
    private HashMap<Position, Player> getOccupiedPositions() {
        // Get occupied positions on board
        HashMap<Position, Player> occupiedPositions = new HashMap();
        
        for (Piece piece : pieces) {
            occupiedPositions.put(piece.getPosition(), piece.getPlayer());
        }
        
        return occupiedPositions;
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
