/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.fer.zemris.optjava.dz2;

import jama.Matrix;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

/**
 *
 * @author Adi
 */
public class Sustav {
    public static void main(String[] args){
        int algorithm = 0;
        if(args[0].equals("grad"))algorithm = 1;
        else if(args[0].equals("newton"))algorithm = 2;
        int iterations = Integer.parseInt(args[1]);
        double[] startPoint = new double[2];
        double[][] params = new double[10][];
        BufferedReader br = null;
        int index = 0;
        
        //Parse input and make SATFormula
        try{

            br = new BufferedReader(new FileReader(args[2]));
            String s;
            while((s = br.readLine())!= null){                
                //Check for %
                if(s.charAt(0)== '#')continue;
                String[] parameters = s.substring(1, s.length()-1).split(", ");
                double[] param = new double[parameters.length];
                for(int i = 0; i<parameters.length; ++i){
                    param[i] = Double.parseDouble(parameters[i]);
                }
                params[index++] = param;
                if(index == 10)break;
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
        
        switch(algorithm){
            case 1:{
                FunctionSystem func = new FunctionSystem(params);
                gradientDescent(func, iterations);
                break;
            }
            case 2:{
                FunctionSystem func = new FunctionSystem(params);
                newtonMethod(func, iterations);
                break;
            }
            case 0:
                return;
        }
    }
     
    public static Matrix gradientDescent(FunctionSystem func, int iterations){
        
        Matrix sPoint = new Matrix(func.noOfVariables(), 1);
        Random rand = new Random();
        for(int i = 0; i<func.noOfVariables(); ++i){
            sPoint.set(i, 0, (rand.nextDouble() - 0.5)*10);
        }
        
        Matrix x = null;

        System.out.println("START POS: " + NumOptAlgorithms.matrixToString(sPoint));

        for(int i = 0; i < iterations; ++i){
            double lLambda = 0.;
            double uLambda = 0.001;
            Matrix err = func.totalError(sPoint.transpose().getArray()[0]);
            if(NumOptAlgorithms.gradientDistance(err)<0.0001)break;
            
            while(true){
                //postavljanje x-a

                x = sPoint.minus(err.times(uLambda));

                Matrix df = func.totalError(x.transpose().getArray()[0]);
                df = df.transpose();
                Matrix dfi = df.times(err.times(-1));

                if(dfi.get(0, 0) > 0){
                    break;
                }

                lLambda = uLambda;
                uLambda *=2;
            }
        
            while(true){
                double midLambda = (lLambda + uLambda)/2;
                x = sPoint.minus(err.times(midLambda));
                
                Matrix df = func.totalError(x.transpose().getArray()[0]);
                df = df.transpose();
                Matrix dfi = df.times(err.times(-1));

                if(Math.abs(dfi.get(0,0)) < 0.001){
                    System.out.println("ERR: " + NumOptAlgorithms.matrixToString(err.times(midLambda)));
                    break;
                }
                if(dfi.get(0,0) > 0) uLambda = midLambda;
                else lLambda = midLambda;
            }
            System.out.println("POS: "+ NumOptAlgorithms.matrixToString(x));

            sPoint = (Matrix)x.copy();
        }
            
        System.out.println("Final point:" + NumOptAlgorithms.matrixToString(x));
        
        return x;
    }
        
    public static Matrix newtonMethod(FunctionSystem func, int iterations){
        Matrix tau;
        Matrix hesse = null;
        Matrix nf = null;
        
        Matrix sPoint = new Matrix(func.noOfVariables(), 1);
        Random rand = new Random();
        for(int i = 0; i<func.noOfVariables(); ++i){
            sPoint.set(i, 0, (rand.nextDouble() - 0.5)*10);
        }
        
        Matrix x = (Matrix)sPoint.copy();
        
        
        for(int i = 0; i<iterations; ++i){
            hesse = func.getHesseMatrix();
            nf = func.totalError(x.transpose().getArray()[0]);
            
            hesse = hesse.inverse();
            hesse = hesse.times(nf);
            tau = hesse.times(-1);
            
            
            x = x.plus(tau);
            System.out.println(NumOptAlgorithms.matrixToString(x));
            if(NumOptAlgorithms.gradientDistance(func.totalError(x.transpose().getArray()[0])) < 0.001)break;
        }
        
        for(int i = 0; i<func.noOfVariables(); ++i){
            System.out.println(func.value(i, x.transpose().getArray()[0]));
        }
        
        
        return x;
    }
}
