package com.anttilip.chkm8.controller;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;

public class PlayerInputProcessor extends InputAdapter {

    private static final int RESET_BUTTON = Keys.R;
    private static final int UNDO_BUTTON = Keys.U;

    private int lastClickX;
    private int lastClickY;
    private boolean isDragged;
    private boolean resetPressed;
    private boolean undoPressed;

    /**
     * Constructor for custom input processor which handles user input.
     */
    PlayerInputProcessor() {
        lastClickX = -1;
        lastClickY = -1;
        isDragged = false;
        resetPressed = false;
        undoPressed = false;
    }

    Vector2 getLastClickPosition() {
        return new Vector2(lastClickX, lastClickY);
    }

    boolean isDragged() {
        return isDragged;
    }

    /**
     * Check if player pressed the reset button.
     * @return Return true if user pressed return button, otherwise return false.
     */
    boolean isResetPressed() {
        if (resetPressed) {
            resetPressed = false;
            return true;
        }
        return false;
    }

    boolean isUndoPressed() {
        if (undoPressed) {
            undoPressed = false;
            return true;
        }
        return false;
    }

    @Override
    public boolean keyDown(int key) {
        switch (key) {
            case RESET_BUTTON:
                resetPressed = true;
            case UNDO_BUTTON:
                undoPressed = true;

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
