package com.anttilip.chkm8.model;

import com.anttilip.chkm8.model.pieces.King;
import com.anttilip.chkm8.model.pieces.Pawn;
import com.anttilip.chkm8.model.pieces.Piece;
import org.junit.Before;
import org.junit.Test;

import java.util.EnumSet;

import static org.junit.Assert.*;

public class StateCheckerTest {

    private ChessState chessState;
    private Piece whiteKing;
    private Piece blackRook;
    private Piece blackQueen;

    @Before
    public void setUp() throws Exception {
        chessState = new ChessState();
        whiteKing = chessState.getBoard().getPiece(4, 0);
        blackQueen = chessState.getBoard().getPiece(3, 7);
        blackRook = chessState.getBoard().getPiece(0, 7);
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
    public void pawnsCauseCheckLeft() {
        chessState.getBoard().getPieces().clear();
        chessState.getBoard().getPieces().add(new King(new Position(7, 7), Player.BLACK));
        chessState.getBoard().getPieces().add(new King(new Position(4, 0), Player.WHITE));
        chessState.getBoard().getPieces().add(new Pawn(new Position(3, 1), Player.BLACK));
        assertTrue(chessState.getGameStates().contains(GameState.CHECK));
    }

    @Test
    public void pawnsCauseCheckRight() {
        chessState.getBoard().getPieces().clear();
        chessState.getBoard().getPieces().add(new King(new Position(7, 7), Player.BLACK));
        chessState.getBoard().getPieces().add(new King(new Position(4, 0), Player.WHITE));
        chessState.getBoard().getPieces().add(new Pawn(new Position(5, 1), Player.BLACK));
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
    public void stateInsufficientKnightAndPawn() {
        Piece whiteKingCopy = chessState.getBoard().getPiece(4, 0).copy();
        Piece whiteKnightCopy = chessState.getBoard().getPiece(1, 0).copy();
        Piece whitePawnCopy = chessState.getBoard().getPiece(1, 1).copy();
        Piece blackKingCopy = chessState.getBoard().getPiece(4, 7).copy();

        chessState.getBoard().getPieces().clear();

        chessState.getBoard().getPieces().add(whiteKingCopy);
        chessState.getBoard().getPieces().add(whiteKnightCopy);
        chessState.getBoard().getPieces().add(whitePawnCopy);
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