package hr.fer.zemris.optjava.dz11.genetic_algorithm;

import hr.fer.zemris.optjava.dz11.gray_scale_image.Bounds;
import hr.fer.zemris.optjava.dz11.gray_scale_image.GrayScaleImage;
import hr.fer.zemris.optjava.dz11.task.Evaluator;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;
import hr.fer.zemris.optjava.solutions.SingleObjectiveSolution;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class RectangleGenome extends SingleObjectiveSolution {

    private int[] genome;

    public final int rectangles;
    public int currentRectangles = 10;

    public RectangleGenome(int rectangles, int startRectangles){
        this.rectangles = rectangles;
        this.currentRectangles = startRectangles;
        genome = new int[rectangles * 5 + 1];
    }

    private RectangleGenome(RectangleGenome parent){
        this.rectangles = parent.rectangles;
        this.genome = parent.genome.clone();
        this.currentRectangles = parent.currentRectangles;
    }

    public RectangleGenome clone(){
        return new RectangleGenome(this);
    }

    public int[] getGenome(){
        return genome;
    }

    public void setRandom(int pos, Bounds bounds){

        if(pos < currentRectangles){
            pos *= 5;
            IRNG rand = RNG.getRNG();
            genome[pos] = rand.nextInt(0, bounds.width);
            genome[pos + 1] = rand.nextInt(0, bounds.height);
            genome[pos + 2] = rand.nextInt(0, bounds.width - genome[pos]);
            genome[pos + 3] = rand.nextInt(0, bounds.height - genome[pos + 1]);
            genome[pos + 4] = rand.nextInt(0, 255);
        }
    }

    public void incCurrentRectangles(){
        if(currentRectangles < rectangles)currentRectangles++;
    }

    public boolean hasMaxRectangles(){
        return rectangles == currentRectangles;
    }

    public void dump(String path){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write("Background: ");
            writer.write(String.valueOf(genome[genome.length - 1]));
            writer.newLine();
            for(int i = 0; i < rectangles; ++i){
                int pos = 5*i;
                writer.write("(" + genome[pos] + ", " + genome[pos + 1] + ", " + genome[pos + 2] + ", " + genome[pos + 3] + ", " + genome[pos + 4] + ")\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(GrayScaleImage image){
        image.clear((byte) genome[genome.length - 1]);

        for(int i = 0; i < rectangles; ++i){
            int pos = i*5;
            image.rectangle(genome[pos], genome[pos + 1], genome[pos + 2], genome[pos + 3], (byte) genome[pos + 4]);
        }
    }

    public static RectangleGenome random(int rectangles, int startRectangles, Bounds bounds){
        RectangleGenome res = new RectangleGenome(rectangles, startRectangles);
        IRNG rand = RNG.getRNG();
        for(int i = 0; i < startRectangles; ++i){
            res.genome[5*i] = rand.nextInt(0, bounds.width);
            res.genome[5*i + 1] = rand.nextInt(0, bounds.height);
            res.genome[5*i + 2] = rand.nextInt(0, bounds.width - res.genome[5*i + 1]);
            res.genome[5*i + 3] = rand.nextInt(0, bounds.height - res.genome[5*i + 1]);
            res.genome[5*i + 4] = rand.nextInt(0, 255);
        }
        return res;
    }
}
