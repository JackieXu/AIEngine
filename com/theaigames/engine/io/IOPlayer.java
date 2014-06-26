package com.theaigames.engine.io;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * IOPlayer class
 * 
 * Represents a player in the game.
 * 
 * @author Jackie Xu <jackie@starapple.nl>
 */
public class IOPlayer implements Runnable {
    
    // IOHandler to encapsulate IO streams
    private IOHandler handler;
    
    // BotCommunication interface implementation for callback
    private BotCommunication engine;
    
    /**
     * IOPlayer constructor
     * 
     * @param handler
     * @param engine 
     */
    public IOPlayer(IOHandler handler, BotCommunication engine) {
        this.handler = handler;
        this.engine = engine;
    }
    
    /**
     * 
     * @param line
     * @throws IOException 
     */
    public void process(String line) throws IOException {
        this.handler.getInputStream().write(line);
    }
    
    /**
     * Run Player
     */
    @Override
    public void run() {
        
        String lastLine;
        
        try {
            
            while ((lastLine = this.handler.getOutputStream().readLine()) != null) {
                this.engine.process(this, lastLine);
            }
            
            int exitValue = this.handler.getProcess().waitFor();
            System.out.println("Exited with code " + exitValue);
            
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(IOPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
