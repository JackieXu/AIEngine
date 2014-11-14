package com.theaigames.engine.io;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * IOPlayer class
 * 
 * Does the communication between the bot process and the engine
 * 
 * @author Jackie Xu <jackie@starapple.nl>, Jim van Eeden <jim@starapple.nl>
 */
public class IOPlayer implements Runnable {
    
    private Process process;
    private OutputStreamWriter inputStream;
    private InputStreamGobbler outputGobbler;
    private InputStreamGobbler errorGobbler;
    private String response;
    private StringBuilder dump;
    private int errorCounter;
    private final int maxErrors = 2;
    
    public IOPlayer(Process process, BotCommunication engine) {
        this.inputStream = new OutputStreamWriter(process.getOutputStream());
    	this.outputGobbler = new InputStreamGobbler(process.getInputStream(), this, "output");
    	this.errorGobbler = new InputStreamGobbler(process.getErrorStream(), this, "error");
        this.process = process;
        this.dump = new StringBuilder();
        this.errorCounter = 0;
    }
    
    // processes a line by reading it or writing it
    public void process(String line, String type) throws IOException {
    	switch (type) {
    	case "input":
    		this.inputStream.write(line + "\n");
    		addToDump(line + "\n");
    		this.inputStream.flush();
    		break;
    	case "output":
    		System.out.println("out: " + line);
    		addToDump("Output from your bot: \"" + line + "\"\n");
    		this.response = line;
    		break;
    	case "error":
    		System.out.println("error: " + line);
    		break;
    	}
    }
    
    // waits for a response from the bot
    public String getResponse(long timeOut) {
    	long timeStart = System.currentTimeMillis();
    	String response;
		
    	if (this.errorCounter > this.maxErrors) {
    		addToDump("Maximum number (" + this.maxErrors + ") of time-outs reached: skipping all moves.");
    		return "";
    	}
    	
    	while(this.response == null) {
    		long timeNow = System.currentTimeMillis();
			long timeElapsed = timeNow - timeStart;
			
			if(timeElapsed >= timeOut) {
				addToDump("Response timed out (" + timeOut + "ms), let your bot return 'No moves' instead of nothing or make it faster.");
				this.errorCounter++;
				return "";
			}
			
			try { Thread.sleep(2); } catch (InterruptedException e) {}
    	}
		if(this.response.equalsIgnoreCase("No moves"))
			return "";
		
		response = this.response;
		this.response = null;
		
		return response;
    }
    
    // ends the bot process and it's communication
    public int finish() {
    	try {
            this.inputStream.close();
        } catch (IOException e) {}
    	
    	try {
    		return this.process.waitFor();
    	} catch (InterruptedException ex) {
    		 Logger.getLogger(IOPlayer.class.getName()).log(Level.SEVERE, null, ex);
    		 return -1;
    	}
    }
    
    public Process getProcess() {
        return this.process;
    }
    
    public void addToDump(String dumpy){
		dump.append(dumpy);
	}
    
    public String getStdout() {
    	return this.outputGobbler.getData();
    }
    
    public String getStderr() {
    	return this.errorGobbler.getData();
    }
    
    public String getDump() {
    	return dump.toString();
    }

    @Override
    // start communication with the bot
    public void run() {
        this.outputGobbler.start();
        this.errorGobbler.start();
    }
}
