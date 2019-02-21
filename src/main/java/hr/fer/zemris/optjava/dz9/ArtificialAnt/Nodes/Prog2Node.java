package hr.fer.zemris.optjava.dz9.ArtificialAnt.Nodes;

import hr.fer.zemris.optjava.dz9.ArtificialAnt.Action;
import hr.fer.zemris.optjava.dz9.Node;

public class Prog2Node extends Node<Action, Boolean> {

    public Prog2Node(){
        super(2);
    }

    public Prog2Node(Node<Action, Boolean> c1, Node<Action, Boolean> c2){
        super(2);
        children[0] = c1;
        children[1] = c2;
    }

    @Override
    public Action evaluate() {
        for(Node<Action, Boolean> c : children){
            c.evaluate();
        }
        return null;
    }

    @Override
    public Node<Action, Boolean> clone() {
        return new Prog2Node(children[0].clone(), children[1].clone());
    }

    @Override
    public String toString(){
        return "Prog2(" + children[0].toString() + ", " + children[1].toString() + ")";
    }
}
