/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anttilip.chkm8.model;

import com.anttilip.chkm8.model.pieces.Piece;

/**
 *
 * @author antti
 */
public class Move {
    private Piece piece;
    private Position target;
    private Position origin;

    public Move(Piece piece, Position origin, Position target) {
        this.piece = piece;
        this.origin = origin;
        this.target = target;
    }
    
    public Move(Piece piece, Position target) {
        this(piece, piece.getPosition(), target);
    }

    public Piece getPiece() {
        return piece;
    }

    public Position getTarget() {
        return target;
    }

    @Override
    public String toString() {
        return piece.toString() + " from " + origin.toString() + " to " + target.toString();
    }
    
}
