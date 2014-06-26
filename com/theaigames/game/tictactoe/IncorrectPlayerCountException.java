package com.theaigames.game.tictactoe;

/**
 *
 * @author Jackie Xu <jackie@starapple.nl>
 */
public class IncorrectPlayerCountException extends Exception {
    
    public IncorrectPlayerCountException(String message) {
        super(message);
    }
    
    public IncorrectPlayerCountException(String message, Throwable throwable) {
        super(message, throwable);
    }
    
}
