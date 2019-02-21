package hr.fer.zemris.optjava.dz8;

public interface MOOPProblem {
    int getNumberOfObjectives();
    void evaluateSolution(double[] solution, double[] objectives);
}
