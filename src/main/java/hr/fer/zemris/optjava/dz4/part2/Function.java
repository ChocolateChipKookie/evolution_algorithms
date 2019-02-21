/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.fer.zemris.optjava.dz4.part2;

import hr.fer.zemris.optjava.algorithms.IFunction;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Adi
 */
public class Function implements IFunction{
    private int[][] distances;
    private int[][] amount;
    
    public Function(String filePath){
        
        try(BufferedReader br = new BufferedReader(new FileReader(filePath));){
            String s;
            int matrixSize = Integer.parseInt(br.readLine().trim());
            distances = new int[matrixSize][matrixSize];
            amount = new int[matrixSize][matrixSize];
            br.readLine();
            for(int i = 0; i < matrixSize; ++i){
                s = br.readLine();
                String[] tmp = s.trim().replaceAll("\\s+", " ").split(" ");
                for(int j = 0; j < matrixSize; ++j){
                    distances[i][j] = Integer.parseInt(tmp[j]);
                }
            }
            br.readLine();
            for(int i = 0; i < matrixSize; ++i){
                s = br.readLine();
                String[] tmp = s.trim().replaceAll("\\s+", " ").split(" ");
                for(int j = 0; j < matrixSize; ++j){
                    amount[i][j] = Integer.parseInt(tmp[j]);
                }
            }
        }
        catch(IOException ioe){
            ioe.printStackTrace();
        }
    }
    
    public int dimensions(){
        return distances.length;
    }

    @Override
    public double valueAt(double[] point) {
        int sum = 0;
        
        for(int i = 0; i < point.length; ++i){
            for(int j = 0; j < point.length; ++j){
                sum += distances[(int)point[i]][(int)point[j]]*amount[i][j];
            }
        }
    return sum;
    }
    
}
