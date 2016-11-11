/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.anttilip.chkm8.model.Board;
import com.anttilip.chkm8.model.Pawn;
import com.anttilip.chkm8.model.Piece;
import com.anttilip.chkm8.model.Player;
import com.anttilip.chkm8.model.Position;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class BoardTest {
    Board board;
    
    public BoardTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        board = new Board();
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void checkNotNull() {
        assertFalse(board == null);
    }
    
    @Test
    public void checkInitialPieceCount() {
        assertTrue(board.getPieces().size() == 32);
    }
    
    @Test
    public void checkWhitePieceColor() {
        assertTrue(board.getPieces().get(3).getPlayer() == Player.WHITE);
    }
    
    @Test
    public void checkBlackPieceColor() {
        assertTrue(board.getPieces().get(20).getPlayer() == Player.BLACK);
    }
    
    @Test
    public void checkWhitePawnInitialPosition() {
        assertTrue(board.getPieces().get(3).getClass().getSimpleName().equals("Pawn"));
    }
    
    @Test
    public void checkBlackPawnInitialPosition() {
        assertTrue(board.getPieces().get(20).getClass().getSimpleName().equals("Pawn"));
    }

}
