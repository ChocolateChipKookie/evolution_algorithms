/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.fer.zemris.optjava.dz3;

import hr.fer.zemris.optjava.algorithms.IFunction;
import hr.fer.zemris.optjava.algorithms.GeometricTempSchedule;
import hr.fer.zemris.optjava.algorithms.GreedyAlgorithm;
import hr.fer.zemris.optjava.algorithms.IOptAlgorithm;
import hr.fer.zemris.optjava.solutions.bitvector.BitvectorNeighbourhood;
import hr.fer.zemris.optjava.solutions.bitvector.BitvectorSolution;
import hr.fer.zemris.optjava.solutions.bitvector.GrayBinaryDecoder;
import hr.fer.zemris.optjava.solutions.bitvector.NaturalBinaryDecoder;
import hr.fer.zemris.optjava.solutions.bitvector.SelectiveBitvectorNeighbourhood;
import hr.fer.zemris.optjava.solutions.doublearray.DoubleArraySolution;
import hr.fer.zemris.optjava.solutions.doublearray.PassThroughDecoder;
import hr.fer.zemris.optjava.solutions.doublearray.SelectiveDoubleArrayUnifNeighbourhood;
import hr.fer.zemris.optjava.solutions.IDecoder;
import hr.fer.zemris.optjava.solutions.INeighbourhood;
import hr.fer.zemris.optjava.solutions.SingleObjectiveSolution;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

/**
 *
 * @author Adi
 */
public class RegresijaSustava {
    
    public static void main(String[] args){
        Random rand = new Random();
        IOptAlgorithm alg = null;
       
        IDecoder decoder = null;
        INeighbourhood neighbourhood = null;
        SingleObjectiveSolution solution = null;
        
        BufferedReader br = null;
        double[][] params = new double[20][];
        int index = 0;
        
        try{

            br = new BufferedReader(new FileReader(args[0]));
            String s;
            while((s = br.readLine())!= null){                
                if(s.charAt(0)== '#')continue;
                
                String[] parameters = s.substring(1, s.length()-1).split(", ");
                double[] param = new double[parameters.length];
                for(int i = 0; i<parameters.length; ++i){
                    param[i] = Double.parseDouble(parameters[i]);
                }
                params[index++] = param;
                if(index == 20)break;                
            }
        }
        catch(IOException ioe){
            ioe.printStackTrace();
        }
        finally{
            try {
                if (br != null) br.close();
            } catch (IOException ex){
                ex.printStackTrace();
            }
        }
        
        IFunction func = new Function(params);
       
        int type = 0;
        int bits = 0;
        if(args[1].equals("decimal")) type = 2;
        else{
            type = 1;
            bits = Integer.parseInt(args[1].substring(7));
            if(bits > 31) bits = 31;
            else if(bits < 5) bits = 5;
        }
        
        switch(type){
            case 0:
                decoder = (IDecoder) new NaturalBinaryDecoder( - 10 , 10, bits, 6);
                neighbourhood = (INeighbourhood) new BitvectorNeighbourhood((NaturalBinaryDecoder)decoder, rand);
                solution = new BitvectorSolution(bits * 6);
                break;
            case 1:
                decoder = (IDecoder) new GrayBinaryDecoder( - 10 , 10, bits, 6);
                neighbourhood = (INeighbourhood) new SelectiveBitvectorNeighbourhood((GrayBinaryDecoder)decoder, new double[]{0.5, 0.5, 0.5, 0.5, 0.5, 0.5}, rand);                
                solution = (SingleObjectiveSolution) new BitvectorSolution(bits * 6);
                break;
            case 2:
                decoder = (IDecoder) new PassThroughDecoder();
                neighbourhood = (INeighbourhood) new SelectiveDoubleArrayUnifNeighbourhood(new double[]{0.03, 0.03, 0.03, 0.03, 0.03, 0.03}, new double[]{0.3, 0.3, 0.3, 0.3, 0.3, 0.3}, rand);
                DoubleArraySolution tmp = new DoubleArraySolution(6);
                tmp.values[0] = (rand.nextDouble() - 0.5 ) * 20;
                solution = (SingleObjectiveSolution) tmp;
        }
        
        switch(1){
            case 0:
                alg = new GreedyAlgorithm(
                    decoder, 
                    neighbourhood, 
                    solution, 
                    func,
                    true,
                    1, 
                    100
                );
                break;
            case 1:
                GeometricTempSchedule temp = null;
                if(type < 2) temp = new GeometricTempSchedule(0.994, 1000., 500, 3000);
                else temp = new GeometricTempSchedule(0.995, 100., 5000, 2000);
                alg = new SimulatedAnnealing(
                    decoder, 
                    neighbourhood, 
                    solution,
                    func,
                    temp, 
                    true, 
                    rand
            );
        }
        
        alg.run();
    }
}
