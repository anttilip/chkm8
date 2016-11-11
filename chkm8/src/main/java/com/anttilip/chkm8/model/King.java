/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anttilip.chkm8.model;

import java.util.HashMap;

/**
 *
 * @author antti
 */
public class King extends Piece {

    public King(Position position, Player player) {
        super(position, player);
    }


    @Override
    void updatePossibleMoves(HashMap<Position, Player> occupiedPositions) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
