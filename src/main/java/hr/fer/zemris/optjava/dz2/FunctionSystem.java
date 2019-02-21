/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.fer.zemris.optjava.dz2;

import jama.Matrix;

/**
 *
 * @author Adi
 */
public class FunctionSystem{
    private double[][] params;
    private Matrix[] matrices;
    
    public FunctionSystem(double[][] params){
        this.params = params;
        matrices = new Matrix[params.length];
        
        for(int i = 0; i<params.length; ++i){
            Matrix mat = new Matrix(params[i].length - 1, 1);
            for(int j = 0; j<params[i].length - 1; ++j){
                mat.set(j, 0, params[i][j]);
            }
            matrices[i] = mat;
        }
    }

    public int noOfVariables() {
        return params[0].length - 1;
    }

    public double value(int index, double... point) {
        double sum = 0;
        for(int i = 0; i<params[index].length-1; ++i){
            sum += point[i]*params[index][i];
        }
        return sum;
    }
    
    public double error(int index, double... point){
        return value(index, point) - params[index][params[index].length-1];
    }
    
    public Matrix totalError(double... point){
        Matrix mat = new Matrix(params.length, 1);
        Matrix p = new Matrix(point.length, 1);
        for(int i = 0; i<point.length; ++i){
            p.set(i, 0, point[i]);
        }
        
        for(int i = 0; i<params.length; ++i){
            double err = error(i, point);
            mat = mat.plus(matrices[i].times(err));
        }
        mat = mat.times(1./(double)params.length);
        
        return mat;
    }
    
    public Matrix getHesseMatrix(){
        Matrix mat = new Matrix(params).transpose();
        mat = mat.times(mat.transpose()).times(2);
        return mat;
    }
}
