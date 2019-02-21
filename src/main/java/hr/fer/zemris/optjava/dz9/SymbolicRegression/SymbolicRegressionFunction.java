package hr.fer.zemris.optjava.dz9.SymbolicRegression;

import hr.fer.zemris.optjava.dz9.Function;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SymbolicRegressionFunction implements Function<SymbolicRegressionGPTree> {

    private int inputNumber;
    private List<DataStruct> data;


    public SymbolicRegressionFunction(String inputPath){
        data = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(inputPath))){
            inputNumber = br.readLine().split("\t").length - 1;

            String s;
            while((s = br.readLine()) != null){
                data.add(new DataStruct(s));
            }
        }
        catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    @Override
    public void evaluate(SymbolicRegressionGPTree tree) {
        double sum = 0;
        for(DataStruct d : data){
            double result = tree.evaluate(d.inputs) - d.output;
            sum += result*result;
        }
        tree.fitness = Math.sqrt(sum);
    }

    @Override
    public int numberOfInputs() {
        return inputNumber;
    }
}
