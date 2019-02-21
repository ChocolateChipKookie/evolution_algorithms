/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.fer.zemris.optjava.dz2;

import java.util.Random;

/**
 *
 * @author Adi
 */
public class Jednostavno {
    public static void main(String[] args){
        int algorithm = 0;
        if(args[0].equals("1a"))algorithm = 1;
        else if (args[0].equals("1b"))algorithm = 2;
        else if (args[0].equals("2a"))algorithm = 3;
        else if (args[0].equals("2b"))algorithm = 4;
        
        int iterations = Integer.parseInt(args[1]);
        double[] startPoint = new double[2];
            
        Random rand = new Random();
        
        if(args.length == 4){
            startPoint[0] = Double.parseDouble(args[2]);
            startPoint[1] = Double.parseDouble(args[3]);
        }
        else{
            startPoint[0] = (rand.nextDouble() - 0.5)*5;
            startPoint[1] = (rand.nextDouble() - 0.5)*5;
        }
        
        switch(algorithm){
            case 1:{
                Function1 func = new Function1();
                NumOptAlgorithms.gradientDescent(func, iterations, startPoint);
                break;
            }
            case 2:{
                HFunction1 func = new HFunction1();
                NumOptAlgorithms.newtonMethod(func, iterations, startPoint);
                break;
            }
            case 3:{
                Function2 func = new Function2();
                NumOptAlgorithms.gradientDescent(func, iterations, startPoint);
                break;
            }
            case 4:{
                HFunction2 func = new HFunction2();
                NumOptAlgorithms.newtonMethod(func, iterations, startPoint);
                break;
            }
            case 0:
                return;
        }
        
    }

}
