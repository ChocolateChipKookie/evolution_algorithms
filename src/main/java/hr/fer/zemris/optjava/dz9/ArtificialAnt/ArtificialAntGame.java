package hr.fer.zemris.optjava.dz9.ArtificialAnt;
import hr.fer.zemris.optjava.dz9.*;
import hr.fer.zemris.optjava.dz9.SymbolicRegression.SymbolicRegressionGPTree;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class ArtificialAntGame {

    public static void main(String[] args) {
        PlayingField playingField = new PlayingField(args[0], 500);
        int maxIter = Integer.parseInt(args[1]);
        int populationSize = Integer.parseInt(args[2]);
        double minFitness = 1./(Double.parseDouble(args[3]) - 0.5);

        Random rand = new Random();
        int maxDepth = 15;
        int maxSize = 100;
        int maxNewDepth = 3;
        int maxStartDepth = 10;
        int tournamentSize = 7;
        double crossover = 0.4;
        double reproduction = 0.3;
        double mutation = 0.3;


        ArtificialAntGPTree[] startPopulation = new ArtificialAntGPTree[populationSize];
        int each = populationSize/((maxStartDepth - 1)*2);

        for(int i = 0; i + 2 <= maxStartDepth; ++i){
            for(int j = 0; j < each; ++j){
                startPopulation[i*2 * each + j] = new ArtificialAntGPTree(i + 2, false, playingField, rand);
            }
            for(int j = 0; j < each; ++j){
                startPopulation[(i*2+1) * each + j] = new ArtificialAntGPTree(i + 2, true, playingField, rand);
            }
        }

        for(int i = (maxStartDepth - 1) * each*2; i < populationSize; ++i){
            startPopulation[i] = new ArtificialAntGPTree(2 + rand.nextInt(maxStartDepth - 1), false, playingField, rand);
        }


        SubtreeSwitchMutation<Double, Boolean> m = new SubtreeSwitchMutation<>(maxDepth, maxSize, maxNewDepth,  rand);
        TreeCrossover<Double, Double> cr = new TreeCrossover<>(maxDepth, maxSize, rand);
        Percentages p = new Percentages(crossover, reproduction, mutation);
        Tournament<SymbolicRegressionGPTree> t = new Tournament<>(tournamentSize, rand);

        GeneticAlgorithm<Action, Boolean> ga = new GeneticAlgorithm(m, cr, p, t, maxIter, playingField, startPopulation, minFitness, rand);
        ArtificialAntGPTree gp = (ArtificialAntGPTree) ga.run();

        System.out.println(gp.toString());
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(args[4]))) {
            writer.write(gp.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Object mutex = new Object();
        playingField.enterDisplayMode(mutex);
        playingField.resetGame();

        SwingUtilities.invokeLater(() -> {
            GameFrame frame = new GameFrame(playingField, mutex);
            frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        });

        playingField.startDisplay(gp);
    }
}
