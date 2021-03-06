package com.theaigames.engine.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * InputStreamGobbler class
 * 
 * @author Jackie Xu <jackie@starapple.nl>, Jim van Eeden <jim@starapple.nl>
 */
public class InputStreamGobbler extends Thread {
	
	private InputStream inputStream;
	private IOPlayer player;
	private String type;
	private StringBuffer buffer;

	InputStreamGobbler(InputStream inputStream, IOPlayer player, String type) {
        this.inputStream = inputStream;
        this.player = player;
        this.type = type;
        this.buffer = new StringBuffer();
    }

    public void run() {
    	String lastLine;
    	
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(this.inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            while ((lastLine = bufferedReader.readLine()) != null) {
                this.player.response = lastLine;
                buffer.append(lastLine + "\n");
            }
            try {
            	bufferedReader.close();
            } catch (IOException e) {}
            
        } catch (IOException x) {
            throw new RuntimeException(x);
        }
    }
    
    public String getData() {
		return buffer.toString();
	}
}
