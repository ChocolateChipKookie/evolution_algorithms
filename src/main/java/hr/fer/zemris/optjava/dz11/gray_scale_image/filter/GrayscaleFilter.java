package hr.fer.zemris.optjava.dz11.gray_scale_image.filter;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

public class GrayscaleFilter extends AbstractFilter{

    private static double RED_PART = 0.2126;
    private static double GREEN_PART = 0.7152;
    private static double BLUE_PART = 0.0722;

    @Override
    public BufferedImage filter(BufferedImage src, BufferedImage dest) {
        if (src.getType() == BufferedImage.TYPE_BYTE_GRAY) {
            dest = src;
            return dest;
        }

        if (dest == null) {
            dest = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        }

        final int width = src.getWidth();
        final int height = src.getHeight();

        int[] inPixels = new int[width * height];
        getPixels(src, 0, 0, width, height, inPixels);
        byte[] outPixels = doFilter(inPixels);
        setPixels(dest, 0, 0, width, height, outPixels);
        return dest;
    }

    private byte[] doFilter(int[] inputPixels) {
        int red, green, blue;
        int i = 0;
        byte[] outPixels = new byte[inputPixels.length];

        for(int pixel : inputPixels) {
            red   = (pixel >> 16) & 0xFF;
            green = (pixel >> 8) & 0xFF;
            blue  = pixel & 0xFF;

            outPixels[i++] = (byte)(red * RED_PART + green * GREEN_PART + blue * BLUE_PART);
        }
        return outPixels;
    }

    private static int[] getPixels(BufferedImage img, int x, int y, int w, int h, int[] pixels) {
        if (w == 0 || h == 0) {
            return new int[0];
        }
        if (pixels == null) {
            pixels = new int[w * h];
        } else if (pixels.length < w * h) {
            throw new IllegalArgumentException("pixels array must have a length >= w*h");
        }

        int imageType = img.getType();
        if (imageType == BufferedImage.TYPE_INT_ARGB ||
                imageType == BufferedImage.TYPE_INT_RGB) {
            Raster raster = img.getRaster();
            return (int[]) raster.getDataElements(x, y, w, h, pixels);
        }

        return img.getRGB(x, y, w, h, pixels, 0, w);
    }

    private static void setPixels(BufferedImage img, int x, int y, int width, int height, byte[] pixels) {
        int[] buf = new int[1];
        WritableRaster r = img.getRaster();
        int index=0;
        for(int h = 0; h < height; h++) {
            for(int w = 0; w < width; w++) {
                buf[0] = (int)pixels[index] & 0xFF;
                r.setPixel(w, h, buf);
                index++;
            }
        }
    }

}
