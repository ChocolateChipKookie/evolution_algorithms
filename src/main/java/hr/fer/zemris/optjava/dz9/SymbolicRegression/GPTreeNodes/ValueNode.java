package hr.fer.zemris.optjava.dz9.SymbolicRegression.GPTreeNodes;

import hr.fer.zemris.optjava.dz9.Node;

public class ValueNode extends Node<Double, Double> {

    public double value;

    public ValueNode(double value){
        super(0);
        this.value = value;
    }

    @Override
    public Double evaluate() {
        return value;
    }

    @Override
    public Node<Double, Double> clone() {
        return new ValueNode(value);
    }

    @Override
    public String toString() {
        return "VALUE(" + value + ")";
    }
}
