package hr.fer.zemris.optjava.dz9.SymbolicRegression;

public class DataStruct {

    public Double[] inputs;
    public double output;

    public DataStruct(String s){
        String[] tmp = s.split("\t");
        inputs = new Double[tmp.length - 1];

        for(int i = 0; i < tmp.length - 1; ++i){
            inputs[i] = Double.parseDouble(tmp[i]);
        }
        output = Double.parseDouble(tmp[tmp.length - 1]);
    }

}
