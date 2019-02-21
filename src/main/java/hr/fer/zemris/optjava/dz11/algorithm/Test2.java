package hr.fer.zemris.optjava.dz11.algorithm;

import hr.fer.zemris.optjava.dz11.gray_scale_image.GrayScaleImage;

import java.io.File;
import java.io.IOException;

public class Test2 {

    public static void main(String[] args) throws IOException {
        interpolate(new String[]{
                "11.slike\\Maslina\\maslina-res0.jpg",
                "11.slike\\Maslina\\maslina-res1.jpg",
                "11.slike\\Maslina\\maslina-res2.jpg",
                "11.slike\\Maslina\\maslina-res3.jpg",
                "11.slike\\Maslina\\maslina-res4.jpg",
                "11.slike\\Maslina\\maslina-res5.jpg",
                "11.slike\\Maslina\\maslina-res6.jpg",
                "11.slike\\Maslina\\maslina-res7.jpg",
                "11.slike\\Maslina\\maslina-res8.jpg",
                "11.slike\\Maslina\\maslina-res9.jpg",
                "11.slike\\Maslina\\maslina-res10.jpg",
                "11.slike\\Maslina\\maslina-res11.jpg",
                "11.slike\\Maslina\\maslina-res12.jpg",
                "11.slike\\Maslina\\maslina-res13.jpg",
                "11.slike\\Maslina\\maslina-res14.jpg"});
    }

    public static void interpolate(String[] names) throws IOException {

        GrayScaleImage image = GrayScaleImage.load(new File(names[0]));
        int[] data = new int[image.getData().length];

        for(String name : names) {
            GrayScaleImage tmp = GrayScaleImage.load(new File(name));
            byte[] d = tmp.getData();
            for (int i = 0; i < data.length; ++i) {
                data[i] += d[i] & 0xFF;
            }
        }

        for (int i = 0; i < data.length; ++i) {
            data[i] /= names.length;
        }

        byte[] imData = image.getData();
        for (int i = 0; i < data.length; ++i) {
            imData[i] = (byte) data[i];
        }

        image.save(new File("11.slike\\Maslina\\sve1.png"));
    }
}
