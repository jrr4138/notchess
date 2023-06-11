package edu.siena.csis225.notchess.board.pieces;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;

import edu.siena.csis225.notchess.notchess;
import edu.siena.csis225.notchess.board.Move;
import edu.siena.csis225.notchess.board.Space;
import edu.siena.csis225.notchess.board.Type;
import edu.siena.csis225.notchess.board.Space.SpaceState;

/**
 * Creates a Knight piece
 * 
 * @author Ross & Sorros
 * @date 04-2023
 */
public class Knight extends Piece {

    private Type type;
    private Color color;
    private int row;
    private int col;
    private File image;

    public Knight(int row, int col, Type type, Color color, File image) {
        super(row, col, type, color, image);
    }

    @Override
    public ArrayList<Move> getJumpMoves(Space startSpace, notchess game) {
        ArrayList<Move> moves = new ArrayList<Move>();
        Space endSpace;
        int row = startSpace.getRow();
        int col = startSpace.getCol();
        
        //check all valid moves for a knight to see if there is a jump move
        //check up 2 and right 1
        if(row < 6 && col < 7){
            endSpace = game.getSpace(row + 2, col + 1);
            if(endSpace.getSpaceState() == SpaceState.OCCUPIED){
                if(endSpace.getPiece().getColor() != startSpace.getPiece().getColor()){
                    moves.add(new Move(startSpace, endSpace));
                }
            } 
        }

        //check up 2 and left 1
        if(row < 6 && col > 0){
            endSpace = game.getSpace(row + 2, col - 1);
            if(endSpace.getSpaceState() == SpaceState.OCCUPIED){
                if(endSpace.getPiece().getColor() != startSpace.getPiece().getColor()){
                    moves.add(new Move(startSpace, endSpace));
                }
            } 
        }

        //check down 2 and right 1
        if(row > 1 && col < 7){
            endSpace = game.getSpace(row - 2, col + 1);
            if(endSpace.getSpaceState() == SpaceState.OCCUPIED){
                if(endSpace.getPiece().getColor() != startSpace.getPiece().getColor()){
                    moves.add(new Move(startSpace, endSpace));
                }
            } 
        }

        //check down 2 and left 1
        if(row > 1 && col > 0){
            endSpace = game.getSpace(row - 2, col - 1);
            if(endSpace.getSpaceState() == SpaceState.OCCUPIED){
                if(endSpace.getPiece().getColor() != startSpace.getPiece().getColor()){
                    moves.add(new Move(startSpace, endSpace));
                }
            } 
        }

        //check up 1 and right 2
        if(row < 7 && col < 6){
            endSpace = game.getSpace(row + 1, col + 2);
            if(endSpace.getSpaceState() == SpaceState.OCCUPIED){
                if(endSpace.getPiece().getColor() != startSpace.getPiece().getColor()){
                    moves.add(new Move(startSpace, endSpace));
                }
            } 
        }

        //check up 1 and left 2
        if(row < 7 && col > 1){
            endSpace = game.getSpace(row + 1, col - 2);
            if(endSpace.getSpaceState() == SpaceState.OCCUPIED){
                if(endSpace.getPiece().getColor() != startSpace.getPiece().getColor()){
                    moves.add(new Move(startSpace, endSpace));
                }
            } 
        }

        //check down 1 and right 2
        if(row > 0 && col < 6){
            endSpace = game.getSpace(row - 1, col + 2);
            if(endSpace.getSpaceState() == SpaceState.OCCUPIED){
                if(endSpace.getPiece().getColor() != startSpace.getPiece().getColor()){
                    moves.add(new Move(startSpace, endSpace));
                }
            } 
        }

        //check down 1 and left 2
        if(row > 0 && col > 1){
            endSpace = game.getSpace(row - 1, col - 2);
            if(endSpace.getSpaceState() == SpaceState.OCCUPIED){
                if(endSpace.getPiece().getColor() != startSpace.getPiece().getColor()){
                    moves.add(new Move(startSpace, endSpace));
                }
            } 
        }

        return moves;
    }
    public char getLetter(int i) {
		// will return letter of board column
		char letter = (char) ((char) i + 65);

		// used for X-Axis of Board
		if (i > 10) {
			letter = (char) ((char) (i / 80) + 64);
		}

		return letter;
	}
    @Override
    public boolean isValidMove(Move move, notchess game) {
        Space start = move.getStart();
        Space end = move.getEnd();

        // if end has a piece, check if piece is same color as start piece
        if (end.getSpaceState() == SpaceState.OCCUPIED) {
            if (start.getPiece() != null) {
                if (end.getPiece().getColor() == start.getPiece().getColor()) {
                    return false;
                }
            }
        }

        // check if the knight is moving to a space that is two spaces away and one
        // space away from the current space
        if (Math.abs(start.getRow() - end.getRow()) == 2 && Math.abs(end.getCol() - start.getCol()) == 1) {
            ArrayList<Move> moves = getJumpMoves(start, game);
            if (moves.isEmpty()) {
                return true;
            } else {
                for (Move m : moves) {
                    if (m.getEnd().equals(end)) {
                        return true;
                    }
                }
                return false;
            }
        } else if (Math.abs(start.getRow() - end.getRow()) == 1 && Math.abs(end.getCol() - start.getCol()) == 2) {
            ArrayList<Move> moves = getJumpMoves(start, game);
            if (moves.isEmpty()) {
                return true;
            } else {
                for (Move m : moves) {
                    if (m.getEnd().equals(end)) {
                        return true;
                    }
                }
                return false;
            }
        } else {
            return false;
        }
    }
}
