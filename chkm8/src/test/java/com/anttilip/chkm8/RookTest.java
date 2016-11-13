/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anttilip.chkm8;

import com.anttilip.chkm8.model.Pawn;
import com.anttilip.chkm8.model.Piece;
import com.anttilip.chkm8.model.Player;
import com.anttilip.chkm8.model.Position;
import com.anttilip.chkm8.model.Rook;
import java.util.HashMap;
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
    private HashMap<Position, Piece> emptyMap;
    
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
        emptyMap = new HashMap();
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void movesInEmptyMapInitPosition() {
        assertTrue(rook.getAllowedMoves(emptyMap).size() == 14);
    }
    
    @Test
    public void topMove() {
        Position position = Position.add(rook.getPosition(), new Position(0, 2));
        assertTrue(rook.getAllowedMoves(emptyMap).contains(position));
    }
    
    @Test
    public void bottomMove() {
        Position position = Position.add(rook.getPosition(), new Position(0, -2));
        assertTrue(rook.getAllowedMoves(emptyMap).contains(position));
    }
    
    @Test
    public void leftMove() {
        Position position = Position.add(rook.getPosition(), new Position(-2, 0));
        assertTrue(rook.getAllowedMoves(emptyMap).contains(position));
    }
    
    @Test
    public void rightMove() {
        Position position = Position.add(rook.getPosition(), new Position(2, 0));
        assertTrue(rook.getAllowedMoves(emptyMap).contains(position));
    }
    
    @Test
    public void cantGoDiagonally() {
        Position position = Position.add(rook.getPosition(), new Position(1, 1));
        assertFalse(rook.getAllowedMoves(emptyMap).contains(position));
    }
    
    @Test
    public void cantJumpOverPieces() {
        Position position = Position.add(rook.getPosition(), new Position(2, 0));
        Position inTheWay = Position.add(rook.getPosition(), new Position(1, 0));
        emptyMap.put(inTheWay, new Pawn(inTheWay, Player.WHITE));
        assertFalse(rook.getAllowedMoves(emptyMap).contains(position));
    }
    
     @Test
    public void canEatEnemy() {
        Position enemy = Position.add(rook.getPosition(), new Position(1, 0));
        emptyMap.put(enemy, new Pawn(enemy, Player.BLACK));
        assertTrue(rook.getAllowedMoves(emptyMap).contains(enemy));
    }
    
    @Test
    public void cantEatOwnPieces() {
        Position friendly = Position.add(rook.getPosition(), new Position(1, 0));
        emptyMap.put(friendly, new Pawn(friendly, Player.WHITE));
        assertFalse(rook.getAllowedMoves(emptyMap).contains(friendly));
    }
}
