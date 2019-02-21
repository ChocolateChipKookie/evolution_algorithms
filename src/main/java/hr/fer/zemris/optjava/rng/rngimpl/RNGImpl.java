package hr.fer.zemris.optjava.rng.rngimpl;

import hr.fer.zemris.optjava.rng.IRNG;
import java.util.Random;

public class RNGImpl implements IRNG {

    private Random rand = new Random();

    @Override
    public double nextDouble() {
        return rand.nextDouble();
    }

    @Override
    public double nextDouble(double min, double max) {
        return rand.nextDouble() * (max - min) + min;
    }

    @Override
    public float nextFloat() {
        return rand.nextFloat();
    }

    @Override
    public float nextFloat(float min, float max) {
        return rand.nextFloat() * (max - min) + min;
    }

    @Override
    public int nextInt() {
        return rand.nextInt();
    }

    @Override
    public int nextInt(int min, int max) {
        return min + rand.nextInt(max - min);
    }

    @Override
    public boolean nextBoolean() {
        return rand.nextBoolean();
    }

    @Override
    public double nextGaussian() {
        return rand.nextGaussian();
    }
}
