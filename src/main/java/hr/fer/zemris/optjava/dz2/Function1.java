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
public class Function1 implements IFunction{
    private final int NO_OF_VARIABLES = 2;
    @Override
    public int noOfVariables(){
        return NO_OF_VARIABLES;
    }
    
    @Override
    public double value(double[] point){
        return point[0]*point[0] + (point[1] - 1.)*(point[1] - 1.);
    }
    
    @Override
    public Matrix gradient(double[] point){
        Matrix mat = new Matrix(NO_OF_VARIABLES, 1);
        mat.set(0, 0, 2*point[0]);
        mat.set(1, 0, 2*(point[1] - 1));
        return mat;
    }
}
