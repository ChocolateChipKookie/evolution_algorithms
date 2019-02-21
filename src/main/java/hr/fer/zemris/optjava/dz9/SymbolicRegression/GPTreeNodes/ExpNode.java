package hr.fer.zemris.optjava.dz9.SymbolicRegression.GPTreeNodes;

import hr.fer.zemris.optjava.dz9.Node;

public class ExpNode extends Node<Double, Double> {

    public ExpNode(){
        super(1);
    }

    public ExpNode(Node<Double, Double> c1){
        super(1);
        children[0] = c1;
    }

    @Override
    public Double evaluate() {
        return Math.exp(children[0].evaluate());
    }

    @Override
    public Node<Double, Double> clone() {
        return new ExpNode(children[0].clone());
    }

    @Override
    public String toString() {
        return "EXP(" + children[0].toString() + ")";
    }
}
