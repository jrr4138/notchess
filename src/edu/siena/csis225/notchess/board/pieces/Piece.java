package edu.siena.csis225.notchess.board.pieces;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;

import edu.siena.csis225.notchess.notchess;
import edu.siena.csis225.notchess.board.Move;
import edu.siena.csis225.notchess.board.Space;
import edu.siena.csis225.notchess.board.Type;

/**
 * Creates a piece to be placed on a Space on the Board
 * 
 * @author Ira Goldstein (framework)
 * @author Josh Ross & Chris Sorros (editors)
 * @version Spring 2023
 */

public abstract class Piece {

	private static final int PIECE_SIZE = 80;

	private Type type;
	private Color color;
	private int row;
	private int col;
	private File image;

	public Piece(int row, int col, Type type, Color color, File image) {
		this.row = row;
		this.col = col;
		this.type = type;
		this.color = color;
		this.image = image;
	}
	

	public Color getColor() {
		return color;
	}

	public Type getType() {
		return type;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public File getPieceFile() {
		if (image != null){
			return image;
		}
		return null;
	}
	
	public abstract boolean isValidMove(Move move, notchess game);

	public abstract ArrayList<Move> getJumpMoves(Space startSpace, notchess game);
}