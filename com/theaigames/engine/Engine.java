package com.theaigames.engine;

import com.theaigames.engine.io.BotCommunication;
import com.theaigames.engine.io.IOHandler;
import com.theaigames.engine.io.IOPlayer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Engine class
 * 
 * A general engine to implement IO for bot classes
 * All game logic is handled by implemented Logic interfaces.
 * 
 * @author Jackie Xu <jackie@starapple.nl>
 */
public class Engine implements BotCommunication {
    
    // Boolean representing current engine running state
    private boolean isRunning;
    
    // Class implementing Logic interface; handles all data
    private Logic logic;
    
    // ArrayList containing player handlers
    private ArrayList<IOPlayer> players;
    
    // HashMap containing player actions in a round
    private HashMap<IOPlayer, ArrayList<String>> actions;
    
    // Engine constructor 
    public Engine() {
        this.isRunning = false;
        this.players = new ArrayList();
        this.actions = new HashMap();
    }
    
    // Sets game logic
    public void setLogic(Logic logic) {
        this.logic = logic;
    }
    
    // Determines whether game has ended
    public boolean hasEnded() {
        return this.logic.isGameWon();
    }
    
    // Adds a player to the game
    public void addPlayer(String command) throws IOException {
        
        // Create new process
        Process process = Runtime.getRuntime().exec(command);
        
        // Attach IOHandler to each process
        IOHandler handler = new IOHandler(process);
        
        // Attach IOHandler to player
        IOPlayer player = new IOPlayer(handler, this);
        
        // Add player
        this.players.add(player);
        
        // Create new entry in actions hashmap
        this.actions.put(player, new ArrayList());
        
        // Start running
        player.run();
    }
    
    @Override
    // Processes player action by inserting it into an ArrayList for later use
    public void process(IOPlayer player, String action) {
        
        // Get player's actions
        ArrayList<String> playerActions = this.actions.get(player);
        
        // Add new action
        playerActions.add(action);
        
    }
    
    // Method to start engine
    public void start() throws Exception {
        
        // Set engine to running
        this.isRunning = true;
        
        // Set up game settings
        this.logic.setupGame(this.players);
        
        // Keep running
        while (this.isRunning) {
            
            // Play a round
            this.logic.performActions(this.actions);
            
            // Check if win condition has been met
            if (this.hasEnded()) {
                
                // Stop running
                this.isRunning = false;
                
                // Print winners
                System.out.println(this.logic.getWinner());
                
            }
            
        }
        
    }
    
}
