package hr.fer.zemris.optjava.algorithms.neuralnetwork;

public interface IFunction {
    public double valueAt(double[] point);
    public int solutionSize();
}
