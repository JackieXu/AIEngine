package com.theaigames.engine.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * IOHandler class
 * 
 * Encapsulates all in- and output regarding a process.
 * 
 * @author Jackie Xu <jackie@starapple.nl>
 */
public class IOHandler {
    
    private Process process;
    private BufferedWriter inputStream;
    private BufferedReader errorStream;
    private BufferedReader outputStream;
    
    public IOHandler(Process process) {
        this.process = process;
        this.inputStream = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
        this.errorStream = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        this.outputStream = new BufferedReader(new InputStreamReader(process.getInputStream()));
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
    
}
