/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.fer.zemris.optjava.algorithms.genetic;

/**
 *
 * @author Adi
 */
public interface ISelection<T> {
    
    public T[] select(T[] currentIndividuals);
    
}
