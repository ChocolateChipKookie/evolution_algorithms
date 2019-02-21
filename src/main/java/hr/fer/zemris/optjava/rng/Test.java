package hr.fer.zemris.optjava.rng;

import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;
import hr.fer.zemris.optjava.rng.provimpl.ThreadLocalRNGProvider;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Test {
    public static void main(String[] args) throws Exception{
        Runnable job = new Runnable() {
            @Override
            public void run() {
                IRNG rng = RNG.getRNG();
                for(int i = 0; i < 20; i++) {
                    System.out.println(rng.nextInt(-5, 5));
                }
            }
        };
        EVOThread thread = new EVOThread(job);
        thread.start();
    }
}
