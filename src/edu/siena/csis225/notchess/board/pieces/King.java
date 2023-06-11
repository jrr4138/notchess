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
 * Creates a King piece
 * 
 * @author Ross & Sorros
 * @date 04-2023
 */
public class King extends Piece {
    

	private Type type;
	private Color color;
	private int row;
	private int col;
	private File image;
    
    public King(int row, int col, Type type, Color color, File image) {
        super(row, col, type, color, image);
    }


    @Override
    public ArrayList<Move> getJumpMoves(Space startSpace, notchess game) {
        //check if there is a piece of the opposite color one space next to the king
        ArrayList<Move> moves = new ArrayList<Move>();
        Space endSpace;
        int row = startSpace.getRow();
        int col = startSpace.getCol();

        //check up
        if(row < 7){
            endSpace = game.getSpace(row + 1, col);
            if(endSpace.getSpaceState() == SpaceState.OCCUPIED){
                if(endSpace.getPiece().getColor() != startSpace.getPiece().getColor()){
                    moves.add(new Move(startSpace, endSpace));
                }
            }
        }

        //check down
        if(row > 0){
            endSpace = game.getSpace(row - 1, col);
            if(endSpace.getSpaceState() == SpaceState.OCCUPIED){
                if(endSpace.getPiece().getColor() != startSpace.getPiece().getColor()){
                    moves.add(new Move(startSpace, endSpace));
                }
            }
        }

        //check left
        if(col > 0){
            endSpace = game.getSpace(row, col - 1);
            if(endSpace.getSpaceState() == SpaceState.OCCUPIED){
                if(endSpace.getPiece().getColor() != startSpace.getPiece().getColor()){
                    moves.add(new Move(startSpace, endSpace));
                }
            }
        }

        //check right
        if(col < 7){
            endSpace = game.getSpace(row, col + 1);
            if(endSpace.getSpaceState() == SpaceState.OCCUPIED){
                if(endSpace.getPiece().getColor() != startSpace.getPiece().getColor()){
                    moves.add(new Move(startSpace, endSpace));
                }
            }
        }

        //check up left
        if(row < 7 && col > 0){
            endSpace = game.getSpace(row + 1, col - 1);
            if(endSpace.getSpaceState() == SpaceState.OCCUPIED){
                if(endSpace.getPiece().getColor() != startSpace.getPiece().getColor()){
                    moves.add(new Move(startSpace, endSpace));
                }
            }
        }

        //check up right
        if(row < 7 && col < 7){
            endSpace = game.getSpace(row + 1, col + 1);
            if(endSpace.getSpaceState() == SpaceState.OCCUPIED){
                if(endSpace.getPiece().getColor() != startSpace.getPiece().getColor()){
                    moves.add(new Move(startSpace, endSpace));
                }
            }
        }

        //check down left
        if(row > 0 && col > 0){
            endSpace = game.getSpace(row - 1, col - 1);
            if(endSpace.getSpaceState() == SpaceState.OCCUPIED){
                if(endSpace.getPiece().getColor() != startSpace.getPiece().getColor()){
                    moves.add(new Move(startSpace, endSpace));
                }
            }
        }

        //check down right
        if(row > 0 && col < 7){
            endSpace = game.getSpace(row - 1, col + 1);
            if(endSpace.getSpaceState() == SpaceState.OCCUPIED){
                if(endSpace.getPiece().getColor() != startSpace.getPiece().getColor()){
                    moves.add(new Move(startSpace, endSpace));
                }
            }
        }

        return moves;
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

        //check if the king is moving to a space that is one space away from the current space
        if(Math.abs(start.getRow() - end.getRow()) <= 1 && Math.abs(end.getCol() - start.getCol()) <= 1){
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
