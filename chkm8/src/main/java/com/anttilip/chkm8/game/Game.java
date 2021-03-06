package com.anttilip.chkm8.game;

import com.badlogic.gdx.ApplicationListener;

import com.anttilip.chkm8.controller.ChessController;
import com.anttilip.chkm8.model.ChessState;
import com.anttilip.chkm8.view.ChessView;

public class Game implements ApplicationListener {

    static final String TITLE = "chkm8";
    static final int WIDTH = 512;
    static final int HEIGHT = 512;

    private ChessState state;
    private ChessView view;
    private ChessController controller;

    @Override
    public void create() {
        state = new ChessState();
        controller = new ChessController(state, this);
        view = new ChessView(state, controller);

    }
    @Override
    public void render() {
        this.controller.update();
        this.view.draw();
    }

    /**
     * Resets the game to initialized state.
     */
    public void reset() {
        create();
    }

    /**
     * Undoes last move and resets view and controller.
     */
    public void undo() {
        state.undoLastMove();
        controller = new ChessController(state, this);
        view = new ChessView(state, controller);
    }

    @Override
    public void dispose() {
    }
    
    @Override
    public void resize(int w, int h) {
    }
    
    @Override
    public void pause() {
    }
    
    @Override
    public void resume() {
    }

}
