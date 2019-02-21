/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.fer.zemris.optjava.solutions;

/**
 *
 * @author Adi
 */
public interface INeighbourhood<T> {
    public T randomNeighbour(T current);
}
