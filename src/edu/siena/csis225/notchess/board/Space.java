package edu.siena.csis225.notchess.board;

import edu.siena.csis225.notchess.board.pieces.Piece;

/**
 * Represents a position on a board.
 * 
 * @author Ross & Sorros
 * @date 04-2023
 */
public class Space {

    public enum SpaceState {
        OPEN, OCCUPIED
    }
    
    private static final int WIDTH = 80;
	private static final int HEIGHT = 80;

    private Piece piece; // add into code
    private SpaceState spaceState;
    private int row;
    private int col;
    private int x;
    private int y;
    

    /**
     * Creates a new position
     *
     * @param row the row of the position 
     * @param col the col of the position
     */
    public Space(int row, int col, int x, int y) {
        this.spaceState = SpaceState.OPEN;
        this.row = row;
        this.col = col;
        this.x = x;
        this.y = y;
        this.piece = null;
    }

    /**
     * Creates a new position
     *
     * @param row the row of the position 
     * @param col the col of the position
     */
    public Space(int row, int col, int x, int y, Piece piece) {
        this.spaceState = SpaceState.OCCUPIED;
        this.row = row;
        this.col = col;
        this.x = x;
        this.y = y;
        this.piece = piece;
    }

    /**
     * Gets the state of the space (Open, Occupied)
     * @return the state of the space
     */
    public SpaceState getSpaceState() {
        return spaceState;
    }

    /**
     * Setter for SpaceState of Space
     * 
     * @param state SpaceState to change on Space
     */
    public void setSpaceState(SpaceState state){
        this.spaceState = state;
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    public Piece getPiece(){
        return piece;
    }

    public void setPiece(Piece newPiece, int row, int col) {
        this.piece = newPiece;
        this.row = row;
        this.col = col;
        this.spaceState = SpaceState.OCCUPIED;
    }

    public void setPiece(Piece newPiece){
        this.piece = newPiece;
        this.spaceState = SpaceState.OCCUPIED;
    }

    public void removePiece() {
        this.piece = null;
        this.spaceState = SpaceState.OPEN;
    }

    public boolean contains(int x, int y) {
        if(x >= this.x && x <= this.x + WIDTH && y >= this.y && y <= this.y + HEIGHT) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Checks if two objects are equal 
     *
     * @param other object to be compared to instance of this 
     */
    @Override
    public boolean equals(Object other){
        if (other instanceof Space){
            Space otherPosition = (Space)other;
            if (otherPosition.getCol() == this.col){
                if (otherPosition.getRow() == this.row) {
                    return true;
                }
            }
        }
        return false;
    }

    public String toString() {
        return "Space: " + this.row + ", " + this.col + " " + this.spaceState + ", Piece: " + this.piece + "Color: " + this.piece.getColor() + "\n";
    }

} 
