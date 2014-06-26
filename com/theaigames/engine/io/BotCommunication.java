package com.theaigames.engine.io;

/**
 * Bot Communication interface
 * 
 * Handles callback between players and the engine.
 * 
 * @author Jackie Xu <jackie@starapple.nl>
 */
public interface BotCommunication {
    
    /**
     * 
     * @param player
     * @param action 
     */
    public void process(IOPlayer player, String action);
    
}
