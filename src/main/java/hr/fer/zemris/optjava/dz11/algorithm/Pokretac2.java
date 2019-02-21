package hr.fer.zemris.optjava.dz11.algorithm;

import hr.fer.zemris.optjava.dz11.genetic_algorithm.MultiThreadedGA;
import hr.fer.zemris.optjava.dz11.gray_scale_image.Bounds;
import hr.fer.zemris.optjava.dz11.genetic_algorithm.crossovers.RectangleAtomCrossover;
import hr.fer.zemris.optjava.dz11.drawing_window.DrawFrame;
import hr.fer.zemris.optjava.dz11.genetic_algorithm.Tournament;
import hr.fer.zemris.optjava.dz11.task.Evaluator;
import hr.fer.zemris.optjava.dz11.gray_scale_image.GrayScaleImage;
import hr.fer.zemris.optjava.dz11.genetic_algorithm.mutations.*;
import hr.fer.zemris.optjava.dz11.genetic_algorithm.RectangleGenome;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Pokretac2 {

    /////////////////////////////////////////////////////
    //
    //  Za aproksimaciju koristiti sliku malenih dimenzija (200-400 px visine i sinire)
    //  Jer ne utjece znatno na rad, a jako usporava aproksimaciju
    //
    //  ARGS:
    //  0 - File sa slikom koju se zeli aproksimirati
    //  1 - Broj kvadrata(100-1000)
    //  2 - Broj jedinki u populaciji (100-500)
    //  3 - Broj generacija (broj kvadrata * (250 do 1000)) Vjerovatno ce rasti ako ima puno preklapanja
    //  4 - Minimalni fitness kada prestaje rad algoritma (beskorisno)
    //  5 - Put do fajla s zavrsnim kordinatama kvadrata
    //  6 - Put do slike rezultata
    //
    /////////////////////////////////////////////////////

    public static void main(String[] args) throws IOException {

        int rectangles = Integer.parseInt(args[1]);
        int population = Integer.parseInt(args[2]);
        int generations = Integer.parseInt(args[3]);
        double minFitness = Double.parseDouble(args[4]);
        Evaluator evaluator = new Evaluator(args[0]);

        File result = new File(args[6]);
        //Expected image
        String name = result.getName();
        String expectedName = result.getPath().replace(name,"expected-" + name);
        evaluator.image.save(new File(expectedName));

        Bounds bounds = new Bounds(evaluator.image.getWidth(), evaluator.image.getHeight());

        RectangleAtomMutation atomMutation = new RectangleAtomMutation(0.25, 0.10, 0.1, bounds);
        RectangleColourMutation colourMutation = new RectangleColourMutation(0.3, 0.1, 0.00);
        RectangleSwitchMutation switchMutation = new RectangleSwitchMutation(10);
        NewRectMutation newRectMutation = new NewRectMutation(5, bounds);
        LastRectMutation lastRectMutation = new LastRectMutation(0.1, 0.2, 0.1, bounds);

        List<MutationChanceWrapper> chanceWrappers = new ArrayList<>();
        chanceWrappers.add(new MutationChanceWrapper(atomMutation, 0.0));
        chanceWrappers.add(new MutationChanceWrapper(colourMutation, 0.00));
        chanceWrappers.add(new MutationChanceWrapper(switchMutation, 0.00));
        chanceWrappers.add(new MutationChanceWrapper(newRectMutation, 0.));
        chanceWrappers.add(new MutationChanceWrapper(lastRectMutation, 0.5));

        TotalMutation totalMutation = new TotalMutation(chanceWrappers);

        RectangleAtomCrossover atomCrossover = new RectangleAtomCrossover(true, 0.3);
        Tournament tournament = new Tournament(10);

        RectangleGenome[] start = new RectangleGenome[population];
        for(int i = 0; i < population; ++i){
            start[i] = RectangleGenome.random(rectangles, 0, bounds);
        }

        DrawFrame frame = new DrawFrame( bounds.width, bounds.height, 2);
        MultiThreadedGA ga = new MultiThreadedGA(lastRectMutation, atomCrossover, tournament, generations, evaluator, start, minFitness, bounds, frame);

        SwingUtilities.invokeLater(() -> {
            frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        });

        RectangleGenome res = ga.run();
        res.dump(args[5]);

        GrayScaleImage finalImage = new GrayScaleImage(bounds.width, bounds.height);
        res.draw(finalImage);
        finalImage.save(new File(args[6]));
    }
}
