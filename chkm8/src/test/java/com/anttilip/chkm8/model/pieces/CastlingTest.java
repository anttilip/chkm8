/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anttilip.chkm8.model.pieces;

import com.anttilip.chkm8.model.Board;
import com.anttilip.chkm8.model.ChessState;
import com.anttilip.chkm8.model.Player;
import com.anttilip.chkm8.model.Position;
import org.junit.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author antti
 */
public class CastlingTest {

    private Board board;
    private King whiteKing;
    private Rook whiteQueenSideRook;
    private Rook whiteKingSideRook;
    private Bishop whiteBishop;
    private King blackKing;
    private Rook blackQueenSideRook;
    private Rook blackKingSideRook;
    private Bishop blackBishop;

    public CastlingTest() {


    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.whiteKing = new King(new Position(4, 0), Player.WHITE);
        this.whiteQueenSideRook = new Rook(new Position(0, 0), Player.WHITE);
        this.whiteKingSideRook = new Rook(new Position(7, 0), Player.WHITE);
        this.whiteBishop = new Bishop(new Position(2, 1), Player.WHITE);

        this.blackKing = new King(new Position(4, 7), Player.BLACK);
        this.blackQueenSideRook = new Rook(new Position(0, 7), Player.BLACK);
        this.blackKingSideRook = new Rook(new Position(7, 7), Player.BLACK);
        this.blackBishop = new Bishop(new Position(4, 7), Player.BLACK);

        List<Piece> pieces = new ArrayList<>();
        pieces.add(whiteKing);
        pieces.add(whiteQueenSideRook);
        pieces.add(whiteKingSideRook);
        pieces.add(whiteBishop);
        pieces.add(blackKing);
        pieces.add(blackQueenSideRook);
        pieces.add(blackKingSideRook);
        pieces.add(blackBishop);

        // Add pawn between rooks so they don't threaten each other and can castle
        pieces.add(new Pawn(new Position(7, 1), Player.WHITE));
        pieces.add(new Pawn(new Position(0, 6), Player.BLACK));
        this.board = new Board(pieces);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void whiteKingCanCastleKingSide() {
        assertTrue( board.getAllowedMoves(whiteKing).contains(whiteKingSideRook.getPosition()));
    }

    @Test
    public void whiteQueenCanCastleKingSide() {
        assertTrue(board.getAllowedMoves(whiteKing).contains(whiteQueenSideRook.position));
    }

    @Test
    public void blackKingCanCastleKingSide() {
        assertTrue(board.getAllowedMoves(blackKing).contains(blackKingSideRook.position));
    }

    @Test
    public void blackQueenCanCastleKingSide() {
        assertTrue(board.getAllowedMoves(blackKing).contains(blackKingSideRook.position));
    }

    @Test
    public void cantCastleWhenKingIsThreatened() {
        board.movePiece(whiteBishop, new Position(6, 5));
        assertFalse(board.getAllowedMoves(blackKing).contains(blackKingSideRook.position));
    }

    @Test
    public void canCastleWhenRookIsThreatened() {
        board.movePiece(whiteBishop, new Position(3, 3));
        assertTrue(board.getAllowedMoves(blackKing).contains(blackKingSideRook.position));
    }

    @Test
    public void cantCastleWhenSquareInBetweenIsThreatened() {
        board.movePiece(whiteBishop, new Position(2, 3));
        assertFalse(board.getAllowedMoves(blackKing).contains(blackKingSideRook.position));
    }

    @Test
    public void cantCastleWhenSquareInBetweenIsThreatenedBothSides() {
        board.movePiece(blackBishop, new Position(4, 2));
        List<Position> moves = board.getAllowedMoves(whiteKing);
        assertTrue(!moves.contains(whiteKingSideRook.position) && !moves.contains(whiteQueenSideRook.position));
    }

    @Test
    public void cantCastleWhenOwnPieceBlocks() {
        board.movePiece(whiteBishop, new Position(6, 0));
        assertFalse(board.getAllowedMoves(whiteKing).contains(whiteKingSideRook.position));
    }

    @Test
    public void cantCastleWhenOpponentsPieceBlocks() {
        board.movePiece(whiteBishop, new Position(2, 7));
        assertFalse(board.getAllowedMoves(blackKing).contains(blackQueenSideRook.position));
    }

    @Test
    public void castlingMovesKingToRightPositionWhiteQueenSide() {
        board.movePiece(whiteKing, new Position(0, 0));
        assertTrue(board.getPiece(2, 0).equals(whiteKing));
    }

    @Test
    public void castlingMovesRookToRightPositionWhiteQueenSide() {
        board.movePiece(whiteKing, new Position(0, 0));
        assertTrue(board.getPiece(3, 0).equals(whiteQueenSideRook));
    }

    @Test
    public void castlingMovesKingToRightPositionWhiteKingSide() {
        board.movePiece(whiteKing, new Position(7, 0));
        assertTrue(board.getPiece(6, 0).equals(whiteKing));
    }

    @Test
    public void castlingMovesRookToRightPositionWhiteKingSide() {
        board.movePiece(whiteKing, new Position(7, 0));
        assertTrue(board.getPiece(5, 0).equals(whiteKingSideRook));
    }


    @Test
    public void castlingMovesKingToRightPositionBlackQueenSide() {
        board.movePiece(blackKing, new Position(0, 7));
        assertTrue(board.getPiece(2, 7).equals(blackKing));
    }

    @Test
    public void castlingMovesRookToRightPositionBlackQueenSide() {
        board.movePiece(blackKing, new Position(0, 7));
        assertTrue(board.getPiece(3, 7).equals(blackQueenSideRook));
    }

    @Test
    public void castlingMovesKingToRightPositionBlackKingSide() {
        board.movePiece(blackKing, new Position(7, 7));
        assertTrue(board.getPiece(6, 7).equals(blackKing));
    }

    @Test
    public void castlingMovesRookToRightPositionBlackKingSide() {
        board.movePiece(blackKing, new Position(7, 7));
        assertTrue(board.getPiece(5, 7).equals(blackKingSideRook));
    }

    @Test
    public void afterCastlingKingHasMoved() {
        board.movePiece(whiteKing, new Position(0, 0));
        assertTrue(!whiteKing.isFirstMove());
    }

    @Test
    public void afterCastlingRookHasMoved() {
        board.movePiece(whiteKing, new Position(0, 0));
        assertTrue(!whiteQueenSideRook.isFirstMove());
    }

    @Test
    public void castlingTurnHasChanged() {
        ChessState cs = new ChessState();
        cs.getBoard().getPieces().clear();
        cs.getBoard().getPieces().addAll(board.getPieces());
        Player firstTurn = cs.getCurrentPlayer();
        cs.move(whiteKing,  new Position(0, 0));
        assertTrue(cs.getCurrentPlayer() != firstTurn);
    }

}
