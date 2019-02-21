/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.fer.zemris.optjava.algorithms;

/**
 *
 * @author Adi
 */
public interface ITempSchedule {
    public double getNextTemperature();
    public int getInnerLoopCounter();
    public int getOuterLoopCounter();
}
