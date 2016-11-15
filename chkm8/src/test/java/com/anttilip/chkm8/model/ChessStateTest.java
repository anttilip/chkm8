/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anttilip.chkm8.model;

import com.anttilip.chkm8.model.pieces.Pawn;
import com.anttilip.chkm8.model.pieces.Piece;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


import static org.junit.Assert.*;

/**
 *
 * @author antti
 */
public class ChessStateTest {

    private ChessState chessState;
    private Piece whiteKing;
    private Piece whitePawn;
    private Piece blackRook;
    private Piece blackQueen;

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
        whiteKing = chessState.getBoard().getPiece(4, 0);
        whitePawn = chessState.getBoard().getPiece(0, 1);
        blackQueen = chessState.getBoard().getPiece(3, 7);
        blackRook = chessState.getBoard().getPiece(0, 7);
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
        assertTrue(piece.getPosition() == whitePawnCopy.getPosition()
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
        assertTrue(piece.getPosition() == whitePawnCopy.getPosition()
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
    public void stateSetSizeIsOneInBeginning() {
        assertTrue(chessState.getGameStates().size() == 1);
    }

    @Test
    public void stateSetSizeIsOneNormally() {
        Piece whitePawn = new Pawn(new Position(3, 3), Player.WHITE);
        chessState.move(whitePawn, new Position(3, 4));
        assertTrue(chessState.getGameStates().size() == 1);
    }

    @Test
    public void stateIsIncompleteInBeginning() {
        assertTrue(chessState.getGameStates().contains(GameState.INCOMPLETE));
    }

    @Test
    public void stateIsIncompleteNormally() {
        Piece whitePawn = new Pawn(new Position(3, 3), Player.WHITE);
        chessState.move(whitePawn, new Position(3, 4));
        assertTrue(chessState.getGameStates().contains(GameState.INCOMPLETE));
    }

    @Test
    public void stateTurnsToCheck() {
        chessState.move(whiteKing, new Position(2, 3));
        chessState.move(blackRook, new Position(2, 5));
        assertTrue(chessState.getGameStates().contains(GameState.CHECK));
    }
    
    @Test
    public void stateIncompleteWhenOnlyCheck() {
        chessState.move(whiteKing, new Position(2, 3));
        chessState.move(blackRook, new Position(2, 5));
        assertTrue(chessState.getGameStates().contains(GameState.INCOMPLETE));
    }

    @Test
    public void stateTurnsToBackToIncomplete() {
        chessState.move(whiteKing, new Position(2, 3));
        chessState.move(blackRook, new Position(2, 5));
        chessState.getGameStates();
        chessState.move(blackRook, new Position(3, 5));
        assertTrue(chessState.getGameStates().contains(GameState.INCOMPLETE));
    }


    @Test
    public void stateTurnsToCheckmate() {
        chessState.move(whiteKing, new Position(2, 3));
        chessState.move(blackRook, new Position(2, 2));
        chessState.move(blackQueen, new Position(2, 4));
        assertTrue(chessState.getGameStates().contains(GameState.CHECKMATE));
    }
    
    @Test
    public void stateNotIncompleteWhenCheckmate() {
        chessState.move(whiteKing, new Position(2, 3));
        chessState.move(blackRook, new Position(2, 2));
        chessState.move(blackQueen, new Position(2, 4));
        assertFalse(chessState.getGameStates().contains(GameState.INCOMPLETE));
    }

    @Test
    public void stateTurnsToRepetition3() {
        chessState.move(whiteKing, new Position(2, 3));
        chessState.move(blackRook, new Position(3, 5));
        chessState.move(whiteKing, new Position(2, 2));
        chessState.move(whiteKing, new Position(2, 3));
        chessState.move(whiteKing, new Position(2, 2));
        chessState.move(whiteKing, new Position(2, 3));
        assertTrue(chessState.getGameStates().contains(GameState.REPETITION3));
    }
    
    @Test
    public void stateIncompleteWhenRepetition3() {
        chessState.move(whiteKing, new Position(2, 3));
        chessState.move(blackRook, new Position(3, 5));
        chessState.move(whiteKing, new Position(2, 2));
        chessState.move(whiteKing, new Position(2, 3));
        chessState.move(whiteKing, new Position(2, 2));
        chessState.move(whiteKing, new Position(2, 3));
        assertTrue(chessState.getGameStates().contains(GameState.INCOMPLETE));
    }

    @Test
    public void stateTurnsToRepetition5() {
        chessState.move(whiteKing, new Position(2, 3));
        chessState.move(blackRook, new Position(3, 5));
        chessState.move(whiteKing, new Position(2, 2));
        chessState.move(whiteKing, new Position(2, 3));
        chessState.move(whiteKing, new Position(2, 2));
        chessState.move(whiteKing, new Position(2, 3));
        chessState.move(whiteKing, new Position(2, 2));
        chessState.move(whiteKing, new Position(2, 3));
        chessState.move(whiteKing, new Position(2, 2));
        chessState.move(whiteKing, new Position(2, 3));
        assertTrue(chessState.getGameStates().contains(GameState.REPETITION5));
    }

    @Test
    public void stateNotIncompleteIfRepetition5() {
        chessState.move(whiteKing, new Position(2, 3));
        chessState.move(blackRook, new Position(3, 5));
        chessState.move(whiteKing, new Position(2, 2));
        chessState.move(whiteKing, new Position(2, 3));
        chessState.move(whiteKing, new Position(2, 2));
        chessState.move(whiteKing, new Position(2, 3));
        chessState.move(whiteKing, new Position(2, 2));
        chessState.move(whiteKing, new Position(2, 3));
        chessState.move(whiteKing, new Position(2, 2));
        chessState.move(whiteKing, new Position(2, 3));
        assertFalse(chessState.getGameStates().contains(GameState.INCOMPLETE));
    }
    
    @Test
    public void stateTurnsToMove50On50thMove() {
        for (int i = 0; i < 25; i++) {
            chessState.move(whiteKing, new Position(2, 3));
            chessState.move(whiteKing, new Position(2, 2));
        }

        assertTrue(chessState.getGameStates().contains(GameState.MOVE50));
    }
    
    
    @Test
    public void stateIncompleteWhenOnly50Move() {
        // Black rook moves strangely to avoid game-ending state REPETITION5
        for (int i = 1; i <= 6; i++) {
            chessState.move(blackRook, new Position(0, i));
            chessState.move(blackRook, new Position(1, i));
            chessState.move(blackRook, new Position(2, i));
            chessState.move(blackRook, new Position(3, i));
            chessState.move(blackRook, new Position(4, i));
            chessState.move(blackRook, new Position(5, i));
            chessState.move(blackRook, new Position(6, i));
            chessState.move(blackRook, new Position(7, i));
        }
        chessState.move(blackRook, new Position(0, 1));
        chessState.move(blackRook, new Position(1, 1));
        assertTrue(chessState.getGameStates().contains(GameState.INCOMPLETE));
    }

    @Test
    public void stateDoesntTurnToMove50On49thMove() {
        for (int i = 0; i < 24; i++) {
            chessState.move(whiteKing, new Position(2, 3));
            chessState.move(whiteKing, new Position(2, 2));
        }
        chessState.move(whiteKing, new Position(2, 3));

        assertFalse(chessState.getGameStates().contains(GameState.MOVE50));
    }

    @Test
    public void stateTurnsToMove75on75thMove() {
        for (int i = 0; i < 37; i++) {
            chessState.move(whiteKing, new Position(2, 3));
            chessState.move(whiteKing, new Position(2, 2));
        }
        chessState.move(whiteKing, new Position(2, 3));

        assertTrue(chessState.getGameStates().contains(GameState.MOVE75));
    }
    
    @Test
    public void stateNotIncompleteWhenMove75() {
        for (int i = 0; i < 37; i++) {
            chessState.move(whiteKing, new Position(2, 3));
            chessState.move(whiteKing, new Position(2, 2));
        }
        chessState.move(whiteKing, new Position(2, 3));

        assertFalse(chessState.getGameStates().contains(GameState.INCOMPLETE));
    }

    @Test
    public void stateDoesntTurnToMove75On74thMove() {
        for (int i = 0; i < 37; i++) {
            chessState.move(whiteKing, new Position(2, 3));
            chessState.move(whiteKing, new Position(2, 2));
        }

        assertFalse(chessState.getGameStates().contains(GameState.MOVE75));
    }

    @Test
    public void stateStaleMateWhenIs() {
        Piece whiteKingCopy = chessState.getBoard().getPiece(4, 0).copy();
        Piece whitePawnCopy = chessState.getBoard().getPiece(0, 1).copy();
        Piece blackKingCopy = chessState.getBoard().getPiece(4, 7).copy();
        Piece blackQueenCopy = chessState.getBoard().getPiece(3, 7).copy();
        Piece blackPawn = chessState.getBoard().getPiece(0, 6).copy();

        chessState.getBoard().getPieces().clear();

        chessState.getBoard().getPieces().add(whiteKingCopy);
        chessState.getBoard().getPieces().add(whitePawnCopy);
        chessState.getBoard().getPieces().add(blackKingCopy);
        chessState.getBoard().getPieces().add(blackQueenCopy);
        chessState.getBoard().getPieces().add(blackPawn);

        chessState.move(whitePawnCopy, new Position(2, 0));
        chessState.move(whiteKingCopy, new Position(7, 7));
        chessState.move(blackKingCopy, new Position(5, 6));
        chessState.move(blackQueenCopy, new Position(6, 5));
        chessState.move(whitePawnCopy, new Position(2, 1));
        chessState.move(blackPawn, new Position(2, 2));

        assertTrue(chessState.getGameStates().contains(GameState.STALEMATE));
    }

    @Test
    public void stateNotIncompleteWhenStalemate() {
        Piece whiteKingCopy = chessState.getBoard().getPiece(4, 0).copy();
        Piece whitePawnCopy = chessState.getBoard().getPiece(0, 1).copy();
        Piece blackKingCopy = chessState.getBoard().getPiece(4, 7).copy();
        Piece blackQueenCopy = chessState.getBoard().getPiece(3, 7).copy();
        Piece blackPawn = chessState.getBoard().getPiece(0, 6).copy();

        chessState.getBoard().getPieces().clear();

        chessState.getBoard().getPieces().add(whiteKingCopy);
        chessState.getBoard().getPieces().add(whitePawnCopy);
        chessState.getBoard().getPieces().add(blackKingCopy);
        chessState.getBoard().getPieces().add(blackQueenCopy);
        chessState.getBoard().getPieces().add(blackPawn);

        chessState.move(whitePawnCopy, new Position(2, 0));
        chessState.move(whiteKingCopy, new Position(7, 7));
        chessState.move(blackKingCopy, new Position(5, 6));
        chessState.move(blackQueenCopy, new Position(6, 5));
        chessState.move(whitePawnCopy, new Position(2, 1));
        chessState.move(blackPawn, new Position(2, 2));

        assertFalse(chessState.getGameStates().contains(GameState.INCOMPLETE));
    }

    @Test
    public void stateStaleMateOnWrongTurn() {
        Piece whiteKingCopy = chessState.getBoard().getPiece(4, 0).copy();
        Piece whitePawnCopy = chessState.getBoard().getPiece(0, 1).copy();
        Piece blackKingCopy = chessState.getBoard().getPiece(4, 7).copy();
        Piece blackQueenCopy = chessState.getBoard().getPiece(3, 7).copy();
        Piece blackPawn = chessState.getBoard().getPiece(0, 6).copy();

        chessState.getBoard().getPieces().clear();

        chessState.getBoard().getPieces().add(whiteKingCopy);
        chessState.getBoard().getPieces().add(whitePawnCopy);
        chessState.getBoard().getPieces().add(blackKingCopy);
        chessState.getBoard().getPieces().add(blackQueenCopy);
        chessState.getBoard().getPieces().add(blackPawn);

        chessState.move(whitePawnCopy, new Position(2, 0));
        chessState.move(whiteKingCopy, new Position(7, 7));
        chessState.move(blackKingCopy, new Position(5, 6));
        chessState.move(blackQueenCopy, new Position(6, 5));
        chessState.move(whitePawnCopy, new Position(2, 1));
        chessState.move(blackPawn, new Position(2, 3));
        chessState.move(blackPawn, new Position(2, 2));

        assertFalse(chessState.getGameStates().contains(GameState.STALEMATE));
    }

    @Test
    public void stateStaleMateWhenIsnt() {
        Piece whiteKingCopy = chessState.getBoard().getPiece(4, 0).copy();
        Piece whitePawnCopy = chessState.getBoard().getPiece(0, 1).copy();
        Piece blackKingCopy = chessState.getBoard().getPiece(4, 7).copy();
        Piece blackQueenCopy = chessState.getBoard().getPiece(3, 7).copy();
        Piece blackPawn = chessState.getBoard().getPiece(0, 6).copy();

        chessState.getBoard().getPieces().clear();

        chessState.getBoard().getPieces().add(whiteKingCopy);
        chessState.getBoard().getPieces().add(whitePawnCopy);
        chessState.getBoard().getPieces().add(blackKingCopy);
        chessState.getBoard().getPieces().add(blackQueenCopy);
        chessState.getBoard().getPieces().add(blackPawn);

        chessState.move(whitePawnCopy, new Position(2, 2));
        chessState.move(whiteKingCopy, new Position(7, 7));
        chessState.move(blackKingCopy, new Position(5, 6));
        chessState.move(blackQueenCopy, new Position(6, 5));
        chessState.move(whitePawnCopy, new Position(2, 3));
        chessState.move(blackPawn, new Position(2, 1));

        assertFalse(chessState.getGameStates().contains(GameState.STALEMATE));
    }

    @Test
    public void stateInsufficientBishop() {
        Piece whiteKingCopy = chessState.getBoard().getPiece(4, 0).copy();
        Piece whiteBishopCopy = chessState.getBoard().getPiece(2, 0).copy();
        Piece blackKingCopy = chessState.getBoard().getPiece(4, 7).copy();

        chessState.getBoard().getPieces().clear();

        chessState.getBoard().getPieces().add(whiteKingCopy);
        chessState.getBoard().getPieces().add(whiteBishopCopy);
        chessState.getBoard().getPieces().add(blackKingCopy);

        assertTrue(chessState.getGameStates().contains(GameState.INSUFFICIENT));
    }

    @Test
    public void stateInsufficient2Bishops() {
        Piece whiteKingCopy = chessState.getBoard().getPiece(4, 0).copy();
        Piece whiteBishopCopy = chessState.getBoard().getPiece(2, 0).copy();
        Piece whiteBishopCopy2 = chessState.getBoard().getPiece(5, 0).copy();
        Piece blackKingCopy = chessState.getBoard().getPiece(4, 7).copy();

        chessState.getBoard().getPieces().clear();

        chessState.getBoard().getPieces().add(whiteKingCopy);
        chessState.getBoard().getPieces().add(whiteBishopCopy);
        chessState.getBoard().getPieces().add(whiteBishopCopy2);
        chessState.getBoard().getPieces().add(blackKingCopy);

        assertFalse(chessState.getGameStates().contains(GameState.INSUFFICIENT));
    }

    @Test
    public void stateInsufficientKnight() {
        Piece whiteKingCopy = chessState.getBoard().getPiece(4, 0).copy();
        Piece whiteKnightCopy = chessState.getBoard().getPiece(1, 0).copy();
        Piece blackKingCopy = chessState.getBoard().getPiece(4, 7).copy();

        chessState.getBoard().getPieces().clear();

        chessState.getBoard().getPieces().add(whiteKingCopy);
        chessState.getBoard().getPieces().add(whiteKnightCopy);
        chessState.getBoard().getPieces().add(blackKingCopy);

        assertTrue(chessState.getGameStates().contains(GameState.INSUFFICIENT));
    }

    @Test
    public void stateInsufficient2Knights() {
        Piece whiteKingCopy = chessState.getBoard().getPiece(4, 0).copy();
        Piece whiteKnightCopy = chessState.getBoard().getPiece(1, 0).copy();
        Piece whiteKnightCopy2 = chessState.getBoard().getPiece(6, 0).copy();
        Piece blackKingCopy = chessState.getBoard().getPiece(4, 7).copy();

        chessState.getBoard().getPieces().clear();

        chessState.getBoard().getPieces().add(whiteKingCopy);
        chessState.getBoard().getPieces().add(whiteKnightCopy);
        chessState.getBoard().getPieces().add(whiteKnightCopy2);
        chessState.getBoard().getPieces().add(blackKingCopy);

        assertFalse(chessState.getGameStates().contains(GameState.INSUFFICIENT));
    }

    @Test
    public void stateInsufficientKnightAndBishop() {
        Piece whiteKingCopy = chessState.getBoard().getPiece(4, 0).copy();
        Piece whiteKnightCopy = chessState.getBoard().getPiece(1, 0).copy();
        Piece whiteBishopCopy = chessState.getBoard().getPiece(5, 0).copy();
        Piece blackKingCopy = chessState.getBoard().getPiece(4, 7).copy();

        chessState.getBoard().getPieces().clear();

        chessState.getBoard().getPieces().add(whiteKingCopy);
        chessState.getBoard().getPieces().add(whiteKnightCopy);
        chessState.getBoard().getPieces().add(whiteBishopCopy);
        chessState.getBoard().getPieces().add(blackKingCopy);

        assertFalse(chessState.getGameStates().contains(GameState.INSUFFICIENT));
    }

    @Test
    public void stateInsufficientOnlyKings() {
        Piece whiteKingCopy = chessState.getBoard().getPiece(4, 0).copy();
        Piece blackKingCopy = chessState.getBoard().getPiece(4, 7).copy();

        chessState.getBoard().getPieces().clear();

        chessState.getBoard().getPieces().add(whiteKingCopy);
        chessState.getBoard().getPieces().add(blackKingCopy);

        assertTrue(chessState.getGameStates().contains(GameState.INSUFFICIENT));
    }

    @Test
    public void stateInsufficientRookFalse() {
        Piece whiteKingCopy = chessState.getBoard().getPiece(4, 0).copy();
        Piece whiteRookCopy = chessState.getBoard().getPiece(0, 0).copy();
        Piece blackKingCopy = chessState.getBoard().getPiece(4, 7).copy();

        chessState.getBoard().getPieces().clear();

        chessState.getBoard().getPieces().add(whiteKingCopy);
        chessState.getBoard().getPieces().add(whiteRookCopy);
        chessState.getBoard().getPieces().add(blackKingCopy);

        assertFalse(chessState.getGameStates().contains(GameState.INSUFFICIENT));
    }

    @Test
    public void stateInsufficientQueenFalse() {
        Piece whiteKingCopy = chessState.getBoard().getPiece(4, 0).copy();
        Piece whiteQueenCopy = chessState.getBoard().getPiece(3, 0).copy();
        Piece blackKingCopy = chessState.getBoard().getPiece(4, 7).copy();

        chessState.getBoard().getPieces().clear();

        chessState.getBoard().getPieces().add(whiteKingCopy);
        chessState.getBoard().getPieces().add(whiteQueenCopy);
        chessState.getBoard().getPieces().add(blackKingCopy);

        assertFalse(chessState.getGameStates().contains(GameState.INSUFFICIENT));
    }

    @Test
    public void stateInsufficientPawnFalse() {
        Piece whiteKingCopy = chessState.getBoard().getPiece(4, 0).copy();
        Piece whitePawnCopy = chessState.getBoard().getPiece(4, 1).copy();
        Piece blackKingCopy = chessState.getBoard().getPiece(4, 7).copy();

        chessState.getBoard().getPieces().clear();

        chessState.getBoard().getPieces().add(whiteKingCopy);
        chessState.getBoard().getPieces().add(whitePawnCopy);
        chessState.getBoard().getPieces().add(blackKingCopy);

        assertFalse(chessState.getGameStates().contains(GameState.INSUFFICIENT));
    }

    @Test
    public void stateInsufficientPawnTrue() {
        Piece whiteKingCopy = chessState.getBoard().getPiece(4, 0).copy();
        Piece whitePawnCopy = chessState.getBoard().getPiece(4, 1).copy();
        Piece blackKingCopy = chessState.getBoard().getPiece(4, 7).copy();

        chessState.getBoard().getPieces().clear();

        chessState.getBoard().getPieces().add(whiteKingCopy);
        chessState.getBoard().getPieces().add(whitePawnCopy);
        chessState.getBoard().getPieces().add(blackKingCopy);
        chessState.move(whitePawnCopy, new Position(4, 4));
        chessState.move(blackKingCopy, new Position(4, 5));

        assertTrue(chessState.getGameStates().contains(GameState.INSUFFICIENT));
    }

    @Test
    public void stateInsufficientPawnAndKnight() {
        Piece whiteKingCopy = chessState.getBoard().getPiece(4, 0).copy();
        Piece whitePawnCopy = chessState.getBoard().getPiece(4, 1).copy();
        Piece whiteKnightCopy = chessState.getBoard().getPiece(1, 0).copy();
        Piece blackKingCopy = chessState.getBoard().getPiece(4, 7).copy();

        chessState.getBoard().getPieces().clear();

        chessState.getBoard().getPieces().add(whiteKingCopy);
        chessState.getBoard().getPieces().add(whitePawnCopy);
        chessState.getBoard().getPieces().add(whiteKnightCopy);
        chessState.getBoard().getPieces().add(blackKingCopy);

        assertFalse(chessState.getGameStates().contains(GameState.INSUFFICIENT));
    }
}
