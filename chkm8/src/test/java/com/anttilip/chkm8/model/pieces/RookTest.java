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
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author antti
 */
public class RookTest {
    
    private Rook rook;
    private Board board;
    
    public RookTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        rook = new Rook(new Position(3, 3), Player.WHITE);
        board = new Board(new ArrayList<>());
        board.getPieces().add(rook);
        // Kings are needed to ensure that game runs correctly
        // However kings are intentionally placed outside the map
        board.getPieces().add(new King(new Position(12, 10), Player.WHITE));
        board.getPieces().add(new King(new Position(10, 12), Player.BLACK));
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void movesInEmptyMapInitPosition() {
        assertTrue(board.getAllowedMoves(rook).size() == 14);
    }
    
    @Test
    public void topMove() {
        Position position = Position.add(rook.getPosition(), new Position(0, 2));
        assertTrue(board.getAllowedMoves(rook).contains(position));
    }
    
    @Test
    public void bottomMove() {
        Position position = Position.add(rook.getPosition(), new Position(0, -2));
        assertTrue(board.getAllowedMoves(rook).contains(position));
    }
    
    @Test
    public void leftMove() {
        Position position = Position.add(rook.getPosition(), new Position(-2, 0));
        assertTrue(board.getAllowedMoves(rook).contains(position));
    }
    
    @Test
    public void rightMove() {
        Position position = Position.add(rook.getPosition(), new Position(2, 0));
        assertTrue(board.getAllowedMoves(rook).contains(position));
    }
    
    @Test
    public void cantGoDiagonally() {
        Position position = Position.add(rook.getPosition(), new Position(1, 1));
        assertFalse(board.getAllowedMoves(rook).contains(position));
    }
    
    @Test
    public void cantJumpOverPieces() {
        Position position = Position.add(rook.getPosition(), new Position(2, 0));
        Position inTheWay = Position.add(rook.getPosition(), new Position(1, 0));
        board.getPieces().add(new Pawn(inTheWay, Player.WHITE));
        assertFalse(board.getAllowedMoves(rook).contains(position));
    }
    
     @Test
    public void canEatEnemy() {
        Position enemy = Position.add(rook.getPosition(), new Position(1, 0));
        board.getPieces().add(new Pawn(enemy, Player.BLACK));
        assertTrue(board.getAllowedMoves(rook).contains(enemy));
    }
    
    @Test
    public void cantEatOwnPieces() {
        Position friendly = Position.add(rook.getPosition(), new Position(1, 0));
        board.getPieces().add(new Pawn(friendly, Player.WHITE));
        assertFalse(board.getAllowedMoves(rook).contains(friendly));
    }

    @Test
    public void moveCantCauseCheck() {
        Piece whiteKing = board.getKing(Player.WHITE);
        Piece blackBishop = new Bishop(new Position(3, 7), Player.BLACK);
        board.getPieces().add(blackBishop);
        board.movePiece(rook, new Position(4, 6));
        board.movePiece(whiteKing, new Position(5, 5));

        assertTrue(board.getAllowedMoves(rook).isEmpty());
    }

    @Test
    public void blocksCheck() {
        Piece whiteKing = board.getKing(Player.WHITE);
        Piece blackBishop = new Bishop(new Position(3, 7), Player.BLACK);
        board.getPieces().add(blackBishop);
        board.movePiece(rook, new Position(4, 5));
        board.movePiece(whiteKing, new Position(5, 5));
        List<Position> moves = board.getAllowedMoves(rook);
        assertTrue(moves.size() == 1 && moves.contains(new Position(4, 6)));
    }
    
    @Test
    public void copyOfItselfIsSame() {
        assertTrue(rook.copy().equals(rook));
    }
    
    @Test
    public void copyOfOtherIsNotSame() {
        Piece other = rook.copy();
        other.setPosition(new Position(4, 4));
        assertFalse(other.equals(rook));
    }

    @Test
    public void hashCodeWithSameIsSame() {
        assertTrue(rook.hashCode() == rook.copy().hashCode());
    }

    @Test
    public void hashCodeWithOtherIsSame() {
        Pawn other = new Pawn(rook.position, rook.player);
        assertTrue(rook.hashCode() != other.hashCode());
    }
}
