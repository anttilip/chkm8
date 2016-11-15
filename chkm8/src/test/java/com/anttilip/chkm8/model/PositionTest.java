/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anttilip.chkm8.model;

import com.anttilip.chkm8.model.Position;
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
public class PositionTest {

    private Position position;
    
    public PositionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        position = new Position(5, 2);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void toStringTest() {
        assertEquals("F3", position.toString());
    }
    
    @Test
    public void offBoardFromLeft() {
        Position outside = new Position(-1, 3);
        assertFalse(outside.onBoard());
    }
    
    @Test
    public void offBoardFromRight() {
        Position outside = new Position(8, 3);
        assertFalse(outside.onBoard());
    }
    
    @Test
    public void offBoardFromBottom() {
        Position outside = new Position(3, -1);
        assertFalse(outside.onBoard());
    }
    
    @Test
    public void offBoardFromTop() {
        Position outside = new Position(3, 8);
        assertFalse(outside.onBoard());
    }
    
    @Test
    public void onBoardCorner() {
        Position corner = new Position(7, 7);
        assertTrue(corner.onBoard());
    }
    
    @Test
    public void twoSamePositionsAreEqual() {
        Position one = new Position(3, 3);
        Position other = new Position(3, 3);
        assertTrue(one.equals(other));
    }
    
    @Test
    public void twoDifferentPositionsAreNotEqual() {
        Position one = new Position(3, 3);
        Position other = new Position(3, 4);
        assertFalse(one.equals(other));
    }
    
    @Test
    public void twoSameObjectsHaveSameHashCode() {
        Position same = new Position(5, 2);
        assertTrue(position.hashCode() == same.hashCode());
    }
    
    @Test
    public void positionAdditionWithZero() {
        assertTrue(Position.add(position, new Position(0, 0)).equals(position));
    }
    
    @Test
    public void positionAddition() {
        Position one = new Position(2, 2);
        Position other = new Position(3, 4);
        Position result = new Position(5, 6);
        assertTrue(Position.add(one, other).equals(result));
    }
    
    @Test
    public void positionAdditionWithNegative() {
        Position one = new Position(2, 2);
        Position other = new Position(-5, -1);
        Position result = new Position(-3, 1);
        assertTrue(Position.add(one, other).equals(result));
    }

    @Test
    public void hashCodeWithSameIsSame() {
        Position other = new Position(5, 2);
        assertTrue(position.hashCode() == other.hashCode());
    }
    @Test
    public void isNotEqualWithString() {
        String other = "Not the same";
        assertFalse(position.equals(other));
    }
}
