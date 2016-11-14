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
public class MoveTest {

    private Move move;
    private Board board;
    private Piece whitePawn;
    private Position origin;
    private Position target;

    public MoveTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        board = Board.createBoard();
        whitePawn = board.getPiece(7, 1);
        origin = whitePawn.getPosition();
        target = new Position(7, 2);
        move = new Move(whitePawn, origin, target, board.hashCode(), board);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void moveNotNull() {
        assertTrue(move != null);
    }
    
    @Test
    public void getBoardReturnsCorrect() {
        assertTrue(move.getBoard() == board);
    }
    
    @Test
    public void getBoardHashReturnsCorrect() {
        assertTrue(move.getBoardHash() == board.hashCode());
    }
    
    @Test
    public void getOriginReturnsCorrect() {
        assertTrue(move.getOrigin() == origin);
    }
    
    @Test
    public void getTargetReturnsCorrect() {
        assertTrue(move.getTarget() == target);
    }
    
    @Test
    public void getPieceReturnsCorrect() {
        assertTrue(move.getPiece() == whitePawn);
    }
    
    @Test
    public void toStringWorks() {
        assertTrue(move.toString().equals("WHITE Pawn from H2 to H3"));
    }
}
