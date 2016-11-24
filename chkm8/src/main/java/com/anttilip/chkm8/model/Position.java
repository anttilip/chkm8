package com.anttilip.chkm8.model;

/**
 * Represents one position or square on board.
 */
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

    /**
     * Checks and returns if position is on board.
     * @return Boolean value of position being on board.
     */
    public boolean onBoard() {
        return (this.x >= 0 && this.x < Board.BOARD_SIZE && this.y >= 0 && this.y < Board.BOARD_SIZE);
    }

    /**
     * Takes new coordinates and adds them to current coordinates.
     * @param x x-coordinate that is added to current x coordinate.
     * @param y y-coordinate that is added to current y coordinate.
     * @return
     */
    public Position add(int x, int y) {
        this.x += x;
        this.y += y;
        return this;
    }

    /**
     * Copies current position and returns new Position instance.
     * @return New position instance identical to current one.
     */
    public Position copy() {
        return new Position(this.x, this.y);
    }

    /**
     * Static method that adds two positions together and returns new position.
     *
     * Example:
     * Position.add(new Position(3, 4), new Position(1, 2));
     * returns new Position where x = 4, y = 6
     * @param a
     * @param b
     * @return new Position instance of combined positions
     */
    public static Position add(Position a, Position b) {
        return new Position(a.getX() + b.getX(), a.getY() + b.getY());
    }

    /**
     * Returns String representation of standard chess position, e.g. "B4"
     * @return Position converted to a standard square.
     */
    @Override
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
