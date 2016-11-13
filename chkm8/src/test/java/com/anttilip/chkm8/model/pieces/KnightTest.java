/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anttilip.chkm8.model.pieces;

import com.anttilip.chkm8.model.pieces.Knight;
import com.anttilip.chkm8.model.pieces.Pawn;
import com.anttilip.chkm8.model.pieces.Piece;
import com.anttilip.chkm8.model.Player;
import com.anttilip.chkm8.model.Player;
import com.anttilip.chkm8.model.Position;
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
public class KnightTest {
    
    private Knight knight;
    private HashMap<Position, Piece> emptyMap;
    
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
        emptyMap = new HashMap();
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void eightMovesOnEmpty() {
        assertTrue(knight.getAllowedMoves(emptyMap).size() == 8);
    }
    
    @Test
    public void topRightMove() {
        Position topRight = Position.add(knight.getPosition(), new Position(1, 2));
        assertTrue(knight.getAllowedMoves(emptyMap).contains(topRight));
    }
    
    @Test
    public void topLeftMove() {
        Position topLeft = Position.add(knight.getPosition(), new Position(-1, 2));
        assertTrue(knight.getAllowedMoves(emptyMap).contains(topLeft));
    }
    
    @Test
    public void bottomLeftMove() {
        Position bottomLeft = Position.add(knight.getPosition(), new Position(-1, -2));
        assertTrue(knight.getAllowedMoves(emptyMap).contains(bottomLeft));
    }
    
    @Test
    public void bottomRightMove() {
        Position bottomRight = Position.add(knight.getPosition(), new Position(1, -2));
        assertTrue(knight.getAllowedMoves(emptyMap).contains(bottomRight));
    }
    
    @Test
    public void rightTopMove() {
        Position rightTop = Position.add(knight.getPosition(), new Position(2, 1));
        assertTrue(knight.getAllowedMoves(emptyMap).contains(rightTop));
    }
    
    @Test
    public void rightBottomMove() {
        Position rightBottom = Position.add(knight.getPosition(), new Position(2, -1));
        assertTrue(knight.getAllowedMoves(emptyMap).contains(rightBottom));
    }
    
    @Test
    public void leftBottomMove() {
        Position leftBottom = Position.add(knight.getPosition(), new Position(-2, -1));
        assertTrue(knight.getAllowedMoves(emptyMap).contains(leftBottom));
    }
    
    @Test
    public void leftTopMove() {
        Position leftTop = Position.add(knight.getPosition(), new Position(-2, 1));
        assertTrue(knight.getAllowedMoves(emptyMap).contains(leftTop));
    }
    
    @Test
    public void canJumpOverPieces() {
        Position leftTop = Position.add(knight.getPosition(), new Position(-2, 1));
        Position top = Position.add(knight.getPosition(), new Position(0, 1)); 
        Position topRight = Position.add(knight.getPosition(), new Position(1, 1));
        Position topLeft = Position.add(knight.getPosition(), new Position(-1, 1));
        Position left = Position.add(knight.getPosition(), new Position(-1, 0));
        emptyMap.put(top, new Pawn(top, Player.BLACK));
        emptyMap.put(topRight, new Pawn(topRight, Player.BLACK));
        emptyMap.put(topLeft, new Pawn(topLeft, Player.BLACK));
        emptyMap.put(left, new Pawn(topLeft, Player.BLACK));
        assertTrue(knight.getAllowedMoves(emptyMap).contains(leftTop));
    }
    
     @Test
    public void canEatEnemy() {
        Position enemy = Position.add(knight.getPosition(), new Position(2, 1));
        emptyMap.put(enemy, new Pawn(enemy, Player.BLACK));
        assertTrue(knight.getAllowedMoves(emptyMap).contains(enemy));
    }
    
    @Test
    public void cantEatOwnPieces() {
        Position friendly = Position.add(knight.getPosition(), new Position(2, 1));
        emptyMap.put(friendly, new Pawn(friendly, Player.WHITE));
        assertFalse(knight.getAllowedMoves(emptyMap).contains(friendly));
    }
}
