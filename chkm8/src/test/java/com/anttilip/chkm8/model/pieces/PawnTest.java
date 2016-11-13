/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anttilip.chkm8.model.pieces;

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
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author antti
 */
public class PawnTest {

    private Pawn whitePawn;
    private Pawn blackPawn;
    private HashMap<Position, Piece> emptyMap;

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
        whitePawn = new Pawn(new Position(3, 1), Player.WHITE);
        blackPawn = new Pawn(new Position(3, 6), Player.BLACK);
        emptyMap = new HashMap();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void initiallyFirstMove() {
        assertTrue(whitePawn.isFirstMove());
    }
    
    @Test
    public void afterMoveisNotFirstMove() {
        whitePawn.doFirstMove();
        assertFalse(whitePawn.isFirstMove());
    }

    @Test
    public void whiteInTheEndFalse() {
        assertFalse(whitePawn.isInTheEnd());
    }

    @Test
    public void whiteInTheEndTrue() {
        whitePawn.setPosition(new Position(3, 7));
        assertTrue(whitePawn.isInTheEnd());
    }

    @Test
    public void blackInTheEndFalse() {
        assertFalse(blackPawn.isInTheEnd());
    }

    @Test
    public void blackInTheEndTrue() {
        blackPawn.setPosition(new Position(3, 0));
        assertTrue(blackPawn.isInTheEnd());
    }

    @Test
    public void firstTurnTwoMoves() {
        assertTrue(whitePawn.getAllowedMoves(emptyMap).size() == 2);
    }

    @Test
    public void secondTurnOneMove() {
        whitePawn.doFirstMove();
        assertTrue(whitePawn.getAllowedMoves(emptyMap).size() == 1);
    }

    @Test
    public void whiteCorrectRegularMove() {
        Position oneUp = Position.add(whitePawn.getPosition(), new Position(0, 1));
        assertTrue(whitePawn.getAllowedMoves(emptyMap).contains(oneUp));
    }

    @Test
    public void whiteCorrectDoubleMove() {
        Position twoUp = Position.add(whitePawn.getPosition(), new Position(0, 2));
        assertTrue(whitePawn.getAllowedMoves(emptyMap).contains(twoUp));
    }

    @Test
    public void whiteCorrectAttackMoveRight() {
        Position right = Position.add(whitePawn.getPosition(), new Position(1, 1));
        emptyMap.put(right, blackPawn);
        assertTrue(whitePawn.getAllowedMoves(emptyMap).contains(right));
    }

    @Test
    public void whiteCorrectAttackMoveLeft() {
        Position left = Position.add(whitePawn.getPosition(), new Position(-1, 1));
        emptyMap.put(left, blackPawn);
        assertTrue(whitePawn.getAllowedMoves(emptyMap).contains(left));
    }

    @Test
    public void whiteCantJumpOverPiece() {
        Position oneUp = Position.add(whitePawn.getPosition(), new Position(0, 1));
        Position twoUp = Position.add(whitePawn.getPosition(), new Position(0, 2));
        emptyMap.put(oneUp, blackPawn);
        assertFalse(whitePawn.getAllowedMoves(emptyMap).contains(twoUp));
    }

    @Test
    public void blackCorrectRegularMove() {
        Position oneDown = Position.add(blackPawn.getPosition(), new Position(0, -1));
        assertTrue(blackPawn.getAllowedMoves(emptyMap).contains(oneDown));
    }

    @Test
    public void blackCorrectDoubleMove() {
        Position twoDown = Position.add(blackPawn.getPosition(), new Position(0, -2));
        assertTrue(blackPawn.getAllowedMoves(emptyMap).contains(twoDown));
    }

    @Test
    public void blackCorrectAttackMoveRight() {
        Position right = Position.add(blackPawn.getPosition(), new Position(1, -1));
        emptyMap.put(right, whitePawn);
        assertTrue(blackPawn.getAllowedMoves(emptyMap).contains(right));
    }

    @Test
    public void blackCorrectAttackMoveLeft() {
        Position left = Position.add(blackPawn.getPosition(), new Position(-1, -1));
        emptyMap.put(left, whitePawn);
        assertTrue(blackPawn.getAllowedMoves(emptyMap).contains(left));
    }

    @Test
    public void blackCantJumpOverPiece() {
        Position oneUp = Position.add(blackPawn.getPosition(), new Position(0, -1));
        Position twoUp = Position.add(blackPawn.getPosition(), new Position(0, -2));
        emptyMap.put(oneUp, whitePawn);
        assertFalse(blackPawn.getAllowedMoves(emptyMap).contains(twoUp));
    }
}

