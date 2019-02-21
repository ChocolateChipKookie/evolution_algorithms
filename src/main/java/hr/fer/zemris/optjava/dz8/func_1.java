package hr.fer.zemris.optjava.dz8;

public class func_1 implements  MOOPProblem{
    @Override
    public int getNumberOfObjectives() {
        return 4;
    }

    @Override
    public void evaluateSolution(double[] solution, double[] objectives) {
        objectives[0] = solution[0]*solution[0];
        objectives[1] = solution[1]*solution[1];
        objectives[2] = solution[2]*solution[2];
        objectives[3] = solution[3]*solution[3];
    }
}
