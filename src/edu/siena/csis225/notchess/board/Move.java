package edu.siena.csis225.notchess.board;

/**
 * Used to make an object of a Move made on the ChessBoard
 * 
 * @author Ross & Sorros
 * @date 04-2023
 */
public class Move {
    private Space start;
    private Space end;
    private Space pieceJumped;
    

    /*
     * Constructs Move for a piece
     */
    public Move (Space start, Space end) {
        this.start = start;
        this.end = end;
        this.pieceJumped = null;
    }

    /**
     * Get the starting position of a piece
     * @return start position
     */
    public Space getStart() {
        return start;
    }

    /**
     * Get the ending position of a piece
     * @return end position
     */
    public Space getEnd() {
        return end;
    }

    /**
     * Get the position of the piece that was jumped (if no jumped piece, returns null)
     * @return jumped piece
     */
    public Space getPieceJumped() {
        return pieceJumped;
    }
    
    /**
     * Set the jumped piece of the move
     * @param jumpedPiece The position of the piece that is to be jumped
     */
    public void setJumped (Space jumpedPiece){
        pieceJumped = jumpedPiece;
    }

    /**
     * Checks if two objects are equal
     *
     * @param obj object to be compared to instance of this
     */
    @Override
    public boolean equals(Object obj){
        return obj instanceof Move && ((Move) obj).start == this.start && ((Move) obj).end == this.end;
    }

}
