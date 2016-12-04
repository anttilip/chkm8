package com.anttilip.chkm8.model;

import com.anttilip.chkm8.model.pieces.Queen;
import com.anttilip.chkm8.model.pieces.Bishop;
import com.anttilip.chkm8.model.pieces.Rook;
import com.anttilip.chkm8.model.pieces.Piece;
import com.anttilip.chkm8.model.pieces.Pawn;
import com.anttilip.chkm8.model.pieces.King;
import com.anttilip.chkm8.model.pieces.Knight;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *  Represents the chess board.
 */

public class Board {
    public static final int BOARD_SIZE = 8;
    private final List<Piece> pieces;
    private Position enPassantPosition;

    /**
     * Constructs a new board with given pieces.
     * @param pieces Board is initialized with given pieces
     */
    public Board(List<Piece> pieces) {
        this.pieces = pieces;
        this.enPassantPosition = null;
    }

    /**
     * Moves given piece to a given position.
     * @param piece Piece that is moved
     * @param target Position that piece is moved to
     */
    public void movePiece(Piece piece, Position target) {
        // Remove en passant since no one attacked it this turn
        if (!target.equals(this.enPassantPosition) || !(piece instanceof Pawn)) {
            enPassantPosition = null;
        }
        // Move piece to its new position
        piece.move(target, this);
    }

    /**
     * Gets pieces' allowed moves.
     *
     * Fetches all possible moves from piece and filters out moves which would lead to own king being in check
     *
     * @param piece Piece for which allowed moves are calculated
     * @return List of positions where piece is allowed to move
     */
    public List<Position> getAllowedMoves(Piece piece) {
        // Get all possible moves that piece can make and filter out
        // moves that would lead to own king being checked
        List<Position> allowedMoves = new ArrayList<>();
        for (Position move : piece.getPossibleMoves(this)) {
            Board afterMove = this.copy();
            Piece copy = afterMove.getPiece(piece.getPosition());
            afterMove.movePiece(copy, move);
            if (!afterMove.isPlayerChecked(copy.getPlayer())) {
                // Move does not lead to own king being in check so move is allowed
                allowedMoves.add(move);
            }
        }

        return allowedMoves;
    }

    /**
     * Searches and returns piece at given position.
     * @param position Position where piece is searched
     * @return Piece in the given position, if position does not contain a piece, null is returned
     */
    public Piece getPiece(Position position) {
        for (Piece piece : this.pieces) {
            if (piece.getPosition().equals(position)) {
                return piece;
            }
        }
        return null;
    }

    /**
     * Searches and returns piece at given position.
     * @param x x-coordinate in board
     * @param y y-coordinate in board
     * @return returns piece in given coordinates or null if position is not occupied
     */
    public Piece getPiece(int x, int y) {
        return getPiece(new Position(x, y));
    }

    /**
     * Returns all pieces on board.
     * @return Returns all pieces on board.
     */
    public List<Piece> getPieces() {
        return this.pieces;
    }

    /**
     * Searches and returns list of given players pieces.
     * @param player Player whose pieces are returned
     * @return List of players pieces
     */
    public List<Piece> getPieces(Player player) {
        List<Piece> playersPieces = new ArrayList<>();
        for (Piece piece : this.pieces) {
            if (piece.getPlayer() == player) {
                playersPieces.add(piece);
            }
        }
        return playersPieces;
    }

    /**
     * Searches and returns players king.
     * @param player Player whose king is returned
     * @return Players king
     */
    public King getKing(Player player) {
        for (Piece piece : this.pieces) {
            if (piece instanceof King && piece.getPlayer() == player) {
                return (King) piece;
            }
        }
        return null;
    }

    /**
     * Searches and returns all players rooks as a List.
     * @param player Player whose rooks are returned
     * @return List of players rooks
     */
    public List<Rook> getRooks(Player player) {
        List<Rook> rooks = new ArrayList<>();
        for (Piece rook : this.getPieces(player)) {
            if (rook instanceof Rook) {
                rooks.add((Rook) rook);
            }
        }
        return rooks;
    }

    /**
     * If board has a en passant position for a pawn, en passant position is returned.
     * @return En passant position
     */
    public Position getEnPassantPosition() {
        return this.enPassantPosition;
    }

    /**
     * Sets a new en passant position.
     * @param enPassantPosition Position to which en passant is set
     */
    public void setEnPassantPosition(Position enPassantPosition) {
        this.enPassantPosition = enPassantPosition;
    }

    /**
     * Checks if position contains a piece.
     * @param position Position that is checked for a piece
     * @return boolean value of position containing a piece
     */
    public boolean isOccupied(Position position) {
        return getPiece(position) != null;
    }

