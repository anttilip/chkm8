/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anttilip.chkm8;

import com.anttilip.chkm8.model.pieces.King;
import com.anttilip.chkm8.model.pieces.Pawn;
import com.anttilip.chkm8.model.pieces.Piece;
import com.anttilip.chkm8.model.Player;
import com.anttilip.chkm8.model.Position;
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
public class KingTest {
    
    private King king;
    private HashMap<Position, Piece> emptyMap;
    
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
        emptyMap = new HashMap();
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void initiallyNotChecked() {
        assertFalse(king.isChecked());
    }
    
    @Test
    public void afterCheckChecked() {
        king.setIsChecked(true);
        assertTrue(king.isChecked());
    }
    
     @Test
    public void movesInEmptyMapInitPosition() {
        assertTrue(king.getAllowedMoves(emptyMap).size() == 8);
    }
    
    @Test
    public void topMove() {
        Position position = Position.add(king.getPosition(), new Position(0, 1));
        assertTrue(king.getAllowedMoves(emptyMap).contains(position));
    }
    
    @Test
    public void bottomMove() {
        Position position = Position.add(king.getPosition(), new Position(0, -1));
        assertTrue(king.getAllowedMoves(emptyMap).contains(position));
    }
    
    @Test
    public void leftMove() {
        Position position = Position.add(king.getPosition(), new Position(-1, 0));
        assertTrue(king.getAllowedMoves(emptyMap).contains(position));
    }
    
    @Test
    public void rightMove() {
        Position position = Position.add(king.getPosition(), new Position(1, 0));
        assertTrue(king.getAllowedMoves(emptyMap).contains(position));
    }
    
    @Test
    public void topRightMove() {
        Position position = Position.add(king.getPosition(), new Position(1, 1));
        assertTrue(king.getAllowedMoves(emptyMap).contains(position));
    }
    
    @Test
    public void topLeftMove() {
        Position position = Position.add(king.getPosition(), new Position(-1, 1));
        assertTrue(king.getAllowedMoves(emptyMap).contains(position));
    }
    
    @Test
    public void bottomRightMove() {
        Position position = Position.add(king.getPosition(), new Position(1, -1));
        assertTrue(king.getAllowedMoves(emptyMap).contains(position));
    }
    
    @Test
    public void bottomLeftMove() {
        Position position = Position.add(king.getPosition(), new Position(-1, -1));
        assertTrue(king.getAllowedMoves(emptyMap).contains(position));
    }
    
    @Test
    public void canEatEnemy() {
        Position enemy = Position.add(king.getPosition(), new Position(1, 1));
        emptyMap.put(enemy, new Pawn(enemy, Player.BLACK));
        assertTrue(king.getAllowedMoves(emptyMap).contains(enemy));
    }
    
    @Test
    public void cantEatOwnPieces() {
        Position friendly = Position.add(king.getPosition(), new Position(1, 1));
        emptyMap.put(friendly, new Pawn(friendly, Player.WHITE));
        assertFalse(king.getAllowedMoves(emptyMap).contains(friendly));
    }
}
