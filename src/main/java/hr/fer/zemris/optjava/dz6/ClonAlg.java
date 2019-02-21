package hr.fer.zemris.optjava.dz6;

import hr.fer.zemris.optjava.algorithms.neuralnetwork.IFunction;
import hr.fer.zemris.optjava.algorithms.neuralnetwork.IImuneMutate;
import hr.fer.zemris.optjava.algorithms.neuralnetwork.IImuneSelection;
import hr.fer.zemris.optjava.algorithms.neuralnetwork.IOptAlgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ClonAlg implements IOptAlgorithm<CloneSolution> {

    private IFunction func;
    private IImuneSelection<CloneSolution> selection;
    private IImuneMutate<CloneSolution> mutate;
    private int generationSize;
    private int d;
    private double beta;
    private int iterations;
    private Random rand;
    private CloneSolution bestSolution;
    private int selected;
    private double acceptableError;

    public ClonAlg(IFunction func,
                   IImuneSelection<CloneSolution> selection,
                   IImuneMutate<CloneSolution> mutate,
                   int generationSize,
                   int selected,
                   int d,
                   double beta,
                   int iterations,
                   double acceptableError,
                   Random rand){
        this.func = func;
        this.selection = selection;
        this.mutate = mutate;
        this.generationSize = generationSize;
        this.selected = selected;
        this.d = d;
        this.beta = beta;
        this.iterations = iterations;
        this.acceptableError = acceptableError;
        this.rand = rand;
        bestSolution = new CloneSolution(0);
        bestSolution.value = Double.MAX_VALUE;
    }

    @Override
    public CloneSolution run() {
        CloneSolution[] solutions = new CloneSolution[generationSize];

        for(int i = 0; i < generationSize; ++i){
            solutions[i] = new CloneSolution(func.solutionSize(), 0, 1, rand);
            solutions[i].value = func.valueAt(solutions[i].weights);
        }

        for(int i = 0; i < iterations; ++i){
            //Evaluate
            for(int j = 0; j < generationSize; ++j){
                solutions[j].value = func.valueAt(solutions[j].weights);
            }

            //Select and sort
            CloneSolution[] selected = selection.select(solutions, this.selected);
            if(i%100 == 0){
                System.out.println(selected[0].value);
            }

            //Clone
            CloneSolution[] cloned = clone(selected);

            //Hyper mutate
            for(int j = 0; j < cloned.length; ++j){
                mutate.mutate(cloned[j], 0.2);
            }

            //Evaluate
            for(int j = 0; j < cloned.length; ++j){
                cloned[j].value = func.valueAt(cloned[j].weights);
            }

            //Select next gen
            List<CloneSolution> sortTmp = new ArrayList<>();
            //noinspection Duplicates
            Collections.addAll(sortTmp, cloned);
            sortTmp.sort((CloneSolution t, CloneSolution t1) -> {
                if(t.value > t1.value) return 1;
                else if(t.value < t1.value) return -1;
                else return 0;
            });

            if(sortTmp.get(0).value < bestSolution.value){
                bestSolution = sortTmp.get(0).clone();
                if(bestSolution.value < acceptableError){
                    break;
                }
            }

            for(int j = 0; j < d; ++j){
                solutions[j] = sortTmp.get(j);
            }
            for(int j = d; j < solutions.length; ++j) {
                solutions[j] = new CloneSolution(func.solutionSize(), 0, 1, rand);
            }
        }
        return bestSolution;
    }

    @Override
    public CloneSolution getSolution() {
        return bestSolution;
    }

    @Override
    public double[] getSolutionVector() {
        return bestSolution.weights;
    }

    private CloneSolution[] clone(CloneSolution[] currentIndividuals){
        ArrayList<CloneSolution> tmp = new ArrayList<>();
        for(int i = 0; i < currentIndividuals.length; ++i){
            int offSpring = (int) Math.ceil((beta * currentIndividuals.length)/(i+1));
            for(int j = 0; j < offSpring; ++j){
                tmp.add(currentIndividuals[i].clone());
            }
        }
        return tmp.toArray(new CloneSolution[tmp.size()]);
    }

}
