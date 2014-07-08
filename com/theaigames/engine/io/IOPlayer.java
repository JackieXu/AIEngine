package com.theaigames.engine.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * IOPlayer class
 * 
 * @author Jackie Xu <jackie@starapple.nl>
 */
public class IOPlayer implements Runnable {
    
    private Process process;
    private BotCommunication engine;
    private BufferedWriter inputStream;
    private BufferedReader errorStream;
    private BufferedReader outputStream;
    
    public IOPlayer(Process process, BotCommunication engine) {
        this.inputStream = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
        this.errorStream = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        this.outputStream = new BufferedReader(new InputStreamReader(process.getInputStream()));
        this.process = process;
        this.engine = engine;
    }
    
    public void processData(String line) throws IOException {
        this.inputStream.write(line);
    }
    
    public Process getProcess() {
        return this.process;
    }
    
    public BufferedWriter getInputStream() {
        return this.inputStream;
    }
    
    public BufferedReader getErrorStream() {
        return this.errorStream;
    }
    
    public BufferedReader getOutputStream() {
        return this.outputStream;
    }
    
    public void process(String line) throws IOException {
        this.inputStream.write(line);
    }

    @Override
    public void run() {
        String lastLine;
        
        try {
            
            while ((lastLine = this.outputStream.readLine()) != null) {
                this.engine.process(this, lastLine);
            }
            
            int exitValue = this.process.waitFor();
            System.out.println("Exited with code " + exitValue);
            
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(IOPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
