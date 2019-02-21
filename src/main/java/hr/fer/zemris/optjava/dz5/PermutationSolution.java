/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.fer.zemris.optjava.dz5;

/**
 *
 * @author Adi
 */
public class PermutationSolution {
    
    private double value;
    public int[] permutation;
    
    public PermutationSolution(int cities){
        permutation = new int[cities];
    }
    
    public double getValue(){
        return value;
    }
    
    public void setValue(double value){
        this.value = value;
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(int i : permutation){
            sb.append(i).append(' ');
        }
        return sb.substring(0, sb.length() - 1);
    }
    
    public String printFrom(int element){
        StringBuilder sb = new StringBuilder();
        
        int pos = 0;
        for(int i = 0; i < permutation.length; ++i){
            if(permutation[pos = i] == element)break;
        }
        
        for(int i = 0; i < permutation.length; ++i){
            sb.append(permutation[(i + pos) % permutation.length]).append(' ');
        }
        
        return sb.substring(0, sb.length() - 1);
    }
    
}
