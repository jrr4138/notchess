package edu.siena.csis225.notchess;

import edu.siena.csis225.notchess.board.*;
import edu.siena.csis225.notchess.board.Space.SpaceState;
import edu.siena.csis225.notchess.board.pieces.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * NotChess
 * Game of Checkers that has Chess piece functionality
 * Objective of the Game: Capture all of the enemies pieces
 * Game will end if all pieces from one side are captured or
 * if the game lasts 100 total moves
 *
 * @author Ira Goldstein (Framework)
 * @author Josh Ross & Chris Sorros (editors)
 * @version Spring 2023
 */

public class notchess extends edu.siena.csis225.threadgraphics.ThreadGraphicsController
		implements MouseListener, ActionListener {

	private static final int MAX_TURNS = 100; // 50 PER PLAYER
	private static final int WIDTH = 80;
	private static final int HEIGHT = 80;
	private static final File BLACK_KING = new File("edu/siena/csis225/notchess/images/black_king.png");
	private static final File WHITE_KING = new File("edu/siena/csis225/notchess/images/white_king.png");
	private static final File BLACK_KNIGHT = new File("edu/siena/csis225/notchess/images/black_knight.png");
	private static final File WHITE_KNIGHT = new File("edu/siena/csis225/notchess/images/white_knight.png");
	private static final File BLACK_PAWN = new File("edu/siena/csis225/notchess/images/black_pawn.png");
	private static final File WHITE_PAWN = new File("edu/siena/csis225/notchess/images/white_pawn.png");
	private static final File BLACK_BISHOP = new File("edu/siena/csis225/notchess/images/black_bishop.png");
	private static final File WHITE_BISHOP = new File("edu/siena/csis225/notchess/images/white_bishop.png");
	private static final File BLACK_ROOK = new File("edu/siena/csis225/notchess/images/black_rook.png");
	private static final File WHITE_ROOK = new File("edu/siena/csis225/notchess/images/white_rook.png");
	private static final File BLACK_QUEEN = new File("edu/siena/csis225/notchess/images/black_queen.png");
	private static final File WHITE_QUEEN = new File("edu/siena/csis225/notchess/images/white_queen.png");

	private int x;
	private int y;
	private Space[][] board;
	private ArrayList<Piece> whitePieces;
	private ArrayList<Piece> blackPieces;
	private Player whitePlayer;
	private Player blackPlayer;
	private Point pressPoint;
	private Point releasePoint;
	private Space selectedSpace = null;
	private Piece selectedPiece = null;
	private JLabel moveLabel = new JLabel("Move: ");
	private JLabel[][] x_axis;
	private JLabel[][] y_axis;
	private JLabel enterMove = new JLabel("Enter Move: ");
	private JTextField writtenMove = new JTextField("", 10);
	private JButton moveButton = new JButton("Move");
	private int counter;
	private JLabel playerTurn = new JLabel("White's Turn");

	/**
	 * Constructor, which simply calls the superclass constructor
	 * with an appropriate window label and dimensions.
	 */
	public notchess() {

		super("Not Chess", 800, 850);
		this.board = new Space[8][8];
		// set up the board
		// fill the board with all unoccupied spaces

		this.whitePieces = new ArrayList<>();
		this.blackPieces = new ArrayList<>();
		this.whitePlayer = new Player(Color.WHITE, "White Player");
		this.blackPlayer = new Player(Color.BLACK, "Black Player");
		whitePlayer.changeTurn();
		this.x = 60;
		this.y = 60;
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				if (row < 2 || row >= 6) {
					board[row][col] = new Space(row, col, x, y, makePiece(row, col));
				} else {
					board[row][col] = new Space(row, col, x, y);
				}
				x += WIDTH;
			}
			x = 60;
			y += HEIGHT;
		}
		fillXAxis();
		fillYAxis();

		this.counter = 0;
	}

	public void fillXAxis() {
		x_axis = new JLabel[1][8];
		for (int i = 0; i < 8; i++) {
			x_axis[0][i] = new JLabel("" + getLetter(i));
		}
	}

	public void fillYAxis() {
		y_axis = new JLabel[8][1];
		for (int i = 0; i < 8; i++) {
			y_axis[i][0] = new JLabel("" + (i + 1));
		}
	}

	public Piece getPieceAtPosition(Space position) {
		return board[position.getRow()][position.getCol()].getPiece();
	}

	/**
	 * Get Space from Board
	 * 
	 * @param row row on board
	 * @param col col on board
	 * @return Space on the Board
	 */
	public Space getSpace(int row, int col) {
		return board[row][col];
	}

	/**
	 * Get the specified Space from the Board
	 * 
	 * @param pos Space
	 * @return Space on Board
	 */
	public Space getSpace(Space pos) {
		return board[pos.getRow()][pos.getCol()];
	}

	public Piece makePiece(int row, int col) {
		Piece piece = null;
		if (row == 0) {
			if (col == 0 || col == 7) {
				piece = new Rook(row, col, Type.ROOK, Color.WHITE, WHITE_ROOK);
				whitePieces.add(piece);
			} else if (col == 1 || col == 6) {
				piece = new Knight(row, col, Type.KNIGHT, Color.WHITE, WHITE_KNIGHT);
				whitePieces.add(piece);
			} else if (col == 2 || col == 5) {
				piece = new Bishop(row, col, Type.BISHOP, Color.WHITE, WHITE_BISHOP);
				whitePieces.add(piece);
			} else if (col == 3) {
				piece = new King(row, col, Type.KING, Color.WHITE, WHITE_KING);
				whitePieces.add(piece);
			} else if (col == 4) {
				piece = new Queen(row, col, Type.QUEEN, Color.WHITE, WHITE_QUEEN);
				whitePieces.add(piece);
			}
		} else if (row == 1) {
			piece = new Pawn(row, col, Type.PAWN, Color.WHITE, WHITE_PAWN);
			whitePieces.add(piece);
		} else if (row == 6) {
			piece = new Pawn(row, col, Type.PAWN, Color.BLACK, BLACK_PAWN);
			blackPieces.add(piece);
		} else if (row == 7) {
			if (col == 0 || col == 7) {
				piece = new Rook(row, col, Type.ROOK, Color.BLACK, BLACK_ROOK);
				blackPieces.add(piece);
			} else if (col == 1 || col == 6) {
				piece = new Knight(row, col, Type.KNIGHT, Color.BLACK, BLACK_KNIGHT);
				blackPieces.add(piece);
			} else if (col == 2 || col == 5) {
				piece = new Bishop(row, col, Type.BISHOP, Color.BLACK, BLACK_BISHOP);
				blackPieces.add(piece);
			} else if (col == 3) {
				piece = new King(row, col, Type.KING, Color.BLACK, BLACK_KING);
				blackPieces.add(piece);
			} else if (col == 4) {
				piece = new Queen(row, col, Type.QUEEN, Color.BLACK, BLACK_QUEEN);
				blackPieces.add(piece);
			}
		}
		board[row][col] = new Space(row, col, x, y, piece);
		return piece;
	}

	/**
	 * Used to get the letter at the respected board column
	 * through an input integer column number
	 * 
	 * @param i board column
	 * @return letter associated with input column
	 */
	public char getLetter(int i) {
		// will return letter of board column
		char letter = (char) ((char) i + 65);

		// used for X-Axis of Board
		if (i > 10) {
			letter = (char) ((char) (i / 80) + 64);
		}

		return letter;
	}

	/**
	 * Adds mouse listeners
	 * 
	 * @param p the JPanel to which the mouse listeners will be
	 *          attached
	 */
	@Override
	protected void addListeners(JPanel panel) {
		panel.addMouseListener(this);
		moveButton.addActionListener(this);
	}

	public void paintBoard(Graphics g) {
		panel.add(moveLabel);
		panel.add(playerTurn);
		panel.add(enterMove);
		panel.add(writtenMove);
		panel.add(moveButton);
		for (int i = 0; i < 8; i++) {
			panel.add(x_axis[0][i]);
			panel.add(y_axis[i][0]);
		}

		int x = 60;
		int y = 60;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board[i][j].getSpaceState() == SpaceState.OCCUPIED) {
					try {
						// create and ImageObserver
						g.drawImage(ImageIO.read(board[i][j].getPiece().getPieceFile()), x, y, null);
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
				x += WIDTH;
			}
			x = 60;
			y += HEIGHT;
		}
	}

	public void paint(Graphics g) {
		// create a 8x8 checkerboard
		int x = 60;
		int y = 60;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if ((i + j) % 2 == 0) {
					g.setColor(Color.WHITE);
				} else {
					g.setColor(Color.BLACK);
				}
				g.fillRect(x, y, WIDTH, HEIGHT);
				x += WIDTH;
			}
			x = 60;
			y += HEIGHT;
		}

		/*
		 * 
		 */
		Dimension enterMoveSize = enterMove.getPreferredSize();
		enterMove.setBounds(250, 740, enterMoveSize.width, enterMoveSize.height);

		Dimension writtenMoveSize = writtenMove.getPreferredSize();
		writtenMove.setBounds(320, 740, writtenMoveSize.width, writtenMoveSize.height);

		moveButton.setFont(new Font("Trebuchet", Font.PLAIN, 10));
		Dimension moveButtonSize = moveButton.getPreferredSize();
		moveButton.setBounds(450, 738, moveButtonSize.width, moveButtonSize.height);

		/*
		 * 
		 */

		Dimension size = moveLabel.getPreferredSize();
		moveLabel.setBounds(320, 15, size.width, size.height);

		// set the size of the playerTurn label
		Dimension playerTurnSize = playerTurn.getPreferredSize();
		playerTurnSize.setSize(200, 11);
		playerTurn.setBounds(320, 720, playerTurnSize.width, playerTurnSize.height);

		for (int i = 80; i <= 640; i += 80) {
			Dimension size1 = x_axis[0][(i - 80) / 80].getPreferredSize();
			x_axis[0][(i - 80) / 80].setBounds(i + 40, 40, size1.width, size1.height);

			Dimension size2 = y_axis[(i - 80) / 80][0].getPreferredSize();
			y_axis[(i - 80) / 80][0].setBounds(40, i + 40, size2.width, size2.height);
		}

		paintBoard(g);
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		pressPoint = e.getPoint();

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Space space = board[i][j];
				if (space.contains((int) pressPoint.getX(), (int) pressPoint.getY())) {
					selectedPiece = space.getPiece();
					selectedSpace = space;
					break;
				}
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {

		releasePoint = e.getPoint();

		if ((releasePoint.getY() < 700 && releasePoint.getY() > 60)
				&& (releasePoint.getX() < 700 && releasePoint.getX() > 60)) {

			// find the space that was clicked on
			if (selectedSpace.getPiece() == null) {
				moveLabel.setText("Move: INVALID SPACE!");
			} else {

				boolean pieceValid = false;
				for (int i = 0; i < 8; i++) {
					for (int j = 0; j < 8; j++) {
						Space newSpace = board[i][j];
						if (newSpace.contains((int) releasePoint.getX(), (int) releasePoint.getY())) {

							// get the move
							Move move = new Move(selectedSpace, newSpace);

							// get the color of the player whos turn it is
							Color playerColor = playerTurn.getText().equals("White's Turn") ? Color.WHITE : Color.BLACK;
							if (selectedSpace.getPiece().isValidMove(move, (notchess) this)
									&& selectedSpace.getPiece().getColor() == playerColor) {

								moveLabel.setText("Move: " + getLetter(selectedSpace.getCol()) + ""
										+ (selectedSpace.getRow() + 1) + "" + getLetter(j) + "" + (i + 1));

								// if piece is pawn at at end of board, change to Queen
								if (selectedSpace.getPiece().getType() == Type.PAWN
										&& (newSpace.getRow() == 0 || newSpace.getRow() == 7)) {

									// if pawn = white
									if (selectedSpace.getPiece().getColor() == Color.WHITE) {

										// if new space has a piece, remove it from black list
										if (newSpace.getPiece() != null) {
											blackPieces.remove(newSpace.getPiece());
										}
										newSpace.setPiece(new Queen(i, j, Type.QUEEN, Color.WHITE, WHITE_QUEEN));

										// if pawn = black
									} else {

										// if new space has a piece, remove it from white list
										if (newSpace.getPiece() != null) {
											whitePieces.remove(newSpace.getPiece());
										}
										newSpace.setPiece(new Queen(i, j, Type.QUEEN, Color.BLACK, BLACK_QUEEN));
									}

									// else piece either isn't pawn or isn't at end, place piece normally
								} else {

									// if new space has a piece, remove it from specified colored list
									if (newSpace.getPiece() != null) {
										if (newSpace.getPiece().getColor() == Color.WHITE) {
											whitePieces.remove(newSpace.getPiece());
										} else {
											blackPieces.remove(newSpace.getPiece());
										}
									}
									newSpace.setPiece(selectedSpace.getPiece(), i, j);
								}

								selectedSpace.removePiece();

								pieceValid = true;
							}
						}
					}
				}
				if (!pieceValid) {
					moveLabel.setText("Move: INVALID!");
				} else {
					counter++;
					if (counter >= MAX_TURNS) {
						if (blackPieces.size() > whitePieces.size()) {
							blackPlayer.IWon();
						} else {
							whitePlayer.IWon();
						}

						// if no more white pieces, black wins
					} else if (whitePieces.size() == 0) {
						blackPlayer.IWon();

						// if no more black pieces, white wins
					} else if (blackPieces.size() == 0) {
						whitePlayer.IWon();
					}

					// if white player won, display win text, end game
					if (whitePlayer.getIsWinner()) {
						moveLabel.setText("WHITE WINS!");
						if (counter >= MAX_TURNS) {
							playerTurn.setText("MAX TURNS MET");
						} else {
							playerTurn.setText("");
						}
						panel.removeMouseListener(this);

						// if black player won, display win text, end game
					} else if (blackPlayer.getIsWinner()) {
						moveLabel.setText("BLACK WINS!");
						if (counter >= MAX_TURNS) {
							playerTurn.setText("MAX TURNS MET");
						} else {
							playerTurn.setText("");
						}
						panel.removeMouseListener(this);

						// else no win condition met, continue game, change turn
					} else {
						whitePlayer.changeTurn();
						blackPlayer.changeTurn();
						if (blackPlayer.getIsTurn()) {
							playerTurn.setText("Black's Turn");
						} else {
							playerTurn.setText("White's Turn");
						}
					}
				}
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// stub
	}

	/**
	 * Used for the text-to-move functionality
	 * 
	 * @param e action event
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		// enterMove = jlabel
		// writtenMove = jtextfield
		// moveButton = jbutton
		String moveString = "";
		String from = "";
		String to = "";
		if (moveButton.equals(e.getSource())) {
			moveString = writtenMove.getText();

			// if string is correct length
			if (moveString.length() == 4) {
				from = moveString.substring(0, 2);
				to = moveString.substring(2, 4);

				// get the column and row of the selected space
				int fromCol = getCol(from.charAt(0));
				int fromRow = getRow(from.charAt(1));
				int toCol = getCol(to.charAt(0));
				int toRow = getRow(to.charAt(1));

				if (fromCol != -1 || fromRow != -1 || toCol != -1 || fromCol != -1) {
					// get the from and to spaces
					Space startSpace = board[fromRow][fromCol];
					Space endSpace = board[toRow][toCol];

					// check who's turn it is
					Color playerColor = playerTurn.getText().equals("White's Turn") ? Color.WHITE : Color.BLACK;

					// get the piece at the from space only if it is not null
					Piece startPiece = startSpace.getPiece() != null ? startSpace.getPiece() : null;
					Piece endPiece = endSpace.getPiece() != null ? startSpace.getPiece() : null;

					// if the piece is null, the move is invalid
					if (startPiece != null) {
						if (playerColor == startPiece.getColor()) {
							Move move = new Move(startSpace, endSpace);
							if (startPiece.isValidMove(move, (notchess) this)) {
								if (endPiece != null) {
									if (endPiece.getColor() == Color.WHITE) {
										whitePieces.remove(endPiece);
									} else {
										blackPieces.remove(endPiece);
									}
								}
								endSpace.setPiece(startPiece, toRow, toCol);
								startSpace.removePiece();
								moveLabel.setText("Move: " + moveString);

								// UPDATE TURN
								counter++;
								if (counter >= MAX_TURNS) {
									if (blackPieces.size() > whitePieces.size()) {
										blackPlayer.IWon();
									} else {
										whitePlayer.IWon();
									}

									// if no more white pieces, black wins
								} else if (whitePieces.size() == 0) {
									blackPlayer.IWon();

									// if no more black pieces, white wins
								} else if (blackPieces.size() == 0) {
									whitePlayer.IWon();
								}

								// if white player won, display win text, end game
								if (whitePlayer.getIsWinner()) {
									moveLabel.setText("WHITE WINS!");
									if (counter >= MAX_TURNS) {
										playerTurn.setText("MAX TURNS MET");
									} else {
										playerTurn.setText("");
									}
									panel.removeMouseListener(this);

									// if black player won, display win text, end game
								} else if (blackPlayer.getIsWinner()) {
									moveLabel.setText("BLACK WINS!");
									if (counter >= MAX_TURNS) {
										playerTurn.setText("MAX TURNS MET");
									} else {
										playerTurn.setText("");
									}
									panel.removeMouseListener(this);

									// else no win condition met, continue game, change turn
								} else {
									whitePlayer.changeTurn();
									blackPlayer.changeTurn();
									if (blackPlayer.getIsTurn()) {
										playerTurn.setText("Black's Turn");
									} else {
										playerTurn.setText("White's Turn");
									}
								}
							} else {
								moveLabel.setText("Move: INVALID!");
							}
						} else {
							moveLabel.setText("Move: INVALID!");
						}
					} else {
						moveLabel.setText("Move: INVALID!");
					}
				} else {
					moveLabel.setText("Move: INVALID!");
				}

			} else {
				moveLabel.setText("Move: INVALID!");
			}

		}

	}

	public int getRow(char c) {
		switch (c) {
			case '1':
				return 0;
			case '2':
				return 1;
			case '3':
				return 2;
			case '4':
				return 3;
			case '5':
				return 4;
			case '6':
				return 5;
			case '7':
				return 6;
			case '8':
				return 7;
			default:
				return -1;
		}
	}

	public int getCol(char c) {
		switch (c) {
			case 'A':
				return 0;
			case 'a':
				return 0;
			case 'B':
				return 1;
			case 'b':
				return 1;
			case 'C':
				return 2;
			case 'c':
				return 2;
			case 'D':
				return 3;
			case 'd':
				return 3;
			case 'E':
				return 4;
			case 'e':
				return 4;
			case 'F':
				return 5;
			case 'f':
				return 5;
			case 'G':
				return 6;
			case 'g':
				return 6;
			case 'H':
				return 7;
			case 'h':
				return 7;
			default:
				return -1;
		}
	}

	public static void main(String args[]) {

		// Need to take command line arguments
		if (args.length == 0) {
			javax.swing.SwingUtilities.invokeLater(new notchess());
		} else if (args.length > 1) {
			System.err.println("Usage: java notchess H");
			System.exit(1);
		} else {
			if (args[0].toLowerCase().equals("c")) {
				System.err.println("This is not the functionality you are looking for...");
				System.exit(1);
			} else if (!args[0].toLowerCase().equals("h")) {
				System.err.println("Usage: java notchess H");
				System.exit(1);
			} else {
				// construct our object and have its run method invoked to
				// set up the GUI once its thread is ready
				javax.swing.SwingUtilities.invokeLater(new notchess());
			}
		}
	}

}