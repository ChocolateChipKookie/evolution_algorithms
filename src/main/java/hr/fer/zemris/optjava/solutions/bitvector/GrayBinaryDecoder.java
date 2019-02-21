/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.fer.zemris.optjava.solutions.bitvector;


/**
 *
 * @author Adi
 */
public class GrayBinaryDecoder extends BitvectorDecoder{
    
    public GrayBinaryDecoder(double[] mins, double[] maxs, int[] bitSizes, int n){
        super(mins, maxs, bitSizes, n);
    }
    
    public GrayBinaryDecoder(double min, double max, int bitSize, int n){
        super(min, max, bitSize, n);
    }
    
    @Override
    public double[] decode(BitvectorSolution solution){
        double[] res = new double[n];
        int index = 0;
        
        for(int i = 0; i < n; ++i){
            int sum = 0;
            int tmp = 1;
            for(int j = 0; j < bits[i]; ++j){
                if(solution.bits[index + j])sum += tmp;
                tmp *= 2;
            }
            index += bits[i];
            
            res[i] = mins[i] + ((double) inverseGray(sum))/(Math.pow(2, bits[i]) - 1.) * (maxs[i] - mins[i]);
        }
        return res;
    }
    
    @Override
    public void decode(BitvectorSolution solution, double[] result){
        result = decode(solution);
    }
    
    private static int inverseGray(int n) 
    { 
        int inv = 0;
        for ( ; n != 0 ; n = n >> 1) 
            inv ^= n; 
      
        return inv; 
    } 
    
}
