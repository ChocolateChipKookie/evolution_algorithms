/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.fer.zemris.optjava.dz6;

import hr.fer.zemris.optjava.algorithms.neuralnetwork.FFANN;
import hr.fer.zemris.optjava.algorithms.neuralnetwork.IOptAlgorithm;
import hr.fer.zemris.optjava.algorithms.neuralnetwork.ITransferFunction;
import hr.fer.zemris.optjava.algorithms.neuralnetwork.SigmoidTransferFunction;

import java.util.Random;

/**
 *
 * @author Adi
 */
public class IrisProblem {
    
    public static void main(String[] args){
        IrisDataset data = IrisDataset.loadData(args[0]);
        FFANN neuralNetwork = new FFANN(new int[]{4, 4, 3},
                new ITransferFunction[]{
                        new SigmoidTransferFunction(),
                        new SigmoidTransferFunction()});

        int populationSize = Integer.parseInt(args[2]);
        double acceptableError = Double.parseDouble(args[3]);
        int generations = Integer.parseInt(args[4]);

        IrisFunction func = new IrisFunction(neuralNetwork, data);
        Random rand = new Random();


        IOptAlgorithm optAlgorithm = null;
        if(args[1].contains("pso")){
            int neighbourhood = 0;
            if(args[1].charAt(4) != 'a'){
                neighbourhood = Integer.parseInt(args[1].substring(6));
            }
            InertiaFunction inertia = new InertiaFunction(generations, 2, 0.2);
            optAlgorithm =
                    new ParticleSwarmOptimisation(
                            func,
                            inertia,
                            2,
                            2,
                            new SolutionBoundaries(
                                    -1,
                                    1,
                                    -0.3,
                                    0.3),
                            populationSize,
                            acceptableError,
                            neighbourhood);
        }
        else{
            optAlgorithm =
                    new ClonAlg(
                    func,
                    new ImuneSelection(),
                    new CloneMutate(rand, 0.75),
                    populationSize,
                    15,
                    25,
                    3,
                    generations,
                    acceptableError,
                    rand);
        }

        optAlgorithm.run();
        func.testSolution(optAlgorithm.getSolutionVector(), true);
    }
}
