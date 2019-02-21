/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.fer.zemris.optjava.solutions.integerarray;
import hr.fer.zemris.optjava.solutions.IDecoder;
import java.util.Arrays;

/**
 *
 * @author Adi
 */
public class IntegerDecoder implements IDecoder<IntegerArraySolution> {
    protected double[] mins;
    protected double[] maxs;
    protected int n;
    
    public IntegerDecoder(double[] mins, double[] maxs, int n){
        this.mins = mins.clone();
        this.maxs = maxs.clone();
        this.n = n;
    }
       
    public IntegerDecoder(double min, double max, int n){
        mins = new double[n];
        Arrays.fill(mins, min);
        maxs = new double[n];
        Arrays.fill(maxs, max);
        this.n = n;
    }
    
    public int getDimensions(){
        return n;
    }
    
    @Override
    public double[] decode(IntegerArraySolution solution){
        double[] res = new double[n];
        
        for(int i = 0; i < n; ++i){
            res[i] = mins[i] + ((double)solution.ints[i])/((double) Integer.MAX_VALUE) * (maxs[i] - mins[i]);
        }
        return res;
    }

    @Override
    public void decode(IntegerArraySolution solution, double[] result){
        result = decode(solution);
    }

}
