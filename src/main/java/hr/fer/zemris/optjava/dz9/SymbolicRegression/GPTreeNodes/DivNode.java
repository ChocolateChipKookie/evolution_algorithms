package hr.fer.zemris.optjava.dz9.SymbolicRegression.GPTreeNodes;

import hr.fer.zemris.optjava.dz9.Node;

public class DivNode extends Node<Double, Double>{

    public DivNode(){
        super(2);
    }

    public DivNode(Node<Double, Double> c1, Node<Double, Double> c2){
        super(2);
        children[0] = c1;
        children[1] = c2;
    }

    @Override
    public Double evaluate() {
        double res = children[1].evaluate();
        if(res == 0) return 1.;
        return children[0].evaluate() / children[1].evaluate();
    }

    @Override
    public Node<Double, Double> clone() {
        return new DivNode(children[0].clone(), children[1].clone());
    }

    @Override
    public String toString() {
        return "DIV(" + children[0].toString() + ", " + children[1].toString() + ")";
    }
}
