/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anttilip.chkm8.model;


/**
 *
 * @author antti
 */
public enum Player {
    WHITE (0),
    BLACK (1);
    
    private final int value;
    
    Player(int value) {
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }
}
