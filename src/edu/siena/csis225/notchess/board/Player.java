package edu.siena.csis225.notchess.board;

import java.awt.Color;

/**
 * Represents a player in the game.
 * 
 * @Author Ross & Sorros
 * @version Spring 2023
 * 
 */
public class Player {
    private Color color;
    private String name;
    private boolean isTurn;
    private boolean isWinner;

    /**
     * Creates a new player
     * 
     * @param color the color of the player
     * @param name the name of the player
     */
    public Player(Color color, String name) {
        this.color = color;
        this.name = name;
        if (color == Color.WHITE){
            this.isTurn = true;
        } else {
            this.isTurn = false;
        }
        
        this.isWinner = false;
    }

    /**
     * Gets the color of the player
     * 
     * @return the color of the player
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Gets the name of the player
     * 
     * @return the name of the player
    */
    public String getName() {
        return this.name;
    }

    /**
     * Checks if it is the player's turn.
     * 
     * @return true if it is the player's turn, false otherwise.
     */
    public boolean getIsTurn() {
        return this.isTurn;
    }

    /**
     * Checks if the player has won.
     * 
     * @return true if the player has won, false otherwise.
     */
    public boolean getIsWinner() {
        return this.isWinner;
    }

    /**
     * Changes the turn of the player.
     */
    public void changeTurn() {
        if(this.isTurn == true) {
            this.isTurn = false;
        } else {
            this.isTurn = true;
        }
    }

    /**
     * Sets the player as the winner.
     */
    public void IWon() {
        this.isWinner = true;
    }
}
