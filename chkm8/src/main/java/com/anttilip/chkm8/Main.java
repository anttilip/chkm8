
package com.anttilip.chkm8;

import com.anttilip.chkm8.model.Board;
import com.anttilip.chkm8.model.ChessState;
import com.anttilip.chkm8.model.Piece;

public class Main {
    public static void main(String[] args) {
        ChessState chessState = new ChessState();
        
        Board board = chessState.getBoard();
        // Print pieces
        for (Piece p : board.getPieces()) {
            System.out.println(p.toString());
        }
    }
}
