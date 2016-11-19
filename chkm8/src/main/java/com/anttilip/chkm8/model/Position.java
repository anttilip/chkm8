package com.anttilip.chkm8.model;

public class Position {
    private static final String BOARD_LETTERS = "ABCDEFGH";
    private int x;
    private int y;

    
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public boolean onBoard() {
        return (this.x >= 0 && this.x < Board.BOARD_SIZE && this.y >= 0 && this.y < Board.BOARD_SIZE);
    }
    
    public Position add(int x, int y) {
        this.x += x;
        this.y += y;
        return this;
    }

    public Position copy() {
        return new Position(this.x, this.y);
    }

    public static Position add(Position a, Position b) {
        return new Position(a.getX() + b.getX(), a.getY() + b.getY());
    }

    public String toString() {
        return BOARD_LETTERS.charAt(this.x) + Integer.toString(y + 1);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Position)) {
            return false;
        }
        Position other = (Position) o;
        return other.x == this.x && other.y == this.y;
    }

    @Override
    public int hashCode() {
        return 11 * this.x + this.y;
    }

}
