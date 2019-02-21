package hr.fer.zemris.optjava.dz9.SymbolicRegression.GPTreeNodes;

import hr.fer.zemris.optjava.dz9.Node;

public class SqrtNode extends Node<Double, Double> {

    public SqrtNode(){
        super(1);
    }

    public SqrtNode(Node<Double, Double> c1){
        super(1);
        children[0] = c1;
    }

    @Override
    public Double evaluate() {
        double res = children[0].evaluate();
        if(res < 0 ) return 1.;
        return Math.sqrt(res);
    }

    @Override
    public Node<Double, Double> clone() {
        return new SqrtNode(children[0].clone());
    }

    @Override
    public String toString() {
        return "SQRT(" + children[0].toString() + ")";
    }
}
