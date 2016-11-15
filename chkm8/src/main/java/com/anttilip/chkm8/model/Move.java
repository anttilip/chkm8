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

    private final Piece piece;
    private final Position target;
    private final Position origin;
    private final Board board;

    public Move(Piece piece, Position origin, Position target, Board board) {
        this.piece = piece;
        this.origin = origin;
        this.target = target;
        this.board = board;
    }

    public Piece getPiece() {
        return this.piece;
    }

    public Position getTarget() {
        return this.target;
    }

    public Position getOrigin() {
        return this.origin;
    }

    public int getBoardHash() {
        return this.board.hashCode();
    }
    
    public Board getBoard() {
        return this.board;
    }

    @Override
    public String toString() {
        return piece.toString() + " from " + origin.toString() + " to " + target.toString();
    }

}
