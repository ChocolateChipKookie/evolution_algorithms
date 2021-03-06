package hr.fer.zemris.optjava.dz9.SymbolicRegression.GPTreeNodes;

import hr.fer.zemris.optjava.dz9.Node;

public class AddNode extends Node<Double, Double> {

    public AddNode(){
        super(2);
    }

    public AddNode(Node<Double, Double> c1, Node<Double, Double> c2){
        super(2);
        children[0] = c1;
        children[1] = c2;
    }

    @Override
    public Double evaluate() {
        return children[0].evaluate() + children[1].evaluate();
    }

    @Override
    public Node<Double, Double> clone() {
        return new AddNode(children[0].clone(), children[1].clone());
    }

    @Override
    public String toString() {
        return "ADD(" + children[0].toString() + ", " + children[1].toString() + ")";
    }
}
