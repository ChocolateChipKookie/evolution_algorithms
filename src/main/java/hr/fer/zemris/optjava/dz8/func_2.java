package hr.fer.zemris.optjava.dz8;

public class func_2 implements  MOOPProblem{
    @Override
    public int getNumberOfObjectives() {
        return 2;
    }

    @Override
    public void evaluateSolution(double[] solution, double[] objectives) {
        objectives[0] = solution[0];
        objectives[1] = (1 + solution[1])/solution[0];
    }
}
