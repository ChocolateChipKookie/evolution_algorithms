package hr.fer.zemris.optjava.solutions.bitvector;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import hr.fer.zemris.optjava.solutions.bitvector.BitvectorDecoder;
import hr.fer.zemris.optjava.solutions.bitvector.BitvectorSolution;


/**
 *
 * @author Adi
 */
public class NaturalBinaryDecoder extends BitvectorDecoder{
    
    public NaturalBinaryDecoder(double[] mins, double[] maxs, int[] bitSizes, int n){
        super(mins, maxs, bitSizes, n);
    }
    
    public NaturalBinaryDecoder(double min, double max, int bitSize, int n){
        super(min, max, bitSize, n);
    }
    
    @Override
    public double[] decode(BitvectorSolution solution){
        double[] res = new double[super.n];
        int index = 0;
        
        for(int i = 0; i < res.length; ++i){
            int sum = 0;
            int tmp = 1;
            for(int j = 0; j < bits[i]; ++j){
                if(solution.bits[index + j])sum += tmp;
                tmp *= 2;
            }
            index += bits[i];
            
            res[i] = mins[i] + ((double) sum)/(Math.pow(2, bits[i]) - 1.) * (maxs[i] - mins[i]);
        }
        return res;
    }
    
    @Override
    public void decode(BitvectorSolution solution, double[] result){
        result = decode(solution);
    }
}
