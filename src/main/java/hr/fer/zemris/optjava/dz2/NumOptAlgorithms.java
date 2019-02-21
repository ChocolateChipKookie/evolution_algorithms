/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.fer.zemris.optjava.dz2;

import jama.*;
import java.util.Random;

/**
 *
 * @author Adi
 */
public class NumOptAlgorithms {
    
    public static double gradientDistance(Matrix m){
        double sum = 0;
        for(int i = 0; i<m.getRowDimension();++i){
            sum += m.get(i, 0)*m.get(i, 0);
        }
        return Math.sqrt(sum);
    }
    
    public static String matrixToString(Matrix m){
        StringBuilder sb = new StringBuilder();
        
        sb.append('(');
        for(int i = 0; i<m.getRowDimension() - 1; ++i){
            sb.append(m.get(i, 0) + ", ");
        }
        sb.append(m.get(m.getRowDimension()-1, 0) + ")");
        return sb.toString();
    }
    
    public static Matrix gradientDescent(IFunction func, int iterations, double ... startPoint){
        
        Matrix sPoint = new Matrix(func.noOfVariables(), 1);
        
        for(int i = 0; i<func.noOfVariables(); ++i){
            sPoint.set(i, 0, startPoint[i]);
        }
        
        Matrix x = null;

        System.out.println(matrixToString(sPoint));
                
        for(int i = 0; i < iterations; ++i){
            double lLambda = 0.;
            double uLambda = 0.001;
            Matrix grad = func.gradient(sPoint.transpose().getArray()[0]);
            if(gradientDistance(grad)<0.001)break;
            Matrix d = grad.times(-1);
            
            while(true){
                //postavljanje x-a

                x = sPoint.plus(d.times(uLambda));

                Matrix df = func.gradient(x.transpose().getArray()[0]);
                df = df.transpose();
                Matrix dfi = df.times(d);
                System.out.println(matrixToString(x));
                if(dfi.get(0, 0) > 0){
                    break;
                }

                lLambda = uLambda;
                uLambda *=2;
            }
        
            while(true){
                double midLambda = (lLambda + uLambda)/2;
                x = sPoint.plus(d.times(midLambda));
                
                Matrix df = func.gradient(x.transpose().getArray()[0]);
                df = df.transpose();
                Matrix dfi = df.times(d);

                System.out.println(matrixToString(x));
                
                if(Math.abs(dfi.get(0,0)) < 0.001) break;
                if(dfi.get(0,0) > 0) uLambda = midLambda;
                else lLambda = midLambda;
            }
            System.out.println(matrixToString(x));

            sPoint = (Matrix)x.copy();
        }
            
        System.out.println("Final point:" + matrixToString(x));
        return x;
    }
         
    public static Matrix gradientDescent(IFunction func, int iterations){
        Random rand = new Random();
        double[] startPoint = new double[func.noOfVariables()];
        for(int i = 0; i<startPoint.length; ++i){
            startPoint[i] = (rand.nextDouble() - 0.5)*5;
        }
        return gradientDescent(func, iterations, startPoint);
    }
    
    public static Matrix newtonMethod(IHFunction func, int iterations, double ... startPoint){
        Matrix tau;
        Matrix hesse = null;
        Matrix nf = null;
        Matrix x = new Matrix(func.noOfVariables(), 1);
        
        for(int i = 0; i<func.noOfVariables(); ++i){
            x.set(i, 0, startPoint[i]);
        }
        
        System.out.println(matrixToString(x));
        
        for(int i = 0; i<iterations; ++i){
            hesse = func.getHesseMatrix(x.transpose().getArray()[0]);
            nf = func.gradient(x.transpose().getArray()[0]);
            
            tau = hesse.inverse().times(nf).times(-1);
            
            x = x.plus(tau);
            System.out.println(matrixToString(x));
            if(gradientDistance(func.gradient(x.transpose().getArray()[0])) < 0.001)break;
        }
        
        System.out.println("Final point:" + matrixToString(x));
        
        return x;
    }
    
    public static Matrix newtonMethod(IHFunction func, int iterations){
        Random rand = new Random();
        double[] startPoint = new double[func.noOfVariables()];
        for(int i = 0; i<startPoint.length; ++i){
            startPoint[i] = (rand.nextDouble() - 0.5)*5;
        }
        return newtonMethod(func, iterations, startPoint);
    }
    
}
