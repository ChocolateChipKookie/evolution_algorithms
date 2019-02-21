package hr.fer.zemris.optjava.dz6;

import hr.fer.zemris.optjava.algorithms.neuralnetwork.IInertia;

public class InertiaFunction implements IInertia {

    private final int iterations;
    private  double currentInertia;
    private double alpha;
    private double initialInertia;

    public InertiaFunction(int iterations, double initialInertia, double finalInertia){
        this.initialInertia = initialInertia;
        this.alpha = Math.pow(finalInertia/ initialInertia, 1./iterations);
        this.currentInertia = initialInertia;
        this.iterations = iterations;
    }

    @Override
    public double getIterations() {
        return iterations;
    }

    @Override
    public double getInertia() {
        return (currentInertia *= alpha);
    }
}
