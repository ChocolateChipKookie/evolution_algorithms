package hr.fer.zemris.optjava.dz7;

import hr.fer.zemris.optjava.algorithms.neuralnetwork.IReadOnlyDataset;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Dataset implements IReadOnlyDataset {
    private int datasetSize;
    private DataSample[] data;

    public Dataset(List<String> inputs, int samplesPerPoint, int sampleSize){
        if(sampleSize == -1) sampleSize = inputs.size();
        this.datasetSize = sampleSize - samplesPerPoint;
        double[] dataPoints = new double[inputs.size()];
        for(int i = 0; i < inputs.size(); ++i){
            dataPoints[i] = Double.parseDouble(inputs.get(i));
        }

        double max = - Double.MAX_VALUE, min = Double.MAX_VALUE;
        for (double dataPoint : dataPoints) {
            if (dataPoint < min) min = dataPoint;
            if (dataPoint > max) max = dataPoint;
        }
        double diff = (max - min) / 2;
        for(int i = 0; i < dataPoints.length; ++i){
            dataPoints[i] -= min;
            dataPoints[i] /= diff;
            dataPoints[i] -= 1.;
        }

        this.data = new DataSample[sampleSize - samplesPerPoint];
        for(int i = 0; i < sampleSize - samplesPerPoint; ++i){
            double[] tmp = new double[samplesPerPoint];
            System.arraycopy(dataPoints, i, tmp, 0, samplesPerPoint);
            data[i] = new DataSample(tmp, dataPoints[i + samplesPerPoint]);
        }
    }

    public Dataset(String path, int samplesPerPoint, int sampleSize){
        Dataset tmp = Dataset.loadData(path, samplesPerPoint, sampleSize);
        this.datasetSize = tmp.datasetSize;
        this.data = tmp.data;
    }

    public static Dataset loadData(String path, int samplesPerPoint, int sampleSize) {
        List<String> inputs = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(path))){
            String s;
            while((s = br.readLine()) != null){
                inputs.add(s);
            }
        }
        catch(IOException ioe){
            ioe.printStackTrace();
        }
        return new Dataset(inputs, samplesPerPoint, sampleSize);
    }

    public DataSample getData(int i){
        return data[i];
    }

    public int size(){
        return datasetSize;
    }
}
