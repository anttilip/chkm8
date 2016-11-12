package com.anttilip.chkm8.model;

public class Position {
    private int x;
    private int y;
    
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    public void set(int x, int y) {
        setX(x);
        setY(y);
    }
    
    public void set(Position position) {
        setX(position.x);
        setY(position.y);
    }
    
    public boolean onBoard() {
        return (this.x >= 0 && this.x < 8 && this.y >= 0 && this.y < 8);
    }
    
    public String toString() {
        String alphabets = "ABCDEFGH";
        return alphabets.charAt(this.x) + Integer.toString(y + 1);
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
        return 10 * this.x + this.y;
    }
    
    public static Position add(Position a, Position b) {
        return new Position(a.getX() + b.getX(), a.getY() + b.getY());
    }

}
