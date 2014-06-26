package com.theaigames.game.tictactoe;

import com.theaigames.engine.Engine;
import com.theaigames.engine.Logic;
import com.theaigames.engine.io.IOPlayer;
import com.theaigames.util.RandomNumberGenerator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Tic Tac Toe
 * 
 * @author Jackie Xu <jackie@starapple.nl>
 */
public class TicTacToe implements Logic {
    
    private State[][] board;
    private IOPlayer playerX;
    private IOPlayer playerO;
    private boolean gameIsWon;
    private IOPlayer winner;
    
    public TicTacToe() {
        this.board = new State[3][3];
    }
    
    @Override
    public void setupGame(ArrayList<IOPlayer> players) throws IncorrectPlayerCountException, IOException {
        
        // Determine array size is two players
        if (players.size() != 2) {
            throw new IncorrectPlayerCountException("Should be two players");
        }
        
        // Construct random number generator
        RandomNumberGenerator rng = new RandomNumberGenerator(System.currentTimeMillis());
        
        // Determine player order
        if (rng.randomInteger(0, 1) == 0) {
            this.playerX = players.get(0);
            this.playerO = players.get(1);
        } else {
            this.playerO = players.get(0);
            this.playerX = players.get(1);
        }
        
        // Output settings to players
        this.playerX.process("SETTINGS YOUR_BOT X");
        this.playerO.process("SETTINGS YOUR_BOT O");
    }

    @Override
    public void performActions(HashMap<IOPlayer, ArrayList<String>> actions) {
        
        // Get two entries
        actions.get(this.playerO);
        
        
    }

    @Override
    public boolean isGameWon() {
        return this.gameIsWon;
    }

    @Override
    public IOPlayer getWinner() {
        return this.winner;
    }
    
    public void makeMove(int row, int column, State state) {
        
        if (this.board[row][column] == State.BLANK) {
            this.board[row][column] = state;
        }

        
    }
    
    public static void main(String[] args) throws Exception {
        
        // Construct engine
        Engine engine = new Engine();
        
        // Set logic
        engine.setLogic(new TicTacToe());
        
        // Add players
        engine.addPlayer("java com.theaigames.bots.tictactoe.Bot");
        engine.addPlayer("java com.theaigames.bots.tictactoe.Bot");
        
        // Start
        engine.start();
        
    }
    
}
