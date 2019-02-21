package hr.fer.zemris.optjava.dz11.algorithm;

import hr.fer.zemris.optjava.dz11.gray_scale_image.GrayScaleImage;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Test {

    public static void main(String[] args) throws IOException {
        String[] pokArgs = new String[7];
        pokArgs[0] = "11.slike\\Maslina\\maslina.jpg";
        pokArgs[1] = "4000";
        pokArgs[2] = "125";
        pokArgs[3] = "200000";
        pokArgs[4] = "-0";
        String txt = "maslina-res";
        String result = "11.slike\\Maslina\\maslina-res";

        for(int i = 1; i < 15; ++i){
            System.out.println(i);
            pokArgs[5] = txt + i + ".txt";
            pokArgs[6] = result + i + ".jpg";
            Pokretac2.main(pokArgs);
        }
    }

}
