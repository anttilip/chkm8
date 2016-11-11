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
public class PawnTest {
    
    private Pawn pawn;
    
    public PawnTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    
    @Test
    public void whitePawnOneForward() {
        Pawn pawn = new Pawn(new Position(3, 1), Player.WHITE);
        assertTrue(pawn.isMoveAllowed(new Position(3, 2)));
    }
    
    @Test
    public void whitePawnTwoForwardStartingPosition() {
        Pawn pawn = new Pawn(new Position(3, 1), Player.WHITE);
        assertTrue(pawn.isMoveAllowed(new Position(3, 3)));
    }
    
    @Test
    public void whitePawnCantGoBackwards() {
        Pawn pawn = new Pawn(new Position(3, 1), Player.WHITE);
        assertFalse(pawn.isMoveAllowed(new Position(3, 0)));
    }
    
    @Test
    public void whitePawnOneForwardNotStartingPosition() {
        Pawn pawn = new Pawn(new Position(3, 3), Player.WHITE);
        assertTrue(pawn.isMoveAllowed(new Position(3, 4)));
    }
    
    @Test
    public void whitePawnTwoForwardNotStartingPosition() {
        Pawn pawn = new Pawn(new Position(3, 2), Player.WHITE);
        assertFalse(pawn.isMoveAllowed(new Position(3, 4)));
    }
}
