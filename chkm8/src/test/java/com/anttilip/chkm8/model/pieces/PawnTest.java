/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anttilip.chkm8.model.pieces;

import com.anttilip.chkm8.model.Board;
import com.anttilip.chkm8.model.Player;
import com.anttilip.chkm8.model.Position;
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
        whitePawn = new Pawn(new Position(3, 1), Player.WHITE);
        blackPawn = new Pawn(new Position(3, 6), Player.BLACK);
        board = new Board(new ArrayList<>());
        board.getPieces().add(whitePawn);
        board.getPieces().add(blackPawn);
        // Kings are needed to ensure that game runs correctly
        // However kings are intentionally placed outside the map
        board.getPieces().add(new King(new Position(12, 10), Player.WHITE));
        board.getPieces().add(new King(new Position(10, 12), Player.BLACK));
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
        whitePawn.doFirstMove();
        assertFalse(whitePawn.isFirstMove());
    }

    @Test
    public void whiteInTheEndFalse() {
        assertFalse(whitePawn.isInTheEnd());
    }

    @Test
    public void whiteInTheEndTrue() {
        whitePawn.setPosition(new Position(3, 7));
        assertTrue(whitePawn.isInTheEnd());
    }

    @Test
    public void blackInTheEndFalse() {
        assertFalse(blackPawn.isInTheEnd());
    }

    @Test
    public void blackInTheEndTrue() {
        blackPawn.setPosition(new Position(3, 0));
        assertTrue(blackPawn.isInTheEnd());
    }

    @Test
    public void firstTurnTwoMoves() {
        assertTrue(whitePawn.getAllowedMoves(board, false).size() == 2);
    }

    @Test
    public void secondTurnOneMove() {
        whitePawn.doFirstMove();
        assertTrue(whitePawn.getAllowedMoves(board, false).size() == 1);
    }

    @Test
    public void whiteCorrectRegularMove() {
        Position oneUp = Position.add(whitePawn.getPosition(), new Position(0, 1));
        assertTrue(whitePawn.getAllowedMoves(board, false).contains(oneUp));
    }

    @Test
    public void whiteCorrectDoubleMove() {
        Position twoUp = Position.add(whitePawn.getPosition(), new Position(0, 2));
        assertTrue(whitePawn.getAllowedMoves(board, false).contains(twoUp));
    }

    @Test
    public void whiteCorrectAttackMoveRight() {
        Position right = Position.add(whitePawn.getPosition(), new Position(1, 1));
        board.getPieces(Player.BLACK).get(0).setPosition(right);
        assertTrue(whitePawn.getAllowedMoves(board, false).contains(right));
    }

    @Test
    public void whiteCorrectAttackMoveLeft() {
        Position left = Position.add(whitePawn.getPosition(), new Position(-1, 1));
        board.getPieces(Player.BLACK).get(0).setPosition(left);
        assertTrue(whitePawn.getAllowedMoves(board, false).contains(left));
    }

    @Test
    public void whiteCantJumpOverPiece() {
        Position oneUp = Position.add(whitePawn.getPosition(), new Position(0, 1));
        Position twoUp = Position.add(whitePawn.getPosition(), new Position(0, 2));
        board.getPieces(Player.BLACK).get(0).setPosition(oneUp);
        assertFalse(whitePawn.getAllowedMoves(board, false).contains(twoUp));
    }

    @Test
    public void blackCorrectRegularMove() {
        Position oneDown = Position.add(blackPawn.getPosition(), new Position(0, -1));
        assertTrue(blackPawn.getAllowedMoves(board, false).contains(oneDown));
    }

    @Test
    public void blackCorrectDoubleMove() {
        Position twoDown = Position.add(blackPawn.getPosition(), new Position(0, -2));
        assertTrue(blackPawn.getAllowedMoves(board, false).contains(twoDown));
    }

    @Test
    public void blackCorrectAttackMoveRight() {
        Position right = Position.add(blackPawn.getPosition(), new Position(1, -1));
        board.getPieces(Player.WHITE).get(0).setPosition(right);
        assertTrue(blackPawn.getAllowedMoves(board, false).contains(right));
    }

    @Test
    public void blackCorrectAttackMoveLeft() {
        Position left = Position.add(blackPawn.getPosition(), new Position(-1, -1));
        board.getPieces(Player.WHITE).get(0).setPosition(left);
        assertTrue(blackPawn.getAllowedMoves(board, false).contains(left));
    }

    @Test
    public void blackCantJumpOverPiece() {
        Position oneUp = Position.add(blackPawn.getPosition(), new Position(0, -1));
        Position twoUp = Position.add(blackPawn.getPosition(), new Position(0, -2));
        board.getPieces(Player.WHITE).get(0).setPosition(oneUp);
        assertFalse(blackPawn.getAllowedMoves(board, false).contains(twoUp));
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
        whitePawn.doFirstMove();
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

