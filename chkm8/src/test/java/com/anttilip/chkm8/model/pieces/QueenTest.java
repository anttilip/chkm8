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
public class QueenTest {
    
    private Queen queen;
    private Board board;
    
    public QueenTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        queen = new Queen(new Position(3, 3), Player.WHITE);
        board = new Board(new ArrayList());
        board.getPieces().add(queen);
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
        assertTrue(queen.getAllowedMoves(board, false).size() == 27);
    }
    
    @Test
    public void topMove() {
        Position position = Position.add(queen.getPosition(), new Position(0, 2));
        assertTrue(queen.getAllowedMoves(board, false).contains(position));
    }
    
    @Test
    public void bottomMove() {
        Position position = Position.add(queen.getPosition(), new Position(0, -2));
        assertTrue(queen.getAllowedMoves(board, false).contains(position));
    }
    
    @Test
    public void leftMove() {
        Position position = Position.add(queen.getPosition(), new Position(-2, 0));
        assertTrue(queen.getAllowedMoves(board, false).contains(position));
    }
    
    @Test
    public void rightMove() {
        Position position = Position.add(queen.getPosition(), new Position(2, 0));
        assertTrue(queen.getAllowedMoves(board, false).contains(position));
    }
    
    @Test
    public void topRightMove() {
        Position position = Position.add(queen.getPosition(), new Position(2, 2));
        assertTrue(queen.getAllowedMoves(board, false).contains(position));
    }
    
    @Test
    public void topLeftMove() {
        Position position = Position.add(queen.getPosition(), new Position(-2, 2));
        assertTrue(queen.getAllowedMoves(board, false).contains(position));
    }
    
    @Test
    public void bottomRightMove() {
        Position position = Position.add(queen.getPosition(), new Position(2, -2));
        assertTrue(queen.getAllowedMoves(board, false).contains(position));
    }
    
    @Test
    public void bottomLeftMove() {
        Position position = Position.add(queen.getPosition(), new Position(-2, -2));
        assertTrue(queen.getAllowedMoves(board, false).contains(position));
    }
    
    @Test
    public void doesntHaveIllegalMove() {
        Position position = Position.add(queen.getPosition(), new Position(3, 2));
        assertFalse(queen.getAllowedMoves(board, false).contains(position));
    }
    
    @Test
    public void cantJumpOverPiecesHorizontally() {
        Position position = Position.add(queen.getPosition(), new Position(2, 0));
        Position inTheWay = Position.add(queen.getPosition(), new Position(1, 0));
        board.getPieces().add(new Pawn(inTheWay, Player.WHITE));
        assertFalse(queen.getAllowedMoves(board, false).contains(position));
    }
    
    @Test
    public void cantJumpOverPiecesDiagonally() {
        Position position = Position.add(queen.getPosition(), new Position(2, 2));
        Position inTheWay = Position.add(queen.getPosition(), new Position(1, 1));
        board.getPieces().add(new Pawn(inTheWay, Player.WHITE));
        assertFalse(queen.getAllowedMoves(board, false).contains(position));
    }
    
     @Test
    public void canEatEnemy() {
        Position enemy = Position.add(queen.getPosition(), new Position(1, 1));
        board.getPieces().add(new Pawn(enemy, Player.BLACK));
        assertTrue(queen.getAllowedMoves(board, false).contains(enemy));
    }
    
    @Test
    public void cantEatOwnPieces() {
        Position friendly = Position.add(queen.getPosition(), new Position(1, 1));
        board.getPieces().add(new Pawn(friendly, Player.WHITE));
        assertFalse(queen.getAllowedMoves(board, false).contains(friendly));
    }
    

}
