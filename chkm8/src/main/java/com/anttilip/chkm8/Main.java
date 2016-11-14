package com.anttilip.chkm8;

import com.anttilip.chkm8.model.Board;
import com.anttilip.chkm8.model.ChessState;
import com.anttilip.chkm8.model.Move;
import com.anttilip.chkm8.model.Player;
import com.anttilip.chkm8.model.pieces.Piece;
import com.anttilip.chkm8.model.Position;
import com.anttilip.chkm8.model.pieces.King;
import com.anttilip.chkm8.model.pieces.Pawn;
import com.anttilip.chkm8.model.pieces.Rook;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ChessState chessState = new ChessState();
        Board board = chessState.getBoard();
        
        System.out.println("Board is initilized with default positions\n");
        System.out.println("Move Pawn B2 to B3\n");
        chessState.move(chessState.getBoard().getPiece(1, 1), new Position(1, 2));

        System.out.println("Selected piece:");
        Piece piece = board.getPiece(2, 0);
        System.out.println(piece);
        System.out.println("\nAllowed moves:");
        System.out.println(board.getAllowedMoves(piece));
        
        System.out.println("Move bishop to F6\n");
        chessState.move(piece, new Position(5, 5));
        
        System.out.println("Bishops new allowed moves:");
        System.out.println(board.getAllowedMoves(piece));
        
        System.out.println("\nMove history:");
        List<Move> history = chessState.getMoveHistory();
        for (Move m : history) {
            System.out.println(m);
        }
        
        Board board2 = new Board(new ArrayList());
        King whiteKing = new King(new Position(3, 3), Player.WHITE);
        Pawn whitePawn = new Pawn(new Position(4, 2), Player.WHITE);
        Rook blackRook = new Rook(new Position(5, 3), Player.BLACK);
        King blackKing = new King(new Position(6, 6), Player.BLACK);
        board2.getPieces().add(whiteKing);
        board2.getPieces().add(whitePawn);
        board2.getPieces().add(blackRook);
        board2.getPieces().add(blackKing);

        System.out.println("\n\nWhite pawn position: " + whitePawn.getPosition());
        System.out.println("White pawn allowed moves: " + board2.getAllowedMoves(whitePawn));
        System.out.println("White king is checked: " + board2.isCheck(Player.WHITE));

    }
}

/*
TODO: 
  - Tests
    - Undo tests
    - GameState tests
    - Check test
    - Self check move tests
    - Board copy tests
    - Move tests

  - Model
    - En passant
    - Pawn promotion
    - Castling

  - View 
    - Whole UI
      - libgdx vs swing

  - Controller
    - Whole controller
*/