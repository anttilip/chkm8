package com.anttilip.chkm8;

import com.anttilip.chkm8.model.Board;
import com.anttilip.chkm8.model.ChessState;
import com.anttilip.chkm8.model.Player;
import com.anttilip.chkm8.model.Position;
import com.anttilip.chkm8.model.pieces.King;
import com.anttilip.chkm8.model.pieces.Pawn;
import com.anttilip.chkm8.model.pieces.Rook;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        ChessState chessState = new ChessState();
        System.out.println("Board is initialized with default positions\n");
        Board board2 = new Board(new ArrayList<>());
        King whiteKing = new King(new Position(3, 3), Player.WHITE);
        Pawn whitePawn = new Pawn(new Position(4, 2), Player.WHITE);
        Rook blackRook = new Rook(new Position(5, 3), Player.BLACK);
        King blackKing = new King(new Position(6, 6), Player.BLACK);
        board2.getPieces().add(whiteKing);
        board2.getPieces().add(whitePawn);
        board2.getPieces().add(blackRook);
        board2.getPieces().add(blackKing);

        System.out.println("\n\nWhite pawn position: " + blackRook.getPosition());
        System.out.println("White pawn allowed moves: " + board2.getAllowedMoves(blackRook));
        System.out.println("White king is checked: " + board2.isCheck(Player.WHITE));
    }
}
