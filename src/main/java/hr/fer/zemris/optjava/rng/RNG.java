package hr.fer.zemris.optjava.rng;
import java.io.*;
import java.util.Properties;
public class RNG {
    private static IRNGProvider rngProvider;
    static {
        Properties p = new Properties();
        try(InputStream is = new FileInputStream("rng-config.properties")){
            p.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            rngProvider = (IRNGProvider) RNG.class.getClassLoader().loadClass(p.getProperty("rng-provider")).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static IRNG getRNG() {
        return rngProvider.getRNG();
    }
}