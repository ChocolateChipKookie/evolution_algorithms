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
public class HFunction1 extends Function1 implements IHFunction{

    @Override
    public Matrix getHesseMatrix(double... point) {
        Matrix res = new Matrix(2, 2);
        res.set(0, 0, 2.);
        res.set(1, 1, 2.);
        return res;
    }

   
}
