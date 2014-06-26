package com.theaigames.engine;

import com.theaigames.engine.io.IOPlayer;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Logic interface
 * 
 * Interface to implement when creating games.
 * 
 * @author Jackie Xu <jackie@starapple.nl>
 */
public interface Logic {
    public void setupGame(ArrayList<IOPlayer> players) throws Exception;
    public void performActions(HashMap<IOPlayer, ArrayList<String>> actions) throws Exception;
    public boolean isGameWon();
    public IOPlayer getWinner();
}
