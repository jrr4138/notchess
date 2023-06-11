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
 * Creates a Rook piece
 * 
 * @author Ross & Sorros
 * @date 04-2023
 */
public class Rook extends Piece {

	private Type type;
	private Color color;
	private int row;
	private int col;
	private File image;

	public Rook(int row, int col, Type type, Color color, File image) {
		super(row, col, type, color, image);
	}

	/**
	 * Returns the first jump move available (if possible)
	 * 
	 * @param startSpace the space move is to be made from
	 * @param game       Board in use
	 * @return if jump is available, return jump move. else, return null.
	 */
	@Override
	public ArrayList<Move> getJumpMoves(Space startSpace, notchess game) {
		ArrayList<Move> moves = new ArrayList<Move>();
		Space endSpace;
		int row = startSpace.getRow();
		int col = startSpace.getCol();

		//check all horizontal and vertical spaces for a jump move
		//check up
		while (row < 7) {
			row++;
			endSpace = game.getSpace(row, col);
			if (endSpace.getSpaceState() == SpaceState.OCCUPIED) {
				if (endSpace.getPiece().getColor() != startSpace.getPiece().getColor()) {
					moves.add(new Move(startSpace, endSpace));
				}
				break;
			}
		}

		//check down
		row = startSpace.getRow();
		col = startSpace.getCol();

		while (row > 0) {
			row--;
			endSpace = game.getSpace(row, col);
			if (endSpace.getSpaceState() == SpaceState.OCCUPIED) {
				if (endSpace.getPiece().getColor() != startSpace.getPiece().getColor()) {
					moves.add(new Move(startSpace, endSpace));
				}
				break;
			}
		}


		//check left

		row = startSpace.getRow();
		col = startSpace.getCol();

		while (col > 0) {
			col--;
			endSpace = game.getSpace(row, col);
			if (endSpace.getSpaceState() == SpaceState.OCCUPIED) {
				if (endSpace.getPiece().getColor() != startSpace.getPiece().getColor()) {
					moves.add(new Move(startSpace, endSpace));
				}
				break;
			}
		}

		//check right

		row = startSpace.getRow();
		col = startSpace.getCol();

		while (col < 7) {
			col++;
			endSpace = game.getSpace(row, col);
			if (endSpace.getSpaceState() == SpaceState.OCCUPIED) {
				if (endSpace.getPiece().getColor() != startSpace.getPiece().getColor()) {
					moves.add(new Move(startSpace, endSpace));
				}
				break;
			}
		}

		return moves;
	}

	/**
	 * WILL RETURN TRUE IF ALL ARE PASSED:
	 * - no pieces are blocking path from start <-> end
	 * - end piece is different color from start piece
	 * - end piece is an empty space
	 * 
	 * @param move Move made by piece (start <-> end)
	 * @param game Game that holds the Board
	 */
	@Override
	public boolean isValidMove(Move move, notchess game) {
		Space start = move.getStart();
		Space end = move.getEnd();

		// make sure the start and the end is on the same row or that they are on the
		// same column
		if (start.getRow() != end.getRow() && start.getCol() != end.getCol()) {
			return false;
		}

		// make sure that there are no pieces in the way
		if (start.getRow() == end.getRow()) {
			if (start.getCol() < end.getCol()) {
				for (int i = start.getCol() + 1; i < end.getCol(); i++) {
					if (game.getSpace(start.getRow(), i).getSpaceState() == SpaceState.OCCUPIED) {
						return false;
					}
				}
			}
		}

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