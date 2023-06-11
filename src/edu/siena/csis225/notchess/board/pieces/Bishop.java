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
 * Creates a Bishop piece
 * 
 * @author Ross & Sorros
 * @date 04-2023
 */
public class Bishop extends Piece{
    

    private Type type;
	private Color color;
	private int row;
	private int col;
	private File image;
    
    public Bishop(int row, int col, Type type, Color color, File image) {
        super(row, col, type, color, image);
    }


    @Override
    public ArrayList<Move> getJumpMoves(Space startSpace, notchess game) {
        ArrayList<Move> moves = new ArrayList<Move>();
        Space endSpace;
        int row = startSpace.getRow();
        int col = startSpace.getCol();
        
        //check all diagnal spaces for a jump move
        //check up and to the right
        while(row < 7 && col < 7){
            row++;
            col++;
            endSpace = game.getSpace(row, col);
            if(endSpace.getSpaceState() == SpaceState.OCCUPIED){
                if(endSpace.getPiece().getColor() != startSpace.getPiece().getColor()){
                    moves.add(new Move(startSpace, endSpace));
                }
                break;
            }
        }

        //check up and to the left
        row = startSpace.getRow();
        col = startSpace.getCol();

        while(row < 7 && col > 0){
            row++;
            col--;
            endSpace = game.getSpace(row, col);
            if(endSpace.getSpaceState() == SpaceState.OCCUPIED){
                if(endSpace.getPiece().getColor() != startSpace.getPiece().getColor()){
                    moves.add(new Move(startSpace, endSpace));
                }
                break;
            }
        }

        //check down and to the right
        row = startSpace.getRow();
        col = startSpace.getCol();

        while(row > 0 && col < 7){
            row--;
            col++;
            endSpace = game.getSpace(row, col);
            if(endSpace.getSpaceState() == SpaceState.OCCUPIED){
                if(endSpace.getPiece().getColor() != startSpace.getPiece().getColor()){
                    moves.add(new Move(startSpace, endSpace));
                }
                break;
            }
        }

        //check down and to the left
        row = startSpace.getRow();
        col = startSpace.getCol();

        while(row > 0 && col > 0){
            row--;
            col--;
            endSpace = game.getSpace(row, col);
            if(endSpace.getSpaceState() == SpaceState.OCCUPIED){
                if(endSpace.getPiece().getColor() != startSpace.getPiece().getColor()){
                    moves.add(new Move(startSpace, endSpace));
                }
                break;
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

        //check if the bishop is moving diagonally
        boolean valid = false;
        boolean isDiagonal = false;
        if(Math.abs(start.getRow() - end.getRow()) == Math.abs(end.getCol() - start.getCol())){
            //check to see if there are any pieces in the way
            isDiagonal = true;
        }

        if(isDiagonal){
            //check to see if there are any pieces in the way
            int row = start.getRow();
            int col = start.getCol();
            int endRow = end.getRow();
            int endCol = end.getCol();
            int rowIncrement = 0;
            int colIncrement = 0;
            if(row < endRow){
                rowIncrement = 1;
            } else {
                rowIncrement = -1;
            }
            if(col < endCol){
                colIncrement = 1;
            } else {
                colIncrement = -1;
            }
            row += rowIncrement;
            col += colIncrement;
            while(row != endRow && col != endCol){
                if(game.getSpace(row, col).getPiece() != null){
                    return false;
                }
                row += rowIncrement;
                col += colIncrement;
            }
            ArrayList<Move> moves = getJumpMoves(start, game);
            if(moves.isEmpty()) {
                return true;
            }
            for(Move m : moves){
                if(m.getEnd().equals(end)){
                    return true;
                }
            }
            return false;
        }

        
        return valid;
    }
}
