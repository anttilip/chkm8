package com.anttilip.chkm8;

import com.anttilip.chkm8.model.ChessState;
import com.anttilip.chkm8.model.Move;
import com.anttilip.chkm8.model.Piece;
import com.anttilip.chkm8.model.Position;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ChessState chessState = new ChessState();      
       
        System.out.println("Board is initilized with default positions\n");
        System.out.println("Move Pawn B2 to B3\n");
        chessState.move(chessState.getBoard().getPiece(1, 1), new Position(1, 2));

        System.out.println("Selected piece:");
        Piece piece = chessState.getBoard().getPiece(2, 0);
        System.out.println(piece);
        System.out.println("\nAllowed moves:");
        System.out.println(chessState.getBoard().getAllowedMoves(piece));
        
        System.out.println("Move bishop to F6\n");
        chessState.move(piece, new Position(5, 5));
        
        System.out.println("Bishops new allowed moves:");
        System.out.println(chessState.getBoard().getAllowedMoves(piece));
        
        System.out.println("\nMove history:");
        List<Move> history = chessState.getMoveHistory();
        for (Move m : history) {
            System.out.println(m);
        }
    }
}
