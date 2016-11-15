/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anttilip.chkm8.model.pieces;

import com.anttilip.chkm8.model.Board;
import com.anttilip.chkm8.model.Player;
import com.anttilip.chkm8.model.Position;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 *
 * @author antti
 */
public class EnPassantTest {

    private Board board;
    private Pawn movingWhite;
    private Pawn movingBlack;
    private Pawn attackingWhite;
    private Pawn attackingBlack;

    public EnPassantTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        movingWhite = new Pawn(new Position(3, 1), Player.WHITE);
        movingBlack = new Pawn(new Position(5, 6), Player.BLACK);
        attackingWhite = new Pawn(new Position(4, 4), Player.WHITE);
        attackingBlack = new Pawn(new Position(4, 3), Player.BLACK);
        board = new Board(new ArrayList<>());
        board.getPieces().add(movingWhite);
        board.getPieces().add(movingBlack);
        board.getPieces().add(attackingBlack);
        board.getPieces().add(attackingWhite);
        board.getPieces().add(new King(new Position(7, 0), Player.WHITE));
        board.getPieces().add(new King(new Position(0, 7), Player.BLACK));
    }
    
    @After
    public void tearDown() {
    }


    @Test
    public void enPassantIsCreatedWhite() {
        board.movePiece(movingWhite, new Position(3, 3));
        Piece enPassant = board.getTemporaryPieces().get(new Position(3, 2));
        assertTrue(enPassant != null);
    }

    @Test
    public void enPassantIsCreatedBlack() {
        board.movePiece(movingBlack, new Position(5, 4));
        Piece enPassant = board.getTemporaryPieces().get(new Position(5, 5));
        assertTrue(enPassant != null);
    }

    @Test
    public void enPassantIsCorrectClass() {
        board.movePiece(movingWhite, new Position(3, 3));
        Piece enPassant = board.getTemporaryPieces().get(new Position(3, 2));
        assertTrue(enPassant instanceof Pawn);
    }

    @Test
    public void pawnCanAttackEnPassant() {
        board.movePiece(movingWhite, new Position(3, 3));
        assertTrue(board.getAllowedMoves(attackingBlack).contains(new Position(3, 2)));
    }

    @Test
    public void pawnCanAttackEnPassant2() {
        board.movePiece(movingBlack, new Position(5, 4));
        assertTrue(board.getAllowedMoves(attackingWhite).contains(new Position(5, 5)));
    }

    @Test
    public void originalPawnDiesWhenEnPassantDies() {
        board.movePiece(movingWhite, new Position(3, 3));
        board.movePiece(attackingBlack, new Position(3, 2));
        assertFalse(board.getPieces().contains(movingWhite));
    }

    @Test
    public void originalPawnDiesWhenEnPassantDies2() {
        board.movePiece(movingBlack, new Position(5, 4));
        board.movePiece(attackingWhite, new Position(5, 5));
        assertFalse(board.getPieces().contains(movingBlack));
    }

    @Test
    public void originalDoesntDieIfRookAttacksEnPassant() {
        board.movePiece(movingWhite, new Position(3, 3));
        Rook rook = new Rook(new Position(0, 2), Player.BLACK);
        board.getPieces().add(rook);
        board.movePiece(rook, new Position(3, 2));
        assertTrue(board.getPieces().contains(movingWhite));
    }

    @Test
    public void enPassantDoesntBlockOtherPieces() {
        board.movePiece(movingWhite, new Position(3, 3));
        Rook rook = new Rook(new Position(0, 2), Player.BLACK);
        board.getPieces().add(rook);
        assertTrue(board.getAllowedMoves(rook).contains(new Position(5, 2)));
    }

    @Test
    public void enPassantIsRemovedFromTemporaryPieces() {
        board.movePiece(movingWhite, new Position(3, 3));
        board.movePiece(attackingBlack, new Position(3, 2));
        assertFalse(board.getTemporaryPieces().containsKey(new Position(3, 2)));
    }

    @Test
    public void enPassantIsKilledWhenOriginalDies() {
        board.movePiece(movingWhite, new Position(3, 3));
        board.movePiece(attackingBlack, new Position(3, 3));
        assertFalse(board.getTemporaryPieces().containsKey(new Position(3, 2)));
    }
}
