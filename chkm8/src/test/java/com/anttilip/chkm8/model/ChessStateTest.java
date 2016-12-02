/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anttilip.chkm8.model;

import com.anttilip.chkm8.model.pieces.Pawn;
import com.anttilip.chkm8.model.pieces.Piece;
import com.anttilip.chkm8.model.pieces.Queen;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


import java.util.List;

import static org.junit.Assert.*;

/**
 *
 * @author antti
 */
public class ChessStateTest {

    private ChessState chessState;
    private Piece whitePawn;

    public ChessStateTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        chessState = new ChessState();
        whitePawn = chessState.getBoard().getPiece(0, 1);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void notNull() {
        assertFalse(chessState == null);
    }

    @Test
    public void getBoardNotNull() {
        assertFalse(chessState.getBoard() == null);
    }

    @Test
    public void moveCounterInitiallyZero() {
        assertTrue(chessState.getMoveCount() == 0);
    }

    @Test
    public void moveCounterIncrementsOnce() {
        Board board = chessState.getBoard();
        chessState.move(board.getPiece(3, 0), new Position(5, 0));
        assertTrue(chessState.getMoveCount() == 1);
    }

    @Test
    public void moveCounterIncrementsTwice() {
        Piece whiteQueen = chessState.getBoard().getPiece(new Position(3, 0));
        chessState.move(whiteQueen, new Position(3, 3));
        chessState.move(whiteQueen, new Position(3, 6));
        assertTrue(chessState.getMoveCount() == 2);
    }

    @Test
    public void firstTurnIsWhite() {
        assertTrue(chessState.getCurrentPlayer() == Player.WHITE);
    }

    @Test
    public void secondTurnBlack() {
        Piece whiteQueen = chessState.getBoard().getPiece(new Position(3, 0));
        chessState.move(whiteQueen, new Position(3, 3));
        assertTrue(chessState.getCurrentPlayer() == Player.BLACK);
    }

    @Test
    public void thirdTurnIsWhiteAgain() {
        Piece whiteQueen = chessState.getBoard().getPiece(new Position(3, 0));
        chessState.move(whiteQueen, new Position(3, 3));
        chessState.move(whiteQueen, new Position(3, 6));
        assertTrue(chessState.getCurrentPlayer() == Player.WHITE);
    }

    @Test
    public void moveHistoryOutputIsValid() {
        Piece whiteQueen = chessState.getBoard().getPiece(new Position(3, 0));
        chessState.move(whiteQueen, new Position(3, 3));
        assertTrue(chessState.getMoveHistory().get(0).toString().equals("WHITE Queen from D1 to D4"));
    }

    @Test
    public void undoWithNoMoves() {
        chessState.undoLastMove();
        assertTrue(chessState.getMoveCount() == 0);
    }

    @Test
    public void undoOneMoveCount() {
        Piece whitePawn = new Pawn(new Position(3, 3), Player.WHITE);
        chessState.getBoard().getPieces().add(whitePawn);
        chessState.move(whitePawn, new Position(3, 4));
        chessState.undoLastMove();
        assertTrue(chessState.getMoveCount() == 0);
    }

    @Test
    public void undoOneMoveRemoveNewPosition() {
        Piece whitePawn = new Pawn(new Position(3, 3), Player.WHITE);
        chessState.getBoard().getPieces().add(whitePawn);
        chessState.move(whitePawn, new Position(3, 4));
        chessState.undoLastMove();
        assertTrue(chessState.getBoard().getPiece(3, 4) == null);
    }

    @Test
    public void undoOneMoveReturnOldPosition() {
        Piece whitePawn = new Pawn(new Position(3, 3), Player.WHITE);
        Piece whitePawnCopy = whitePawn.copy();
        chessState.getBoard().getPieces().add(whitePawn);
        chessState.move(whitePawn, new Position(3, 4));
        chessState.undoLastMove();
        Piece piece = (chessState.getBoard().getPiece(3, 3));
        assertTrue(piece.getPosition().equals((whitePawnCopy.getPosition()))
                && piece.getPlayer() == whitePawnCopy.getPlayer()
                && piece.getClass() == whitePawnCopy.getClass());
    }

