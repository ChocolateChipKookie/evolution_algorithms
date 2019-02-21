/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.fer.zemris.optjava.algorithms.genetic;

/**
 *
 * @author Adi
 * @param <T>
 */
public interface ICrossover<T> {
    
    public T CreateOffspring(T mother, T father);
}
