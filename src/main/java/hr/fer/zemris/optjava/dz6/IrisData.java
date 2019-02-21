/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.fer.zemris.optjava.dz6;

/**
 *
 * @author Adi
 */
public class IrisData{
    public final double[] inputs;
    public final double[] outputs;
    
    public IrisData(String input){
        String[] insNouts = input.split(":");
        insNouts[0] = insNouts[0].substring(1, insNouts[0].length() - 1);
        insNouts[1] = insNouts[1].substring(1, insNouts[1].length() - 1);
        String[] ins = insNouts[0].split(",");
        String[] outs = insNouts[1].split(",");
        
        inputs = new double[ins.length];
        outputs = new double[outs.length];
        
        for(int i = 0; i < inputs.length; ++i){
            inputs[i] = Double.parseDouble(ins[i]);
        }
        for(int i = 0; i < outputs.length; ++i){
            outputs[i] = Double.parseDouble(outs[i]);
        }
    }
}