    /**
     * Checks if players king is in check.
     *
     * Positions that can threaten players king and checks if position
     * contains a enemy piece that threatens players king.
     *
     * @param player Player who might be checked.
     * @return boolean value of player being in check.
     */
    public boolean isPlayerChecked(Player player) {
        King king = this.getKing(player);

        // Check if pawns threaten king
        int enemyPawnDirection = (player == Player.WHITE) ? 1 : -1;
        Piece right = this.getPiece(Position.add(king.getPosition(), new Position(1, enemyPawnDirection)));
        Piece left = this.getPiece(Position.add(king.getPosition(), new Position(-1, enemyPawnDirection)));

        if (right instanceof Pawn && right.getPlayer() != player) {
            // Pawn on right threatens the king
            return true;
        }
        if (left instanceof Pawn && left.getPlayer() != player) {
            // Pawn on left threatens the king
            return true;
        }

        // Check if a knight threaten king
        for (Position direction : Knight.MOVE_DIRECTIONS) {
            Piece enemy = this.getPiece(Position.add(king.getPosition(), direction));
            if (enemy instanceof Knight && enemy.getPlayer() == Player.getOther(player)) {
                return true;
            }
        }

        // Scan from king to check if other pieces threaten the king
        for (Position direction : King.MOVE_DIRECTIONS) {
            boolean moreThanOneSquareAway = false;
            Position square = Position.add(king.getPosition(), direction);
            while (square.onBoard()) {
                if (this.getPiece(square) != null) {
                    if (Player.getOther(player) == this.getPiece(square).getPlayer()) {
                        Piece enemyPiece = this.getPiece(square);
                        if (moreThanOneSquareAway && !enemyPiece.canMoveMoreThanOnce()) {
                            // Only check for rooks, bishops and queens
                            break;
                        }
                        if (Arrays.asList(enemyPiece.getMoveDirections()).contains(direction)) {
                            // King is in check if enemy piece can move to the same direction as king
                            return true;
                        }
                    }
                    // If scan finds own piece, king can't be threatened from that direction
                    break;
                }
                square = Position.add(square, direction);
                moreThanOneSquareAway = true;
            }
        }
        return false;
    }

    /**
     * Checks if given king and rook are allowed to castle.
     *
     * Checks if king is in check, important squares are not threatened and that no piece blocks the castling.
     * @param king King for which castling is calculated
     * @param rook Rook for which castling is calculated
     * @return Returns boolean value of castling being allowed for given pieces
     */
    public boolean isCastlingAllowed(King king, Rook rook) {
        // If king or rook has already moved, castling is not possible
        if (!king.isFirstMove() || !rook.isFirstMove()) {
            return false;
        }

        int direction = rook.getPosition().getX() - king.getPosition().getX();
        direction = (direction < 0) ? -1 : 1;
        Position square = king.getPosition().copy();
        int moves = 0;
        while (!square.equals(rook.getPosition())) {
            Piece pieceInSquare = this.getPiece(square);
            // Path to castling can't be blocked by other pieces
            if (this.getPiece(square) != null && (!pieceInSquare.equals(king) && !pieceInSquare.equals(rook))) {
                return false;
            }

            // Check if square is threatened by simulating king moving into that position
            // King only moves 2 places, so no need to check if other squares are threatened
            if (moves <= 2) {
                Board boardCopy = this.copy();
                Piece kingCopy = boardCopy.getPiece(king.getPosition());
                if (!square.equals(king.getPosition())) {
                    boardCopy.movePiece(kingCopy, square);
                }
                if (boardCopy.isPlayerChecked(king.getPlayer())) {
                    // If king would be in check after the move, square is threatened
                    return false;
                }
            }
            // Check next square toward the rook
            square.add(direction, 0);
            moves++;
        }

        return true;
    }

    /**
     * Creates standard board.
     *
     * Creates all pieces in standard positions for both players
     * @return New board object initialized with default pieces
     */
    static Board createBoard() {
        List<Piece> pieces = new ArrayList<>();

        for (Player player : Player.values()) {
            // Create pawns
            for (int x = 0; x < BOARD_SIZE; x++) {
                pieces.add(new Pawn(new Position(x, 1 + (BOARD_SIZE - 3) * player.getValue()), player));
            }

            // Create Rooks
            pieces.add(new Rook(new Position(0, 7 * player.getValue()), player));
            pieces.add(new Rook(new Position(BOARD_SIZE - 1, (BOARD_SIZE - 1) * player.getValue()), player));

            // Create Knights
            pieces.add(new Knight(new Position(1, 7 * player.getValue()), player));
            pieces.add(new Knight(new Position(BOARD_SIZE - 2, (BOARD_SIZE - 1) * player.getValue()), player));

            // Create Bishops
            pieces.add(new Bishop(new Position(2, 7 * player.getValue()), player));
            pieces.add(new Bishop(new Position(BOARD_SIZE - 3, (BOARD_SIZE - 1) * player.getValue()), player));

            // Create Queen
            pieces.add(new Queen(new Position(3, (BOARD_SIZE - 1) * player.getValue()), player));

            // Create King
            pieces.add(new King(new Position(4, (BOARD_SIZE - 1) * player.getValue()), player));

        }

        return new Board(pieces);
    }

    /**
     * Copies current instance of Board.
     * @return Returns new identical instance of Board.
     */
    public Board copy() {
        List<Piece> piecesCopy = new ArrayList<>();
        for (Piece orig : this.pieces) {
            piecesCopy.add(orig.copy());
        }
        Board copy = new Board(piecesCopy);
        copy.enPassantPosition = this.enPassantPosition;
        return copy;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        for (Piece piece : this.pieces) {
            hash = 7 * hash + piece.hashCode();
        }
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Board)) {
            return false;
        }
        Board other = (Board) o;
        for (Piece piece : other.getPieces()) {
            if (!this.pieces.contains(piece)) {
                return false;
            }
        }
        return other.getPieces().size() == this.pieces.size();
    }
}
