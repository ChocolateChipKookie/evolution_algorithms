package hr.fer.zemris.optjava.dz11.task;

import hr.fer.zemris.optjava.dz11.gray_scale_image.GrayScaleImage;
import hr.fer.zemris.optjava.dz11.genetic_algorithm.RectangleGenome;
import java.util.List;

public class EvaluationTask extends Task{
    private ThreadLocal<GrayScaleImage> canvasRetailer;
    private Evaluator evaluator;
    private List<RectangleGenome> genomes;

    public EvaluationTask(Evaluator evaluator, List<RectangleGenome> genomes){
        this.evaluator = evaluator;
        canvasRetailer = ThreadLocal.withInitial(() -> new GrayScaleImage(evaluator.image.getWidth(), evaluator.image.getHeight()));
        this.genomes = genomes;
    }

    @Override
    public Result work() {
        GrayScaleImage canvas = canvasRetailer.get();

        for(RectangleGenome g : genomes){
            int[] genome = g.getGenome();
            g.draw(canvas);
            g.fitness = -(double) Evaluator.evaluate(evaluator.image, canvas);
        }
        return new Result(genomes);
    }
}
