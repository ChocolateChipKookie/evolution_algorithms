/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.fer.zemris.optjava.dz3;

import hr.fer.zemris.optjava.algorithms.IFunction;

/**
 *
 * @author Adi
 */
public class Function implements IFunction{
        
        private double[][] params;
        
        public Function(double[][] params){
            this.params = params;
        }
        
        @Override
        public double valueAt(double[] point) {
            double sum = 0;
            for(int i = 0; i < params.length; ++i){
                double value = singleValue(point, i) - params[i][params[0].length - 1];
                sum += value*value;
            }
            sum /= params.length;
            return sum;
        }
        
        private double singleValue(double[] point, int paramsIndex){
            return  point[0] * params[paramsIndex][0] + 
                    point[1] * params[paramsIndex][0]*params[paramsIndex][0]*params[paramsIndex][0]*params[paramsIndex][1] + 
                    point[2] * Math.exp(point[3]*params[paramsIndex][2])*(1 + Math.cos(point[4]*params[paramsIndex][3])) + 
                    point[5] * params[paramsIndex][3]*params[paramsIndex][4]*params[paramsIndex][4];
        }
    }