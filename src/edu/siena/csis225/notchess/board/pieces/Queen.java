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
 * Creates a Queen piece
 * 
 * @author Ross & Sorros
 * @date 04-2023
 */
public class Queen extends Piece {
    

    private Type type;
	private Color color;
	private int row;
	private int col;
	private File image;
    
    public Queen(int row, int col, Type type, Color color, File image) {
        super(row, col, type, color, image);
    }


    @Override
    public ArrayList<Move> getJumpMoves(Space startSpace, notchess game) {
        //do what I did for Bishop and Rook combined
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

        //check all horizontal and vertical spaces for a jump move
        //check up
        row = startSpace.getRow();
        col = startSpace.getCol();

        while(row < 7){
            row++;
            endSpace = game.getSpace(row, col);
            if(endSpace.getSpaceState() == SpaceState.OCCUPIED){
                if(endSpace.getPiece().getColor() != startSpace.getPiece().getColor()){
                    moves.add(new Move(startSpace, endSpace));
                }
                break;
            }
        }

        //check down
        row = startSpace.getRow();
        col = startSpace.getCol();

        while(row > 0){
            row--;
            endSpace = game.getSpace(row, col);
            if(endSpace.getSpaceState() == SpaceState.OCCUPIED){
                if(endSpace.getPiece().getColor() != startSpace.getPiece().getColor()){
                    moves.add(new Move(startSpace, endSpace));
                }
                break;
            }
        }

        //check right
        row = startSpace.getRow();
        col = startSpace.getCol();

        while(col < 7){
            col++;
            endSpace = game.getSpace(row, col);
            if(endSpace.getSpaceState() == SpaceState.OCCUPIED){
                if(endSpace.getPiece().getColor() != startSpace.getPiece().getColor()){
                    moves.add(new Move(startSpace, endSpace));
                }
                break;
            }
        }

        //check left
        row = startSpace.getRow();
        col = startSpace.getCol();

        while(col > 0){
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
    }
}
