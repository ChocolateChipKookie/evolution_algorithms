/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.fer.zemris.optjava.solutions.bitvector;
import hr.fer.zemris.optjava.solutions.IDecoder;
import java.util.Arrays;

/**
 *
 * @author Adi
 */
public abstract class BitvectorDecoder implements IDecoder<BitvectorSolution> {
    protected double[] mins;
    protected double[] maxs;
    protected int[] bits;
    protected int n;
    protected int totalBits;
    
    public BitvectorDecoder(double[] mins, double[] maxs, int[] bitSizes, int n){
        this.mins = mins.clone();
        this.maxs = maxs.clone();
        this.bits = bits.clone();
        this.n = n;
        totalBits = 0;
        for(int i = 0; i < bitSizes.length; ++i){
            totalBits += bitSizes[i];
        }

    }
    
    public BitvectorDecoder(double min, double max, int bitSize, int n){
        mins = new double[n];
        Arrays.fill(mins, min);
        maxs = new double[n];
        Arrays.fill(maxs, max);
        bits = new int[n];
        Arrays.fill(bits, bitSize);
        totalBits = bitSize * n;
        this.n = n;
    }
    
    public int getTotalBits(){
        return totalBits;
    }
    
    public int getDimensions(){
        return n;
    }
    
    @Override
    public abstract double[] decode(BitvectorSolution solution);

    @Override
    public abstract void decode(BitvectorSolution solution, double[] result);    
}
