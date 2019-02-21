package hr.fer.zemris.optjava.dz9;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Config {

    public String functionNodes;
    public String constantRange;
    public int populationSize;
    public int tournamentSize;
    public int iterations;
    public double crossover;
    public double mutation;
    public double reproduction;
    public int maxStartDepth;
    public int maxDepth;
    public int maxSize;

    public Config(String path){
        try(BufferedReader br = new BufferedReader(new FileReader(path))){
            functionNodes = br.readLine().split(":")[1].trim();
            constantRange = br.readLine().split(":")[1].trim();
            populationSize = Integer.parseInt(br.readLine().split(":")[1].trim());
            tournamentSize = Integer.parseInt(br.readLine().split(":")[1].trim());
            iterations = Integer.parseInt(br.readLine().split(":")[1].trim());
            crossover = Double.parseDouble(br.readLine().split(":")[1].trim());
            mutation = Double.parseDouble(br.readLine().split(":")[1].trim());
            reproduction = Double.parseDouble(br.readLine().split(":")[1].trim());
            maxStartDepth = Integer.parseInt(br.readLine().split(":")[1].trim());
            maxDepth = Integer.parseInt(br.readLine().split(":")[1].trim());
            maxSize = Integer.parseInt(br.readLine().split(":")[1].trim());
        }
        catch(IOException ioe){
            ioe.printStackTrace();
        }
    }
}
