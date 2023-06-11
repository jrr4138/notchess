package edu.siena.csis225.notchess.board.pieces;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;

import javax.naming.ldap.StartTlsRequest;

import edu.siena.csis225.notchess.notchess;
import edu.siena.csis225.notchess.board.Move;
import edu.siena.csis225.notchess.board.Space;
import edu.siena.csis225.notchess.board.Type;
import edu.siena.csis225.notchess.board.Space.SpaceState;

/**
 * Creates a Pawn piece
 * 
 * @author Ross & Sorros
 * @date 04-2023
 */
public class Pawn extends Piece {

    private Type type;
    private Color color;
    private int row;
    private int col;
    private File image;

    // used to determine if the piece has moved previously
    private boolean moved;

    public Pawn(int row, int col, Type type, Color color, File image) {
        super(row, col, type, color, image);
        this.moved = false;
    }

    @Override
    public ArrayList<Move> getJumpMoves(Space startSpace, notchess game) {
        ArrayList<Move> moves = new ArrayList<Move>();
        Space endSpace;

        int row = startSpace.getRow();
        int col = startSpace.getCol();

        //check if pawn movement results in a take
        if(startSpace.getPiece().getColor() == Color.WHITE){
            //check up and to the right
            if(row < 7 && col < 7){
                endSpace = game.getSpace(row + 1, col + 1);
                if(endSpace.getSpaceState() == SpaceState.OCCUPIED){
                    if(endSpace.getPiece().getColor() != startSpace.getPiece().getColor()){
                        moves.add(new Move(startSpace, endSpace));
                    }
                }
            }

            //check up and to the left
            if(row < 7 && col > 0){
                endSpace = game.getSpace(row + 1, col - 1);
                if(endSpace.getSpaceState() == SpaceState.OCCUPIED){
                    if(endSpace.getPiece().getColor() != startSpace.getPiece().getColor()){
                        moves.add(new Move(startSpace, endSpace));
                    }
                }
            }
        }else{
            //check down and to the right
            if(row > 0 && col < 7){
                endSpace = game.getSpace(row - 1, col + 1);
                if(endSpace.getSpaceState() == SpaceState.OCCUPIED){
                    if(endSpace.getPiece().getColor() != startSpace.getPiece().getColor()){
                        moves.add(new Move(startSpace, endSpace));
                    }
                }
            }

            //check down and to the left
            if(row > 0 && col > 0){
                endSpace = game.getSpace(row - 1, col - 1);
                if(endSpace.getSpaceState() == SpaceState.OCCUPIED){
                    if(endSpace.getPiece().getColor() != startSpace.getPiece().getColor()){
                        moves.add(new Move(startSpace, endSpace));
                    }
                }
            }
        }
        return moves;
    }

    /**
     * WILL RETURN TRUE IF ALL ARE PASSED:
     * - piece does not move backwards
     * - piece takes from corner, not directly on
     * - piece is not blocked from moving forwards
     * 
     * @param move Move made by piece (start <-> end)
     * @param game Game that holds the Board
     */
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

        // if white, can't move up
        if (start.getPiece().getColor() == Color.WHITE) {
            if (start.getRow() > end.getRow()) {
                return false;
            }

            // else black, can't move down
        } else {
            if (start.getRow() < end.getRow()) {
                return false;
            }
        }

        // if the hasMoved is false, then the pawn can move two spaces as long as there
        // is no piece in front of it
        if (!this.moved) {
            if (start.getCol() == end.getCol() && Math.abs(start.getRow() - end.getRow()) <= 2) {
                if (start.getRow() - end.getRow() == 2) {
                    if (game.getSpace(start.getRow() - 1, start.getCol()).getSpaceState() == SpaceState.OCCUPIED) {
                        return false;
                    }
                }
                ArrayList<Move> moves = getJumpMoves(start, game);
                if (moves.isEmpty()) {
                    moved = true;
                    return true;
                } else {
                    for (Move m : moves) {
                        if (m.getEnd().equals(end)) {
                            moved = true;
                            return true;
                        }
                    }

                    return false;
                }
            } else {
                if (Math.abs(start.getRow() - end.getRow()) == 1 && Math.abs(start.getCol() - end.getCol()) == 1) {
                    if (end.getSpaceState() == SpaceState.OCCUPIED) {
                        ArrayList<Move> moves = getJumpMoves(start, game);
                        if (moves.isEmpty()) {
                            moved = true;
                            return true;
                        } else {
                            for (Move m : moves) {
                                if (m.getEnd().equals(end)) {
                                    moved = true;
                                    return true;
                                }
                            }

                            return false;
                        }
                    }
                } else {
                    // if the pawn is moving one space forward, then it is a valid move
                    if (start.getCol() == end.getCol() && Math.abs(start.getRow() - end.getRow()) == 1) {
                        // if the space is unoccupied, then it is a valid move
                        if (end.getSpaceState() == SpaceState.OPEN) {
                            ArrayList<Move> moves = getJumpMoves(start, game);
                            if (moves.isEmpty()) {
                                moved = true;
                                return true;
                            } else {
                                for (Move m : moves) {
                                    if (m.getEnd().equals(end)) {
                                        moved = true;
                                        return true;
                                    }
                                }

                                return false;
                            }
                        }
                    } else {
                        return false;
                    }
                }
            }
        } else {
            // if there is a piece one space diagnal to the pawn, then the pawn can take it
            if (Math.abs(start.getRow() - end.getRow()) == 1 && Math.abs(start.getCol() - end.getCol()) == 1) {
                if (end.getSpaceState() == SpaceState.OCCUPIED) {
                    ArrayList<Move> moves = getJumpMoves(start, game);
                    if (moves.isEmpty()) {
                        moved = true;
                        return true;
                    } else {
                        for (Move m : moves) {
                            if (m.getEnd().equals(end)) {
                                moved = true;
                                return true;
                            }
                        }

                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                // if the pawn is moving one space forward, then it is a valid move
                if (start.getCol() == end.getCol() && Math.abs(start.getRow() - end.getRow()) == 1) {
                    // if the space is unoccupied, then it is a valid move
                    if (end.getSpaceState() == SpaceState.OPEN) {
                        ArrayList<Move> moves = getJumpMoves(start, game);
                        if (moves.isEmpty()) {
                            moved = true;
                            return true;
                        } else {
                            for (Move m : moves) {
                                if (m.getEnd().equals(end)) {
                                    moved = true;
                                    return true;
                                }
                            }

                            return false;
                        }
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }

        return false;
    }
}
