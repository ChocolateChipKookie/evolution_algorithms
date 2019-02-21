package hr.fer.zemris.optjava.dz9.SymbolicRegression.GPTreeNodes;

import hr.fer.zemris.optjava.dz9.Node;

public class CosNode extends Node<Double, Double> {

    public CosNode(){
        super(1);
    }

    public CosNode(Node<Double, Double> c1){
        super(1);
        children[0] = c1;
    }

    @Override
    public Double evaluate() {
        return Math.cos(children[0].evaluate());
    }

    @Override
    public Node<Double, Double> clone() {
        return new CosNode(children[0].clone());
    }

    @Override
    public String toString() {
        return "COS(" + children[0].toString() + ")";
    }


}
