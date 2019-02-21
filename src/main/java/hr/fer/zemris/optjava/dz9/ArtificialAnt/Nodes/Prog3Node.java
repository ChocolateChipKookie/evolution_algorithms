package hr.fer.zemris.optjava.dz9.ArtificialAnt.Nodes;

import hr.fer.zemris.optjava.dz9.ArtificialAnt.Action;
import hr.fer.zemris.optjava.dz9.Node;

public class Prog3Node extends Node<Action, Boolean> {

    public Prog3Node(){
        super(3);
    }

    public Prog3Node(Node<Action, Boolean> c1, Node<Action, Boolean> c2, Node<Action, Boolean> c3){
        super(3);
        children[0] = c1;
        children[1] = c2;
        children[2] = c3;
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
        return new Prog3Node(children[0].clone(), children[1].clone(), children[2].clone());
    }

    @Override
    public String toString(){
        return "Prog3(" + children[0].toString() + ", " + children[1].toString() + ", " + children[2].toString() + ")";
    }

}
