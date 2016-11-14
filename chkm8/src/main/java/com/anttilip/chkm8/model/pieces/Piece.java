package com.anttilip.chkm8.model.pieces;

import com.anttilip.chkm8.model.Board;
import com.anttilip.chkm8.model.Player;
import com.anttilip.chkm8.model.Position;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Piece {

    protected Position position;
    protected final Player player;

    public Piece(Position position, Player player) {
        this.player = player;
        this.position = position;
    }

    public abstract List<Position> getAllowedMoves(Board board, boolean selfCheckAllowed);
    
    public boolean moveLeadsToSelfCheck(Position target, Board board) {
        Board boardCopy = board.copy();
        boardCopy.movePiece(boardCopy.getPiece(this.position), target);
        return boardCopy.isCheck(this.player);
    }

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
    
    public abstract Piece copy();
}
