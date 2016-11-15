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
public class KnightTest {
    
    private Knight knight;
    private Board board;
    
    public KnightTest() {   
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        knight = new Knight(new Position(3, 3), Player.WHITE);
        board = new Board(new ArrayList<>());
        board.getPieces().add(knight);
        // Kings are needed to ensure that game runs correctly
        // However kings are intentionally placed outside the map
        board.getPieces().add(new King(new Position(12, 10), Player.WHITE));
        board.getPieces().add(new King(new Position(10, 12), Player.BLACK));
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void eightMovesOnEmpty() {
        assertTrue(knight.getAllowedMoves(board, false).size() == 8);
    }
    
    @Test
    public void topRightMove() {
        Position topRight = Position.add(knight.getPosition(), new Position(1, 2));
        assertTrue(knight.getAllowedMoves(board, false).contains(topRight));
    }
    
    @Test
    public void topLeftMove() {
        Position topLeft = Position.add(knight.getPosition(), new Position(-1, 2));
        assertTrue(knight.getAllowedMoves(board, false).contains(topLeft));
    }
    
    @Test
    public void bottomLeftMove() {
        Position bottomLeft = Position.add(knight.getPosition(), new Position(-1, -2));
        assertTrue(knight.getAllowedMoves(board, false).contains(bottomLeft));
    }
    
    @Test
    public void bottomRightMove() {
        Position bottomRight = Position.add(knight.getPosition(), new Position(1, -2));
        assertTrue(knight.getAllowedMoves(board, false).contains(bottomRight));
    }
    
    @Test
    public void rightTopMove() {
        Position rightTop = Position.add(knight.getPosition(), new Position(2, 1));
        assertTrue(knight.getAllowedMoves(board, false).contains(rightTop));
    }
    
    @Test
    public void rightBottomMove() {
        Position rightBottom = Position.add(knight.getPosition(), new Position(2, -1));
        assertTrue(knight.getAllowedMoves(board, false).contains(rightBottom));
    }
    
    @Test
    public void leftBottomMove() {
        Position leftBottom = Position.add(knight.getPosition(), new Position(-2, -1));
        assertTrue(knight.getAllowedMoves(board, false).contains(leftBottom));
    }
    
    @Test
    public void leftTopMove() {
        Position leftTop = Position.add(knight.getPosition(), new Position(-2, 1));
        assertTrue(knight.getAllowedMoves(board, false).contains(leftTop));
    }
    
    @Test
    public void canJumpOverPieces() {
        Position leftTop = Position.add(knight.getPosition(), new Position(-2, 1));
        Position top = Position.add(knight.getPosition(), new Position(0, 1)); 
        Position topRight = Position.add(knight.getPosition(), new Position(1, 1));
        Position topLeft = Position.add(knight.getPosition(), new Position(-1, 1));
        Position left = Position.add(knight.getPosition(), new Position(-1, 0));
        board.getPieces().add(new Pawn(top, Player.BLACK));
        board.getPieces().add(new Pawn(topRight, Player.BLACK));
        board.getPieces().add(new Pawn(topLeft, Player.BLACK));
        board.getPieces().add(new Pawn(left, Player.BLACK));
        assertTrue(knight.getAllowedMoves(board, false).contains(leftTop));
    }
    
     @Test
    public void canEatEnemy() {
        Position enemy = Position.add(knight.getPosition(), new Position(2, 1));
        board.getPieces().add(new Pawn(enemy, Player.BLACK));
        assertTrue(knight.getAllowedMoves(board, false).contains(enemy));
    }
    
    @Test
    public void cantEatOwnPieces() {
        Position friendly = Position.add(knight.getPosition(), new Position(2, 1));
        board.getPieces().add(new Pawn(friendly, Player.WHITE));
        assertFalse(knight.getAllowedMoves(board, false).contains(friendly));
    }
    
    @Test
    public void copyOfItselfIsSame() {
        assertTrue(knight.copy().equals(knight));
    }
    
    @Test
    public void copyOfOtherIsNotSame() {
        Piece other = knight.copy();
        other.setPosition(new Position(4, 4));
        assertFalse(other.equals(knight));
    }
    
    @Test
    public void copyOfOtherIsNotSame2() {
        Piece other = new Bishop(knight.getPosition(), Player.BLACK);
        assertFalse(other.equals(knight));
    }

    @Test
    public void hashCodeWithSameIsSame() {
        assertTrue(knight.hashCode() == knight.copy().hashCode());
    }

    @Test
    public void hashCodeWithOtherIsSame() {
        Pawn other = new Pawn(knight.position, knight.player);
        assertTrue(knight.hashCode() != other.hashCode());
    }
}
