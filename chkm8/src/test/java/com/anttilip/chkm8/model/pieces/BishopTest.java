/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anttilip.chkm8.model.pieces;

import com.anttilip.chkm8.model.Board;
import com.anttilip.chkm8.model.MoveLimitation;
import com.anttilip.chkm8.model.Player;
import com.anttilip.chkm8.model.Position;
import java.util.ArrayList;
import java.util.EnumSet;
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
public class BishopTest {
    
    private Bishop bishop;
    private Board board;
    
    public BishopTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        bishop = new Bishop(new Position(3, 3), Player.WHITE);
        board = new Board(new ArrayList<>());
        board.getPieces().add(bishop);
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
        assertTrue(board.getAllowedMoves(bishop).size() == 13);
    }
    
    @Test
    public void topRightMove() {
        Position position = Position.add(bishop.getPosition(), new Position(2, 2));
        assertTrue(board.getAllowedMoves(bishop).contains(position));
    }
    
    @Test
    public void topLeftMove() {
        Position position = Position.add(bishop.getPosition(), new Position(-2, 2));
        assertTrue(board.getAllowedMoves(bishop).contains(position));
    }
    
    @Test
    public void bottomRightMove() {
        Position position = Position.add(bishop.getPosition(), new Position(2, -2));
        assertTrue(board.getAllowedMoves(bishop).contains(position));
    }
    
    @Test
    public void bottomLeftMove() {
        Position position = Position.add(bishop.getPosition(), new Position(-2, -2));
        assertTrue(board.getAllowedMoves(bishop).contains(position));
    }
    
    @Test
    public void cantGoUp() {
        Position position = Position.add(bishop.getPosition(), new Position(0, 1));
        assertFalse(board.getAllowedMoves(bishop).contains(position));
    }
    
    @Test
    public void cantJumpOverPieces() {
        Position position = Position.add(bishop.getPosition(), new Position(2, 2));
        Position inTheWay = Position.add(bishop.getPosition(), new Position(1, 1));
        board.getPieces().add(new Pawn(inTheWay, Player.WHITE));
        assertFalse(board.getAllowedMoves(bishop).contains(position));
    }
    
     @Test
    public void canEatEnemy() {
        Position enemy = Position.add(bishop.getPosition(), new Position(1, 1));
        board.getPieces().add(new Pawn(enemy, Player.BLACK));
        assertTrue(board.getAllowedMoves(bishop).contains(enemy));
    }
    
    @Test
    public void cantEatOwnPieces() {
        Position friendly = Position.add(bishop.getPosition(), new Position(1, 1));
        board.getPieces().add(new Pawn(friendly, Player.WHITE));
        assertFalse(board.getAllowedMoves(bishop).contains(friendly));
    }

    @Test
    public void moveCantCauseCheck() {
        Piece whiteKing = board.getKing(Player.WHITE);
        Piece blackRook = new Rook(new Position(2, 5), Player.BLACK);
        board.getPieces().add(blackRook);
        board.movePiece(bishop, new Position(4, 5));
        board.movePiece(whiteKing, new Position(5, 5));

        assertTrue(board.getAllowedMoves(bishop).isEmpty());
    }

    @Test
    public void blocksCheck() {
        Piece whiteKing = board.getKing(Player.WHITE);
        Piece blackRook = new Rook(new Position(2, 5), Player.BLACK);
        board.getPieces().add(blackRook);
        board.movePiece(bishop, new Position(4, 4));
        board.movePiece(whiteKing, new Position(5, 5));
        List<Position> moves = board.getAllowedMoves(bishop);
        assertTrue(moves.size() == 1 && moves.contains(new Position(3, 5)));
    }
    
    @Test
    public void copyOfItselfIsSame() {
        assertTrue(bishop.copy().equals(bishop));
    }
    
    @Test
    public void copyOfOtherIsNotSame() {
        Piece other = bishop.copy();
        other.setPosition(new Position(4, 4));
        assertFalse(other.equals(bishop));
    }
    
    @Test
    public void copyOfOtherIsNotSame2() {
        Piece other = new Bishop(bishop.getPosition(), Player.BLACK);
        assertFalse(other.equals(bishop));
    }

    @Test
    public void hashCodeWithSameIsSame() {
        assertTrue(bishop.hashCode() == bishop.copy().hashCode());
    }

    @Test
    public void hashCodeWithOtherIsSame() {
        Pawn other = new Pawn(bishop.position, bishop.player);
        assertTrue(bishop.hashCode() != other.hashCode());
    }
}
