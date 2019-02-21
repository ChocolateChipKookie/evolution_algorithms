package hr.fer.zemris.optjava.dz11.task;

import hr.fer.zemris.optjava.dz11.gray_scale_image.GrayScaleImage;

import java.io.File;
import java.io.IOException;

public class Evaluator {

    public final GrayScaleImage image;

    public Evaluator(String path) throws IOException{
        image = GrayScaleImage.load(new File(path));
    }

    public static int evaluate(GrayScaleImage image, GrayScaleImage test){
        byte[] imageData = image.getData();
        byte[] data = test.getData();
        int width = test.getWidth();
        int totalDiff = 0;
        for(int i = 0; i < test.getHeight(); ++i){
            for(int j = 0; j < test.getWidth(); ++j){
                totalDiff += Math.abs(((int)data[i * width + j] & 0xFF) - ((int)imageData[i * width + j] & 0xFF));
            }
        }
        return totalDiff;
    }


}
