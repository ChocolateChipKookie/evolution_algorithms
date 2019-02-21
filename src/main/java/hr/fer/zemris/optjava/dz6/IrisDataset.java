/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.fer.zemris.optjava.dz6;

import hr.fer.zemris.optjava.algorithms.neuralnetwork.IReadOnlyDataset;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Adi
 */
public class IrisDataset implements IReadOnlyDataset {
    
    private int datasetSize;
    private IrisData[] irisData; 
    
    public IrisDataset(List<String> inputs){
        this.datasetSize = inputs.size();
        irisData = new IrisData[datasetSize]; 
        for(int i = 0; i < datasetSize; ++i){
            irisData[i] = new IrisData(inputs.get(i));
        }
    }
    
    public IrisDataset(String path){
        IrisDataset tmp = loadData(path);
        this.datasetSize = tmp.datasetSize;
        this.irisData = tmp.irisData;
    }
    
    public static IrisDataset loadData(String path) {
        List<String> inputs = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(path));){
            String s;
            while((s = br.readLine()) != null){
                inputs.add(s);
            }
        }
        catch(IOException ioe){
            ioe.printStackTrace();
        }
        return new IrisDataset(inputs);
    }
    
    public IrisData getData(int i){
        return irisData[i];
    }
    
    public int size(){
        return datasetSize;
    }
}
