package hr.fer.zemris.optjava.dz8;

public class MultipleObjectiveSolution {
    public double fitness;
    public int front;
    public double[] values;

    public MultipleObjectiveSolution(int resultsSize){
        this.values = new double[resultsSize];
    }

}
