package hr.fer.zemris.optjava.dz9.SymbolicRegression.GPTreeNodes;

import hr.fer.zemris.optjava.dz9.Node;

public class InputNode extends Node<Double, Double>{

    public final int index;
    public double value;

    public InputNode(int index){
        super(0);
        this.index = index;
    }

    @Override
    public Double evaluate() {
        return value;
    }

    @Override
    public Node<Double, Double> clone() {
        return new InputNode(index);
    }

    @Override
    public String toString() {
        return "INPUT(" + index + ")";
    }
}
