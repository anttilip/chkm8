package com.anttilip.chkm8.model;

import java.util.HashMap;
import java.util.List;

public abstract class Piece {
    protected Position position;
    protected final Player player;
    
    public Piece(Position position, Player player) {
        this.player = player;
        this.position = position;
    }
    
    public abstract List<Position> getAllowedMoves(HashMap<Position, Piece> occupiedPositions);

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    @Override
    public String toString() {
        return this.player + " " + this.getClass().getSimpleName();
    }
       
}
