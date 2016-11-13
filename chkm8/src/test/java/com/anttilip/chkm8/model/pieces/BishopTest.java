/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anttilip.chkm8.model.pieces;

import com.anttilip.chkm8.model.pieces.Bishop;
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
public class BishopTest {
    
    private Bishop bishop;
    private HashMap<Position, Piece> emptyMap;
    
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
        emptyMap = new HashMap();
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void movesInEmptyMapInitPosition() {
        assertTrue(bishop.getAllowedMoves(emptyMap).size() == 13);
    }
    
    @Test
    public void topRightMove() {
        Position position = Position.add(bishop.getPosition(), new Position(2, 2));
        assertTrue(bishop.getAllowedMoves(emptyMap).contains(position));
    }
    
    @Test
    public void topLeftMove() {
        Position position = Position.add(bishop.getPosition(), new Position(-2, 2));
        assertTrue(bishop.getAllowedMoves(emptyMap).contains(position));
    }
    
    @Test
    public void bottomRightMove() {
        Position position = Position.add(bishop.getPosition(), new Position(2, -2));
        assertTrue(bishop.getAllowedMoves(emptyMap).contains(position));
    }
    
    @Test
    public void bottomLeftMove() {
        Position position = Position.add(bishop.getPosition(), new Position(-2, -2));
        assertTrue(bishop.getAllowedMoves(emptyMap).contains(position));
    }
    
    @Test
    public void cantGoUp() {
        Position position = Position.add(bishop.getPosition(), new Position(0, 1));
        assertFalse(bishop.getAllowedMoves(emptyMap).contains(position));
    }
    
    @Test
    public void cantJumpOverPieces() {
        Position position = Position.add(bishop.getPosition(), new Position(2, 2));
        Position inTheWay = Position.add(bishop.getPosition(), new Position(1, 1));
        emptyMap.put(inTheWay, new Pawn(inTheWay, Player.WHITE));
        assertFalse(bishop.getAllowedMoves(emptyMap).contains(position));
    }
    
     @Test
    public void canEatEnemy() {
        Position enemy = Position.add(bishop.getPosition(), new Position(1, 1));
        emptyMap.put(enemy, new Pawn(enemy, Player.BLACK));
        assertTrue(bishop.getAllowedMoves(emptyMap).contains(enemy));
    }
    
    @Test
    public void cantEatOwnPieces() {
        Position friendly = Position.add(bishop.getPosition(), new Position(1, 1));
        emptyMap.put(friendly, new Pawn(friendly, Player.WHITE));
        assertFalse(bishop.getAllowedMoves(emptyMap).contains(friendly));
    }

}
