package hr.fer.zemris.optjava.dz9.SymbolicRegression.GPTreeNodes;

import hr.fer.zemris.optjava.dz9.Node;

public class LogNode extends Node<Double, Double> {

    public LogNode(){
        super(1);
    }

    public LogNode(Node<Double, Double> c1){
        super(1);
        children[0] = c1;
    }

    @Override
    public Double evaluate() {
        double res = children[0].evaluate();
        if(res <= 0) return 1.;
        return Math.log10(res);
    }

    @Override
    public Node<Double, Double> clone() {
        return new LogNode(children[0].clone());
    }

    @Override
    public String toString() {
        return "LOG(" + children[0].toString() + ")";
    }
}
