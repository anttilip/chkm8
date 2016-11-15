package com.anttilip.chkm8;

import com.anttilip.chkm8.model.ChessState;
import com.anttilip.chkm8.model.pieces.Piece;
import com.anttilip.chkm8.model.Position;

public class Main {

    public static void main(String[] args) {
        ChessState chessState = new ChessState();
        Piece whiteKing = chessState.getBoard().getPiece(4, 0).copy();
        Piece whitePawn = chessState.getBoard().getPiece(0, 1);
        Piece blackKing = chessState.getBoard().getPiece(4, 7);
        Piece blackQueen = chessState.getBoard().getPiece(3, 7);
        Piece blackRook = chessState.getBoard().getPiece(0, 7).copy();
        chessState.getBoard().getPieces().clear();
//
//        chessState.getBoard().getPieces().add(whiteKing);
//        chessState.getBoard().getPieces().add(blackRook);
//        chessState.getBoard().getPieces().add(blackKing);
//        chessState.getBoard().getPieces().add(blackQueen);
//        chessState.move(whiteKing, new Position(2, 3));
//        chessState.move(blackRook, new Position(2, 4));
//        chessState.move(blackQueen, new Position(2, 2));
//        System.out.println(chessState.getGameStates());

        chessState.getBoard().getPieces().add(whiteKing);
        chessState.getBoard().getPieces().add(blackRook);
        chessState.getBoard().getPieces().add(blackKing);
        System.out.println(chessState.getBoard().hashCode());
        chessState.move(whiteKing, new Position(1, 3));
        chessState.move(whiteKing, new Position(2, 3));
        System.out.println(chessState.getBoard().hashCode());
        chessState.move(blackRook, new Position(3, 5));
        System.out.println(chessState.getBoard().hashCode());
        chessState.move(whiteKing, new Position(2, 2));
        System.out.println(chessState.getBoard().hashCode());
        chessState.move(whiteKing, new Position(2, 3));
        System.out.println(chessState.getBoard().hashCode());
        chessState.move(whiteKing, new Position(2, 2));
        System.out.println(chessState.getBoard().hashCode());
        chessState.move(whiteKing, new Position(2, 3));
        System.out.println(chessState.getBoard().hashCode());

        System.out.println(chessState.getGameStates());
        /*
        System.out.println("Board is initialized with default positions\n");
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
         */

        /**
         * *****************************
         ******* Simple CHECK test ******
        *******************************
         */
//        Board board2 = new Board(new ArrayList());
//        King whiteKing = new King(new Position(3, 3), Player.WHITE);
//        Pawn whitePawn = new Pawn(new Position(4, 2), Player.WHITE);
//        Rook blackRook = new Rook(new Position(5, 3), Player.BLACK);
//        King blackKing = new King(new Position(6, 6), Player.BLACK);
//        board2.getPieces().add(whiteKing);
//        board2.getPieces().add(whitePawn);
//        board2.getPieces().add(blackRook);
//        board2.getPieces().add(blackKing);
//
//        System.out.println("\n\nWhite pawn position: " + whitePawn.getPosition());
//        System.out.println("White pawn allowed moves: " + board2.getAllowedMoves(whitePawn) + ": should be [E4, F4]");
//        System.out.println("White king is checked: " + board2.isCheck(Player.WHITE) + ": should be true");
//        
//        board2 = new Board(new ArrayList());
//        whiteKing = new King(new Position(3, 3), Player.WHITE);
//        whitePawn = new Pawn(new Position(4, 1), Player.WHITE);
//        blackRook = new Rook(new Position(5, 3), Player.BLACK);
//        blackKing = new King(new Position(6, 6), Player.BLACK);
//        board2.getPieces().add(whiteKing);
//        board2.getPieces().add(whitePawn);
//        board2.getPieces().add(blackRook);
//        board2.getPieces().add(blackKing);
//
//        System.out.println("\n\nWhite pawn position: " + whitePawn.getPosition());
//        System.out.println("White pawn allowed moves: " + board2.getAllowedMoves(whitePawn) + ": should be E4");
//        System.out.println("White king is checked: " + board2.isCheck(Player.WHITE) + ": should be true");
        /**
         * ***********
         * END
         */
//        ChessState s = new ChessState();
//        Piece whiteQueen = s.getBoard().getPiece(3, 0);
//        Piece whitePawnQ = s.getBoard().getPiece(2, 1);
//        System.out.println(s.getBoard().getAllowedMoves(whitePawnQ));
//        s.move(whitePawnQ, new Position(2, 3));
//        System.out.println(s.getBoard().getAllowedMoves(whiteQueen));
//        s.move(whiteQueen, new Position(0, 3));
//        System.out.println(s.getBoard().getAllowedMoves(whiteQueen));
//        //s.move(whiteQueen, new Position(6, 5));
//        //System.out.println(s.getBoard().getAllowedMoves(whiteQueen));
//        s.move(s.getBoard().getPiece(3, 6), new Position(3, 4));
//        System.out.println(s.getBoard().isCheck(Player.BLACK));
        //Piece whiteKing2 = new King(new Position(3, 2), Player.WHITE);
        //Piece blackRook2 = new Rook(new Position(3, 3), Player.BLACK);
//        Piece whiteKing2 = s.getBoard().getPiece(4, 0);
//        Piece blackRook2 = s.getBoard().getPiece(0, 7);
//        s.move(whiteKing2, whiteKing2.getPosition());
//        s.move(blackRook2, blackRook2.getPosition());
//        System.out.println(s.getBoard().isCheck(Player.WHITE));
//        for (Piece p : s.getBoard().getPieces()) {
//            System.out.println(p + " " + p.getPosition());
//        }
//        System.out.println(s.getGameStates());
        /*
        Piece whiteKing = s.getBoard().getPiece(4, 0);
        Piece blackRook = s.getBoard().getPiece(0, 7);
        s.move(whiteKing, new Position(2, 3));
        s.move(blackRook, new Position(2, 4));
        System.out.println(s.getBoard().isCheck(Player.WHITE));
         */
    }
}

/*
TODO:
  - Kysyttävää pajassa:
    - Jokaselle piecelle omat isFirstMove()
        - voiks jotenkin kivasti kattoo et onks sama instanceof vaikka Pawn
    - Swing vs libgdx
        - Projektin rakenne muuttuu
    - MVC?
        - Haluun jättää kivan AI plugin
            - esim AIController
            - UserInput ja AI vois käyttää samaa interfacee tms

  - Tests
    - Self check move tests

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
