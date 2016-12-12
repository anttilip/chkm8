package com.anttilip.chkm8.view;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import com.anttilip.chkm8.controller.ChessController;
import com.anttilip.chkm8.model.Player;
import com.anttilip.chkm8.model.Position;
import com.anttilip.chkm8.model.pieces.*;
import com.anttilip.chkm8.model.Board;
import com.anttilip.chkm8.model.ChessState;

public class ChessView {

    private static final int BOARD_SIZE = 512;
    private static final int PIECE_SIZE = 64;

    private final ChessState state;
    private final ChessController controller;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private Map<Class, Texture> whiteTextures;
    private Map<Class, Texture> blackTextures;
    private Texture boardTexture;

    /**
     * Constructor for view class that displays the model.
     *
     * Creates all needed objects for drawing the board.
     *
     * @param state ChessState that represents the model.
     * @param controller ChessController which manipulates the model.
     */
    public ChessView(ChessState state, ChessController controller) {
        this.state = state;
        this.controller = controller;
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        boardTexture = new Texture(Gdx.files.internal("Board.png"));
        whiteTextures = new HashMap<>();
        blackTextures = new HashMap<>();
        linkTextures();
    }

    private void drawPieces(Board board) {
        // Draw pieces which are not dragged
        for (Piece piece : board.getPieces()) {
            if (controller.pieceIsDragged(piece)) {
                // If piece is dragged, don't draw it yet
                continue;
            }
            float x = piece.getPosition().getX() * PIECE_SIZE;
            float y = piece.getPosition().getY() * PIECE_SIZE;
            if (piece.getPlayer() == Player.WHITE) {
                // White texture
                batch.draw(whiteTextures.get(piece.getClass()), x, y);
            } else {
                // Black texture
                batch.draw(blackTextures.get(piece.getClass()), x, y);
            }
        }

        // Dragged piece should be drawn last so it can't get behind any still piece.
        for (Piece piece : board.getPieces()) {
            if (controller.pieceIsDragged(piece)) {
                float x = controller.getDraggedX() - PIECE_SIZE / 2;
                float y = screenYtoGameY(controller.getDraggedY()) - PIECE_SIZE / 2;
                if (piece.getPlayer() == Player.WHITE) {
                    // White textures
                    batch.draw(whiteTextures.get(piece.getClass()), x, y);
                } else {
                    // Black textures
                    batch.draw(blackTextures.get(piece.getClass()), x, y);
                }
            }
        }
    }

    private void drawAllowedMoves() {
        if (controller.getCurrentlyPressedPiece() != null) {
            Piece draggedPiece = controller.getCurrentlyPressedPiece();
            for (Position pos : this.state.getBoard().getAllowedMoves(draggedPiece)) {
                shapeRenderer.setColor(0, 0, 1, 0.45f);
                shapeRenderer.rect(pos.getX() * PIECE_SIZE, pos.getY() * PIECE_SIZE, PIECE_SIZE, PIECE_SIZE);
            }
        }
    }

    private void drawPiecesOwnSquare() {
        if (controller.getCurrentlyPressedPiece() != null) {
            Piece dragged = this.controller.getCurrentlyPressedPiece();
            shapeRenderer.setColor(0, 0.3f, 0, 0.45f);
            shapeRenderer.rect(dragged.getPosition().getX() * PIECE_SIZE, dragged.getPosition().getY() * PIECE_SIZE, PIECE_SIZE, PIECE_SIZE);
        }
    }

    private void drawCheckedSquare() {
        for (Player player : Player.values()) {
            if (state.getBoard().isPlayerChecked(player)) {
                Position check = state.getBoard().getKing(player).getPosition();
                shapeRenderer.setColor(0.6f, 0, 0, 0.45f);
                shapeRenderer.rect(check.getX() * PIECE_SIZE, check.getY() * PIECE_SIZE, PIECE_SIZE, PIECE_SIZE);
            }
        }
    }

    /**
     * Draws the board.
     */
    public void draw() {
        // Draw board to the bottom
        batch.begin();
        batch.draw(this.boardTexture, 0, 0);
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

        // Draw pieces on top of everything
        batch.begin();
        drawPieces(state.getBoard());
        batch.end();

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

    /**
     * Converts screen y-coordinate, e.g. from controller to game coordinate.
     *
     * Origin for input is top left corner but for drawing it is bottom left
     * corner.
     *
     * @param y y-coordinate which is converted.
     * @return Converted y-coordinate.
     */
    private static int screenYtoGameY(int y) {
        return BOARD_SIZE - y;
    }

    /**
     * Converts position from input to square on board.
     *
     * @param screen Vector2 position from input.
     * @return Returns corresponding board square position.
     */
    public static Position screenToBoardPosition(Vector2 screen) {
        int x = (int) screen.x / PIECE_SIZE;
        int y = (int) (BOARD_SIZE - screen.y) / PIECE_SIZE;
        return new Position(x, y);
    }
}
