/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.fer.zemris.optjava.algorithms;

import hr.fer.zemris.optjava.algorithms.ITempSchedule;

/**
 *
 * @author Adi
 */
public class GeometricTempSchedule implements ITempSchedule{
    
    private final double alpha;
    private final double tInitial;
    private double tCurrent;
    private final int innerLimit;
    private final int outerLimit;
    
    public GeometricTempSchedule(double alpha, double tInitial, int innerLimit, int outerLimit){
        this.alpha = alpha;
        this.tInitial = tInitial;
        this.tCurrent = tInitial;
        this.innerLimit = innerLimit;
        this.outerLimit = outerLimit;
    }
    
    @Override
    public double getNextTemperature(){
        tCurrent = tCurrent*alpha;
        return tCurrent;
    }
    
    @Override
    public int getInnerLoopCounter(){
        return innerLimit;
    }

    @Override
    public int getOuterLoopCounter(){
        return outerLimit;
    }
}
