package com.anttilip.chkm8.controller;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

public class PlayerInputProcessor implements InputProcessor {

    private int lastClickX;
    private int lastClickY;
    private boolean isDragged;

    public PlayerInputProcessor() {
        lastClickX = -1;
        lastClickY = -1;
        isDragged = false;

    }

    public int getLastClickX() {
        return lastClickX;
    }

    public int getLastClickY() {
        return lastClickY;
    }
    
    public Vector2 getLastClickPosition() {
        return new Vector2(lastClickX, lastClickY);
    }

    public boolean isDragged() {
        return isDragged;
    }

    @Override
    public boolean keyDown(int i) {
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
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

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(int i) {
        return false;
    }
}
