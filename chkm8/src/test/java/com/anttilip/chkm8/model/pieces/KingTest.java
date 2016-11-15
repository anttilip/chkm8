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
public class KingTest {
    
    private King king;
    private Board board;
    
    public KingTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        king = new King(new Position(3, 3), Player.WHITE);
        board = new Board(new ArrayList<>());
        board.getPieces().add(king);
    }
    
    @After
    public void tearDown() {
    }

     @Test
    public void movesInEmptyMapInitPosition() {
        assertTrue(king.getAllowedMoves(board, false).size() == 8);
    }
    
    @Test
    public void topMove() {
        Position position = Position.add(king.getPosition(), new Position(0, 1));
        assertTrue(king.getAllowedMoves(board, false).contains(position));
    }
    
    @Test
    public void bottomMove() {
        Position position = Position.add(king.getPosition(), new Position(0, -1));
        assertTrue(king.getAllowedMoves(board, false).contains(position));
    }
    
    @Test
    public void leftMove() {
        Position position = Position.add(king.getPosition(), new Position(-1, 0));
        assertTrue(king.getAllowedMoves(board, false).contains(position));
    }
    
    @Test
    public void rightMove() {
        Position position = Position.add(king.getPosition(), new Position(1, 0));
        assertTrue(king.getAllowedMoves(board, false).contains(position));
    }
    
    @Test
    public void topRightMove() {
        Position position = Position.add(king.getPosition(), new Position(1, 1));
        assertTrue(king.getAllowedMoves(board, false).contains(position));
    }
    
    @Test
    public void topLeftMove() {
        Position position = Position.add(king.getPosition(), new Position(-1, 1));
        assertTrue(king.getAllowedMoves(board, false).contains(position));
    }
    
    @Test
    public void bottomRightMove() {
        Position position = Position.add(king.getPosition(), new Position(1, -1));
        assertTrue(king.getAllowedMoves(board, false).contains(position));
    }
    
    @Test
    public void bottomLeftMove() {
        Position position = Position.add(king.getPosition(), new Position(-1, -1));
        assertTrue(king.getAllowedMoves(board, false).contains(position));
    }
    
    @Test
    public void canEatEnemy() {
        Position enemy = Position.add(king.getPosition(), new Position(1, 1));
        board.getPieces().add(new Pawn(enemy, Player.BLACK));
        assertTrue(king.getAllowedMoves(board, false).contains(enemy));
    }
    
    @Test
    public void cantEatOwnPieces() {
        Position friendly = Position.add(king.getPosition(), new Position(1, 1));
        board.getPieces().add(new Pawn(friendly, Player.WHITE));
        assertFalse(king.getAllowedMoves(board, false).contains(friendly));
    }
    
    @Test
    public void copyOfItselfIsSame() {
        assertTrue(king.copy().equals(king));
    }
    
    @Test
    public void copyOfOtherIsNotSame() {
        Piece other = king.copy();
        other.setPosition(new Position(4, 4));
        assertFalse(other.equals(king));
    }
    
    @Test
    public void copyOfOtherIsNotSame2() {
        Piece other = new Bishop(king.getPosition(), Player.BLACK);
        assertFalse(other.equals(king));
    }

    @Test
    public void hashCodeWithSameIsSame() {
        assertTrue(king.hashCode() == king.copy().hashCode());
    }

    @Test
    public void hashCodeWithOtherIsSame() {
        Pawn other = new Pawn(king.position, king.player);
        assertTrue(king.hashCode() != other.hashCode());
    }
}