    @Test
    public void undoOneOnceWhenTwoMovesCount() {
        Piece whitePawn = new Pawn(new Position(3, 3), Player.WHITE);
        chessState.getBoard().getPieces().add(whitePawn);
        chessState.move(whitePawn, new Position(3, 4));
        chessState.move(whitePawn, new Position(3, 5));
        chessState.undoLastMove();
        assertTrue(chessState.getMoveCount() == 1);
    }

    @Test
    public void undoOneTwiceWhenTwoMovesCount() {
        Piece whitePawn = new Pawn(new Position(3, 3), Player.WHITE);
        chessState.getBoard().getPieces().add(whitePawn);
        chessState.move(whitePawn, new Position(3, 4));
        chessState.move(whitePawn, new Position(3, 5));
        chessState.undoLastMove();
        chessState.undoLastMove();
        assertTrue(chessState.getMoveCount() == 0);
    }

    @Test
    public void undoTwoMovesReturnOldPosition() {
        Piece whitePawn = new Pawn(new Position(3, 3), Player.WHITE);
        Piece whitePawnCopy = whitePawn.copy();
        chessState.getBoard().getPieces().add(whitePawn);
        chessState.move(whitePawn, new Position(3, 4));
        chessState.move(whitePawn, new Position(3, 5));
        chessState.undoLastMove();
        chessState.undoLastMove();
        Piece piece = (chessState.getBoard().getPiece(3, 3));
        assertTrue(piece.getPosition().equals(whitePawnCopy.getPosition())
                && piece.getPlayer() == whitePawnCopy.getPlayer()
                && piece.getClass() == whitePawnCopy.getClass());
    }

    @Test
    public void undoChangesTurns() {
        chessState.getBoard().getPieces().add(whitePawn);
        chessState.move(whitePawn, new Position(3, 4));
        chessState.undoLastMove();
        assertTrue(chessState.getCurrentPlayer() == Player.WHITE);
    }

    @Test
    public void undoChangesTurnsWithTwoMoves() {
        chessState.getBoard().getPieces().add(whitePawn);
        chessState.move(whitePawn, new Position(3, 4));
        chessState.move(whitePawn, new Position(3, 5));
        chessState.undoLastMove();
        assertTrue(chessState.getCurrentPlayer() == Player.BLACK);
    }

    @Test
    public void undoChangesTurnsWithTwoMoves2() {
        chessState.getBoard().getPieces().add(whitePawn);
        chessState.move(whitePawn, new Position(3, 4));
        chessState.move(whitePawn, new Position(3, 5));
        chessState.undoLastMove();
        chessState.undoLastMove();
        assertTrue(chessState.getCurrentPlayer() == Player.WHITE);
    }

    @Test
    public void undoDoesntChangeTurnsWhenNoMoves() {
        chessState.undoLastMove();
        assertTrue(chessState.getCurrentPlayer() == Player.WHITE);
    }

    @Test
    public void getPieceAtXYReturnsCorrectly() {
        Piece whiteQueen = new Queen(new Position(4, 4), Player.WHITE);
        chessState.getBoard().getPieces().add(whiteQueen);
        assertTrue(chessState.getPieceAt(4, 4).equals(whiteQueen));
    }
    
    @Test
    public void getPieceAtPositionReturnsCorrectly() {
        Piece whiteQueen = new Queen(new Position(4, 4), Player.WHITE);
        chessState.getBoard().getPieces().add(whiteQueen);
        assertTrue(chessState.getPieceAt(whiteQueen.getPosition()).equals(whiteQueen));
    }

    @Test
    public void getPlayersPiecesReturnsCorrectly() {
        Piece whiteQueen = new Queen(new Position(4, 4), Player.WHITE);
        chessState.getBoard().getPieces().clear();
        chessState.getBoard().getPieces().add(whiteQueen);
        List<Piece> whitesPieces = chessState.getPlayersPieces(Player.WHITE);
        assertTrue(whitesPieces.size() == 1 && whitesPieces.get(0).equals(whiteQueen));
    }

    @Test
    public void getPiecesAllowedMovesReturnsSameAsBoard() {
        List<Position> moves = chessState.getGetPiecesAllowedMoves(chessState.getPieceAt(3, 1));
        assertTrue(moves.size() == 2 && moves.contains(new Position(3, 2)) && moves.contains(new Position(3, 3)));
    }
}
