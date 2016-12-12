package com.anttilip.chkm8.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import com.anttilip.chkm8.game.Game;
import com.anttilip.chkm8.model.ChessState;
import com.anttilip.chkm8.model.Position;
import com.anttilip.chkm8.model.pieces.Piece;
import com.anttilip.chkm8.view.ChessView;

public class ChessController {

    private static final int POINTER = 0;
    private final Game game;
    private final ChessState state;
    private final PlayerInputProcessor playerInputProcessor;
    private Piece currentlyPressedPiece;

    /**
     * Constructor for main controller class.
     *
     * Player manipulates model through this class.
     * @param state ChessState that wraps the model.
     * @param game Main game object.
     */
    public ChessController(ChessState state, Game game) {
        this.state = state;
        this.game = game;
        this.playerInputProcessor = new PlayerInputProcessor();
        Gdx.input.setInputProcessor(playerInputProcessor);
        this.currentlyPressedPiece = null;
    }

    public Piece getCurrentlyPressedPiece() {
        return currentlyPressedPiece;
    }

    /**
     * Reads user input and moves pieces according to players input.
     */
    public void update() {
        if (playerInputProcessor.isResetPressed()) {
            // Reset game
            game.reset();
            return;
        }
        if (playerInputProcessor.isUndoPressed()) {
            // Undo last move
            game.undo();
            return;
        }
        if (playerInputProcessor.isDragged()) {
            // Left mouse key is down
            Vector2 clickPos = playerInputProcessor.getLastClickPosition();
            Piece dragged = state.getPieceAt(ChessView.screenToBoardPosition(clickPos));
            if (dragged != null && state.getCurrentPlayer() == dragged.getPlayer()) {
                // Piece is being clicked
                this.currentlyPressedPiece = dragged;
            }
        } else if (this.currentlyPressedPiece != null) {
            // Piece was just released
            Position releasePos = ChessView.screenToBoardPosition(playerInputProcessor.getLastClickPosition());
            for (Position pos : state.getGetPiecesAllowedMoves(this.currentlyPressedPiece)) {
                if (pos.equals(releasePos)) {
                    // If piece was released in allowed square, move piece to that square
                    state.move(this.currentlyPressedPiece, pos);
                }
            }
            currentlyPressedPiece = null;
        }
    }

    /**
     * Check if given piece is being dragged by player.
     * @param piece Piece for which dragging is checked.
     * @return Returns boolean value of piece being dragged.
     */
    public boolean pieceIsDragged(Piece piece) {
        return piece.equals(this.currentlyPressedPiece);
    }

    public int getDraggedX() {
        return Gdx.input.getX(POINTER);
    }

    public int getDraggedY() {
        return Gdx.input.getY(POINTER);
    }
}
