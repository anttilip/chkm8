package com.anttilip.chkm8.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Piece {
    protected Position position;
    protected final Player player;
    protected List<Position> possibleMoves;
    
    public Piece(Position position, Player player) {
        this.player = player;
        this.position = position;
        this.possibleMoves = new ArrayList();
    }
    
    abstract void updatePossibleMoves(HashMap<Position, Player> occupiedPositions);

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    public boolean isMoveAllowed(Position target) {
        return this.possibleMoves.contains(target);
    }
    
    public String toString() {
        return this.player + ", " + this.getClass().getSimpleName() + ": " + this.position.toString();
    }
       
}
