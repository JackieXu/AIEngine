package com.theaigames.bots.tictactoe;

import com.theaigames.util.RandomNumberGenerator;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Random Tic Tac Toe bot
 * 
 * @author Jackie Xu <jackie@starapple.nl>
 */
public class Bot {
    
    private State type;
    private State opponent_type;
    private final Scanner scanner;
    private enum State {BLANK, X, O};
    private State[][] board;
    
    public Bot() {
        this.scanner = new Scanner(System.in);
        this.board = new State[3][3];
    }
    
    public void run() {
        while (this.scanner.hasNextLine()) {
            String line = this.scanner.nextLine().trim();
            
            if (line.length() == 0) {
                continue;
            }
            
            String[] parts = line.split(" ");
            
            switch (parts[0]) {
                case "SETTINGS":
                    switch (parts[1]) {
                        case "YOUR_BOT":
                            switch (parts[2]) {
                                case "X":
                                    this.type = State.X;
                                    this.opponent_type = State.O;
                                    break;
                                case "O":
                                    this.type = State.O;
                                    this.opponent_type = State.X;
                                    break;
                                default:
                                    System.err.printf("Error: %s\n", line);
                                    break;
                            }
                            break;
                        default:
                            System.err.printf("Error: %s\n", line);
                    }
                    break;
                case "OPPONENT_MOVE":
                    String[] position = parts[1].split(",");
                    
                    // Get int values
                    int row = Integer.parseInt(position[0]);
                    int column = Integer.parseInt(position[1]);
                    
                    this.board[row][column] = this.opponent_type;
                    
                    break;
                case "GO":
                    
                    break;
                default:
                    System.err.printf("Error: %s\n", line);
            }
        }
    }
    
    public void makeMove() {
        
        // Get blank cells
        ArrayList<Cell> blankCells = this.getBlankCells();
        
        // Create random number generator
        RandomNumberGenerator rng = new RandomNumberGenerator(System.currentTimeMillis());
        
        // Get random index of valid moves
        int randomValidMoveIndex = rng.randomInteger(0, blankCells.size() - 1);
        
        // Get random valid cell
        Cell randomValidCell = blankCells.get(randomValidMoveIndex);
        
        // Make move
        System.out.printf("PLACE %d,%d\n", randomValidCell.row, randomValidCell.column);
        
    }
    
    public ArrayList<Cell> getBlankCells() {
        
        ArrayList<Cell> blankCells = new ArrayList();
        
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (this.board[row][col] == State.BLANK) {
                    blankCells.add(new Cell(row, col));
                }
            }
        }
        
        return blankCells;
        
    }
    
    public static void main(String[] args) {        
        Bot bot = new Bot();
        bot.run();
    }
    
}
