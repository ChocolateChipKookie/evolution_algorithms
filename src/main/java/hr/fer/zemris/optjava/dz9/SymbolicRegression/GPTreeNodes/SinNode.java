package hr.fer.zemris.optjava.dz9.SymbolicRegression.GPTreeNodes;

import hr.fer.zemris.optjava.dz9.Node;

public class SinNode extends Node<Double, Double> {

    public SinNode(){
        super(1);
    }

    public SinNode(Node<Double, Double> c1){
        super(1);
        children[0] = c1;
    }

    @Override
    public Double evaluate() {
        return Math.sin(children[0].evaluate());
    }

    @Override
    public Node<Double, Double> clone() {
        return new SinNode(children[0].clone());
    }

    @Override
    public String toString() {
        return "SIN(" + children[0].toString() + ")";
    }
}
