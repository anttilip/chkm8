package com.anttilip.chkm8;

import com.anttilip.chkm8.model.ChessState;
import com.anttilip.chkm8.model.Player;
import com.anttilip.chkm8.model.Position;

public class Main {

    public static void main(String[] args) {
        // Create ChessState that creates board and models chess
        ChessState chessState = new ChessState();

        // Print players available pieces
        System.out.println("Whites pieces: " + chessState.getPlayersPieces(Player.WHITE));

        // Print piece at B1
        System.out.println("Piece at B1: " + chessState.getPieceAt(1, 0));

        // Print pieces allowed moves
        System.out.println("Allowed moves: " + chessState.getGetPiecesAllowedMoves(1, 0));

        // Move piece to C3
        System.out.println("Knight to C3");
        chessState.move(chessState.getPieceAt(1, 0), new Position(2, 2));

        // Print piece at C3
        System.out.println("Piece at C3: " + chessState.getPieceAt(2, 2));

        // Print pieces allowed moves
        System.out.println("Allowed moves: " + chessState.getGetPiecesAllowedMoves(2, 2));

        // Undo last move
        System.out.println("Undo last move");
        chessState.undoLastMove();
        System.out.println("Piece at B1: " + chessState.getPieceAt(1, 0));
    }
}
