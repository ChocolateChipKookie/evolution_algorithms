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
public interface IHFunction extends IFunction{
    public Matrix getHesseMatrix(double ... point);
}
