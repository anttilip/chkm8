package com.anttilip.chkm8.model.pieces;

import com.anttilip.chkm8.model.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author antti
 */
public class PawnTest {

    private Pawn whitePawn;
    private Pawn blackPawn;
    private King whiteKing;
    private King blackKing;
    private Board board;

    public PawnTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        // Kings are needed to ensure that game runs correctly
        // However kings are intentionally placed outside the map
        whitePawn = new Pawn(new Position(3, 1), Player.WHITE);
        blackPawn = new Pawn(new Position(3, 6), Player.BLACK);
        whiteKing = new King(new Position(0, 0), Player.WHITE);
        blackKing = new King(new Position(7, 7), Player.BLACK);
        board = new Board(new ArrayList<>());
        board.getPieces().add(whitePawn);
        board.getPieces().add(blackPawn);
        board.getPieces().add(whiteKing);
        board.getPieces().add(blackKing);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void initiallyFirstMove() {
        assertTrue(whitePawn.isFirstMove());
    }

    @Test
    public void afterMoveIsNotFirstMove() {
        board.movePiece(whitePawn, new Position(3, 2));
        assertFalse(whitePawn.isFirstMove());
    }

    @Test
    public void firstTurnTwoMoves() {
        ChessState cs = new ChessState();
        cs.getBoard().getPieces().clear();
        cs.getBoard().getPieces().add(whitePawn);
        cs.getBoard().getPieces().add(whiteKing);
        assertTrue(cs.getBoard().getAllowedMoves(whitePawn).size() == 2);
    }

    @Test
    public void secondTurnOneMove() {
        ChessState cs = new ChessState();
        cs.getBoard().getPieces().clear();
        cs.getBoard().getPieces().add(whitePawn);
        cs.getBoard().getPieces().add(whiteKing);
        cs.move(whitePawn, new Position(3, 2));

        assertTrue(cs.getBoard().getAllowedMoves(whitePawn).size() == 1);
    }

    @Test
    public void whiteCorrectRegularMove() {
        Position oneUp = Position.add(whitePawn.getPosition(), new Position(0, 1));
        assertTrue(board.getAllowedMoves(whitePawn).contains(oneUp));
    }

    @Test
    public void whiteCorrectDoubleMove() {
        Position twoUp = Position.add(whitePawn.getPosition(), new Position(0, 2));
        assertTrue(board.getAllowedMoves(whitePawn).contains(twoUp));
    }

    @Test
    public void whiteCorrectAttackMoveRight() {
        Position right = Position.add(whitePawn.getPosition(), new Position(1, 1));
        board.getPieces(Player.BLACK).get(0).setPosition(right);
        assertTrue(board.getAllowedMoves(whitePawn).contains(right));
    }

    @Test
    public void whiteCorrectAttackMoveLeft() {
        Position left = Position.add(whitePawn.getPosition(), new Position(-1, 1));
        board.getPieces(Player.BLACK).get(0).setPosition(left);
        assertTrue(board.getAllowedMoves(whitePawn).contains(left));
    }

    @Test
    public void whiteCantJumpOverPiece() {
        Position oneUp = Position.add(whitePawn.getPosition(), new Position(0, 1));
        Position twoUp = Position.add(whitePawn.getPosition(), new Position(0, 2));
        board.getPieces(Player.BLACK).get(0).setPosition(oneUp);
        assertFalse(board.getAllowedMoves(whitePawn).contains(twoUp));
    }

    @Test
    public void blackCorrectRegularMove() {
        Position oneDown = Position.add(blackPawn.getPosition(), new Position(0, -1));
        assertTrue(board.getAllowedMoves(blackPawn).contains(oneDown));
    }

    @Test
    public void blackCorrectDoubleMove() {
        Position twoDown = Position.add(blackPawn.getPosition(), new Position(0, -2));
        assertTrue(board.getAllowedMoves(blackPawn).contains(twoDown));
    }

    @Test
    public void blackCorrectAttackMoveRight() {
        Position right = Position.add(blackPawn.getPosition(), new Position(1, -1));
        board.getPieces(Player.WHITE).get(0).setPosition(right);
        assertTrue(board.getAllowedMoves(blackPawn).contains(right));
    }

    @Test
    public void blackCorrectAttackMoveLeft() {
        Position left = Position.add(blackPawn.getPosition(), new Position(-1, -1));
        board.getPieces(Player.WHITE).get(0).setPosition(left);
        assertTrue(board.getAllowedMoves(blackPawn).contains(left));
    }

    @Test
    public void blackCantJumpOverPiece() {
        Position oneUp = Position.add(blackPawn.getPosition(), new Position(0, -1));
        Position twoUp = Position.add(blackPawn.getPosition(), new Position(0, -2));
        board.getPieces(Player.WHITE).get(0).setPosition(oneUp);
        assertFalse(board.getAllowedMoves(blackPawn).contains(twoUp));
    }

    @Test
    public void moveCantCauseCheck() {
        Piece whiteKing = board.getKing(Player.WHITE);
        Piece blackRook = new Rook(new Position(2, 5), Player.BLACK);
        board.getPieces().add(blackRook);
        board.movePiece(whitePawn, new Position(4, 5));
        board.movePiece(whiteKing, new Position(5, 5));

        assertTrue(board.getAllowedMoves(whitePawn).isEmpty());
    }

    @Test
    public void blocksCheck() {
        Piece whiteKing = board.getKing(Player.WHITE);
        Piece blackRook = new Rook(new Position(2, 5), Player.BLACK);
        board.getPieces().add(blackRook);
        board.movePiece(whitePawn, new Position(4, 4));
        board.movePiece(whiteKing, new Position(5, 5));
        List<Position> moves = board.getAllowedMoves(whitePawn);
         assertTrue(moves.size() == 1 && moves.contains(new Position(4, 5)));
    }
    
    @Test
    public void copyOfItselfIsSame() {
        assertTrue(whitePawn.copy().equals(whitePawn));
    }
    
    @Test
    public void copyOfOtherIsNotSame() {
        Piece other = whitePawn.copy();
        other.setPosition(new Position(4, 4));
        assertFalse(other.equals(whitePawn));
    }
    
    @Test
    public void copyOfOtherIsNotSame3() {
        Piece other = whitePawn.copy();
        board.movePiece(whitePawn, new Position(3, 2));
        assertFalse(other.equals(whitePawn));
    }

    @Test
    public void hashCodeWithSameIsSame() {
        assertTrue(whitePawn.hashCode() == whitePawn.copy().hashCode());
    }

    @Test
    public void hashCodeWithOtherIsSame() {
        Bishop other = new Bishop(whitePawn.position, whitePawn.player);
        assertTrue(whitePawn.hashCode() != other.hashCode());
    }

    @Test
    public void promotionToQueen() {
        board.movePiece(this.whitePawn, new Position(3, 7));
        board.getAllowedMoves(board.getPiece(3, 7));
        assertTrue(board.getPiece(3, 7) instanceof Queen);
    }

    @Test
    public void promotionToQueenSamePlayer() {
        board.movePiece(this.whitePawn, new Position(3, 7));
        assertTrue(board.getPiece(3, 7).getPlayer() == whitePawn.player);
    }
}

