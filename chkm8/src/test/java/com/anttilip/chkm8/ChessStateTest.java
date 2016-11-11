/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anttilip.chkm8;

import com.anttilip.chkm8.model.ChessState;
import com.anttilip.chkm8.model.Player;
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
public class ChessStateTest {
    
    ChessState chessState;
    
    public ChessStateTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        chessState = new ChessState();
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void notNull() {
        assertFalse(chessState == null);
    }
    
    @Test
    public void moveCounterInitallyZero() {
        assertTrue(chessState.getMoveCount() == 0);
    }
    
    @Test
    public void moveCounterIncrementsOnce() {
        chessState.run();
        assertTrue(chessState.getMoveCount() == 1);
    }
    
    @Test
    public void moveCounterIncrementsTwice() {
        chessState.run();
        chessState.run();
        assertTrue(chessState.getMoveCount() == 2);
    }
    
    @Test
    public void firstTurnIsWhite() {
        assertTrue(chessState.getCurrentPlayer() == Player.WHITE);
    }
    
    @Test
    public void secondTurnBlack() {
        chessState.run();
        assertTrue(chessState.getCurrentPlayer() == Player.BLACK);
    }
    
    @Test
    public void thirdTurnIsWhiteAgain() {
        chessState.run();
        chessState.run();
        assertTrue(chessState.getCurrentPlayer() == Player.WHITE);
    }
}
