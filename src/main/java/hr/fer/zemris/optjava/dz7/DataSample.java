package hr.fer.zemris.optjava.dz7;

public class DataSample {
    public final double[] inputs;
    public final double output;

    public DataSample(double[] inputs, double output){
        this.inputs = inputs.clone();
        this.output = output;
    }
}
