package com.theaigames.util;

import java.util.Random;

/**
 * Random Number Generator class
 * 
 * Adds utility methods for random methods
 * 
 * @author Jackie Xu <jackie@starapple.nl>
 */
public class RandomNumberGenerator {
    
    private long seed;
    private Random random;
    
    /**
     * Constructs a new RandomNumberGenerator
     * 
     * @param seed 
     */
    public RandomNumberGenerator(long seed) {
        this.seed = seed;
        this.random = new Random(seed);
    }
    
    /**
     * Returns a random integer between min and max inclusive.
     * 
     * @param min The lowest integer to output
     * @param max The highest integer to output
     * @return 
     */
    public int randomInteger(int min, int max) {
        return this.random.nextInt((max - min) + 1) + min;
    }
    
}
