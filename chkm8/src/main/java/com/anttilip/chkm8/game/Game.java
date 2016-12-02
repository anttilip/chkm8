package com.anttilip.chkm8.game;

import com.anttilip.chkm8.controller.ChessController;
import com.anttilip.chkm8.model.ChessState;
import com.anttilip.chkm8.model.GameState;
import com.anttilip.chkm8.view.ChessView;
import com.badlogic.gdx.ApplicationListener;

public class Game implements ApplicationListener {

    public static final String TITLE = "chkm8";
    public static final int WIDTH = 512;
    public static final int HEIGHT = 512;

    private ChessState state;
    private ChessView view;
    private ChessController controller;

    @Override
    public void create() {
        state = new ChessState();
        controller = new ChessController(state);
        view = new ChessView(state, controller);

    }
    @Override
    public void render() {
        if (state.getGameStates().contains(GameState.INCOMPLETE)) {
            this.controller.update();
            this.view.render();
        } else {
            System.out.println("Game over");
            // Display game over screen
        }
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
