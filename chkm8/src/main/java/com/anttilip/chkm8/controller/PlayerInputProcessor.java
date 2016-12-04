package com.anttilip.chkm8.controller;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;

public class PlayerInputProcessor extends InputAdapter {

    private static final int RESET_BUTTON = Keys.R;

    private int lastClickX;
    private int lastClickY;
    private boolean isDragged;
    private boolean resetPressed;

    /**
     * Constructor for custom input processor which handles user input.
     */
    public PlayerInputProcessor() {
        lastClickX = -1;
        lastClickY = -1;
        isDragged = false;
        resetPressed = false;

    }

    public Vector2 getLastClickPosition() {
        return new Vector2(lastClickX, lastClickY);
    }

    public boolean isDragged() {
        return isDragged;
    }

    /**
     * Check if player pressed the reset button.
     * @return Return true if user pressed return button, otherwise return false.
     */
    public boolean isResetPressed() {
        if (resetPressed) {
            resetPressed = false;
            return true;
        }
        return false;
    }

    @Override
    public boolean keyDown(int key) {
        if (key == RESET_BUTTON) {
            resetPressed = true;
        }
        return false;
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            lastClickX = x;
            lastClickY = y;
            isDragged = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            lastClickX = x;
            lastClickY = y;
            isDragged = false;
            return true;
        }
        return false;
    }
}
