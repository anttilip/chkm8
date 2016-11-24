/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anttilip.chkm8.model;

/**
 * Enum class representing different game states, e.g. check and stalemate.
 */
public enum GameState {
    CHECK,
    CHECKMATE,
    STALEMATE,
    REPETITION3,
    REPETITION5,
    INSUFFICIENT,
    MOVE50,
    MOVE75,
    INCOMPLETE;

    public static final GameState[] GAME_ENDING_STATES = {
        CHECKMATE, STALEMATE, REPETITION5, 
        INSUFFICIENT, MOVE75
    };

}
