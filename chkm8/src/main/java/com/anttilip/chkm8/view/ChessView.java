package com.anttilip.chkm8.view;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import com.anttilip.chkm8.controller.ChessController;
import com.anttilip.chkm8.model.Player;
import com.anttilip.chkm8.model.Position;
import com.anttilip.chkm8.model.pieces.*;
import com.anttilip.chkm8.model.Board;
import com.anttilip.chkm8.model.ChessState;
import com.badlogic.gdx.math.Vector2;

public class ChessView {

    private static final int BOARD_SIZE = 512;
    private static final int PIECE_SIZE = 64;

    private final ChessState state;
    private final ChessController controller;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private Map<Class, Texture> whiteTextures;
    private Map<Class, Texture> blackTextures;
    private Texture board;

    public ChessView(ChessState state, ChessController controller) {
        this.state = state;
        this.controller = controller;
    }

    private void drawPieces(Board board) {
        // Draw pieces
        for (Piece piece : board.getPieces()) {
            if (piece.getPlayer() == Player.WHITE) {
                // Draw white pieces
                if (controller.pieceIsDragged(piece)) {
                    // If piece is being dragged, it should move with mouse
                    float x = controller.getDraggedX() - PIECE_SIZE / 2;
                    float y = screenYtoGameY(controller.getDraggedY()) - PIECE_SIZE / 2;
                    batch.draw(whiteTextures.get(piece.getClass()), x, y);
                } else {
                    float x = piece.getPosition().getX() * PIECE_SIZE;
                    float y = piece.getPosition().getY() * PIECE_SIZE;
                    batch.draw(whiteTextures.get(piece.getClass()), x, y);
                }
            } else {
            // Draw black pieces
                if (controller.pieceIsDragged(piece)) {
                    // If piece is being dragged, it should move with mouse
                    float x = controller.getDraggedX() - PIECE_SIZE / 2;
                    float y = screenYtoGameY(controller.getDraggedY()) - PIECE_SIZE / 2;
                    batch.draw(blackTextures.get(piece.getClass()), x, y);
                } else {
                    float x = piece.getPosition().getX() * PIECE_SIZE;
                    float y = piece.getPosition().getY() * PIECE_SIZE;
                    batch.draw(blackTextures.get(piece.getClass()), x, y);
                }
            }
        }
    }

    private void drawAllowedMoves() {
        if (controller.getCurrentlyPressedPiece() != null) {
            Piece draggedPiece = controller.getCurrentlyPressedPiece();
            for (Position pos : this.state.getBoard().getAllowedMoves(draggedPiece)) {
                shapeRenderer.setColor(0, 0, 1, 0.5f);
                shapeRenderer.rect(pos.getX() * PIECE_SIZE, pos.getY() * PIECE_SIZE, PIECE_SIZE, PIECE_SIZE);
            }
        }
    }

    private void drawPiecesOwnSquare() {
        if (controller.getCurrentlyPressedPiece() != null) {
            Piece dragged = this.controller.getCurrentlyPressedPiece();
            shapeRenderer.setColor(0, 0.3f, 0, 0.5f);
            shapeRenderer.rect(dragged.getPosition().getX() * PIECE_SIZE, dragged.getPosition().getY() * PIECE_SIZE, PIECE_SIZE, PIECE_SIZE);
        }
    }

    private void drawCheckedSquare() {
        for (Player player : Player.values()) {
            if (state.getBoard().isPlayerChecked(player)) {
                Position check = state.getBoard().getKing(player).getPosition();
                shapeRenderer.setColor(0.6f, 0, 0, 0.5f);
                shapeRenderer.rect(check.getX() * PIECE_SIZE, check.getY() * PIECE_SIZE, PIECE_SIZE, PIECE_SIZE);
            }
        }
    }

    public void create() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        board = new Texture(Gdx.files.internal("Board.png"));
        whiteTextures = new HashMap<>();
        blackTextures = new HashMap<>();
        linkTextures();
    }

    public void render() {
        // Draw board and pieces
        batch.begin();
        batch.draw(this.board, 0, 0);
        drawPieces(state.getBoard());
        batch.end();

        // Draw visual aids for game
        // Set gl settings for translucent squares
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        // Draw translucent squares
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        drawAllowedMoves();
        drawPiecesOwnSquare();
        drawCheckedSquare();
        shapeRenderer.end();

        // Clear gl settings for next draw
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    private void linkTextures() {
        whiteTextures.put(Pawn.class, new Texture(Gdx.files.internal("white_pawn.png")));
        whiteTextures.put(Rook.class, new Texture(Gdx.files.internal("white_rook.png")));
        whiteTextures.put(Knight.class, new Texture(Gdx.files.internal("white_knight.png")));
        whiteTextures.put(Bishop.class, new Texture(Gdx.files.internal("white_bishop.png")));
        whiteTextures.put(King.class, new Texture(Gdx.files.internal("white_king.png")));
        whiteTextures.put(Queen.class, new Texture(Gdx.files.internal("white_queen.png")));

        blackTextures.put(Pawn.class, new Texture(Gdx.files.internal("black_pawn.png")));
        blackTextures.put(Rook.class, new Texture(Gdx.files.internal("black_rook.png")));
        blackTextures.put(Knight.class, new Texture(Gdx.files.internal("black_knight.png")));
        blackTextures.put(Bishop.class, new Texture(Gdx.files.internal("black_bishop.png")));
        blackTextures.put(King.class, new Texture(Gdx.files.internal("black_king.png")));
        blackTextures.put(Queen.class, new Texture(Gdx.files.internal("black_queen.png")));
    }
       
    
    public static int screenYtoGameY(int y) {
        return BOARD_SIZE - y;
    }
    
    public static Position screenToBoardPosition(Vector2 screen) {
        int x = (int) screen.x / PIECE_SIZE;
        int y = (int) (BOARD_SIZE - screen.y) / PIECE_SIZE;
        return new Position(x, y);
    }
}
