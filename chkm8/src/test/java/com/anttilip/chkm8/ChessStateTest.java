/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anttilip.chkm8;

import com.anttilip.chkm8.model.Bishop;
import com.anttilip.chkm8.model.Board;
import com.anttilip.chkm8.model.ChessState;
import com.anttilip.chkm8.model.Move;
import com.anttilip.chkm8.model.Piece;
import com.anttilip.chkm8.model.Player;
import com.anttilip.chkm8.model.Position;
import java.util.List;
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
    public void getBoardNotNull() {
        assertFalse(chessState.getBoard() == null);
    }

    @Test
    public void moveCounterInitallyZero() {
        assertTrue(chessState.getMoveCount() == 0);
    }

    @Test
    public void moveCounterIncrementsOnce() {
        Board board = chessState.getBoard();
        chessState.move(board.getPiece(3, 0), new Position(5, 0));
        assertTrue(chessState.getMoveCount() == 1);
    }

    @Test
    public void moveCounterIncrementsTwice() {
        Piece whiteQueen = chessState.getBoard().getPiece(new Position(3, 0));
        chessState.move(whiteQueen, new Position(3, 3));
        chessState.move(whiteQueen, new Position(3, 6));
        assertTrue(chessState.getMoveCount() == 2);
    }

    @Test
    public void firstTurnIsWhite() {
        assertTrue(chessState.getCurrentPlayer() == Player.WHITE);
    }

    @Test
    public void secondTurnBlack() {
        Piece whiteQueen = chessState.getBoard().getPiece(new Position(3, 0));
        chessState.move(whiteQueen, new Position(3, 3));
        assertTrue(chessState.getCurrentPlayer() == Player.BLACK);
    }

    @Test
    public void thirdTurnIsWhiteAgain() {
        Piece whiteQueen = chessState.getBoard().getPiece(new Position(3, 0));
        chessState.move(whiteQueen, new Position(3, 3));
        chessState.move(whiteQueen, new Position(3, 6));
        assertTrue(chessState.getCurrentPlayer() == Player.WHITE);
    }

    @Test
    public void moveHistoryOutputIsValid() {
        Piece whiteQueen = chessState.getBoard().getPiece(new Position(3, 0));
        chessState.move(whiteQueen, new Position(3, 3));
        assertTrue(chessState.getMoveHistory().get(0).toString().equals("WHITE Queen from D1 to D4"));
    }
}
