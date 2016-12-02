package com.anttilip.chkm8.game;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class ChessDesktop {

    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = Game.TITLE;
        config.width = Game.WIDTH;
        config.height = Game.HEIGHT;
        config.resizable = false;
        new LwjglApplication(new Game(), config);
    }
}
