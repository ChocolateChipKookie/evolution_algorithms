package hr.fer.zemris.optjava.dz11.algorithm;

import hr.fer.zemris.optjava.dz11.gray_scale_image.Bounds;
import hr.fer.zemris.optjava.dz11.genetic_algorithm.crossovers.RectangleAtomCrossover;
import hr.fer.zemris.optjava.dz11.drawing_window.DrawFrame;
import hr.fer.zemris.optjava.dz11.genetic_algorithm.GeneticAlgorithm;
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

public class Pokretac1 {

    /*
    RADE KORISTITI POKRETAC2
    RADE KORISTITI POKRETAC2
    RADE KORISTITI POKRETAC2
    RADE KORISTITI POKRETAC2
    RADE KORISTITI POKRETAC2
    RADE KORISTITI POKRETAC2
    RADE KORISTITI POKRETAC2
    */


    public static void main(String[] args) throws IOException, InterruptedException {

        //Broj kvadrata
        int rectangles = Integer.parseInt(args[1]);
        //populacija
        int population = Integer.parseInt(args[2]);
        //broj generacija
        int generations = Integer.parseInt(args[3]);
        //minima fitnes
        double minFitness = Double.parseDouble(args[4]);
        //put do fajla s rezultatom
        //put do slike

        Evaluator e = new Evaluator(args[0]);

        File result = new File(args[6]);
        //Expected image
        String name = result.getName();
        String expectedName = result.getPath().replace(name,"expected-" + name);
        e.image.save(new File(expectedName));


        Bounds b = new Bounds(e.image.getWidth(), e.image.getHeight());

        RectangleAtomMutation ram = new RectangleAtomMutation(0.25, 0.10, 0.1, b);
        RectangleColourMutation rcm = new RectangleColourMutation(0.3, 0.1, 0.00);
        RectangleSwitchMutation rsm = new RectangleSwitchMutation(10);
        NewRectMutation nrm = new NewRectMutation(5, b);
        LastRectMutation lrm = new LastRectMutation(0.1, 0.25, 0.1, b);

        List<MutationChanceWrapper> chanceWrappers = new ArrayList<>();
        chanceWrappers.add(new MutationChanceWrapper(ram, 0.1));
        chanceWrappers.add(new MutationChanceWrapper(rcm, 0.2));
        chanceWrappers.add(new MutationChanceWrapper(rsm, 0.1));
        chanceWrappers.add(new MutationChanceWrapper(nrm, 0.1));
        chanceWrappers.add(new MutationChanceWrapper(lrm, 0.5));

        TotalMutation tm = new TotalMutation(chanceWrappers);

        RectangleAtomCrossover rac = new RectangleAtomCrossover(true, 0.3);
        Tournament t = new Tournament(10);

        RectangleGenome[] start = new RectangleGenome[population];
        for(int i = 0; i < population; ++i){
            start[i] = RectangleGenome.random(rectangles, 0, b);
        }

        DrawFrame frame = new DrawFrame( b.width, b.height, 1);
        GeneticAlgorithm ga = new GeneticAlgorithm(lrm, rac, t, generations, e, start, minFitness, b, frame);

        SwingUtilities.invokeLater(() -> {
            frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        });

        RectangleGenome res = ga.run();
        res.dump(args[5]);
        GrayScaleImage finalImage = new GrayScaleImage(b.width, b.height);
        res.draw(finalImage);
        finalImage.save(new File(args[6]));
    }
}
