package hr.fer.zemris.optjava.dz9.ArtificialAnt.Nodes;

import hr.fer.zemris.optjava.dz9.ArtificialAnt.Action;
import hr.fer.zemris.optjava.dz9.ArtificialAnt.InputWrapper;
import hr.fer.zemris.optjava.dz9.Node;

public class IfFoodAhead extends Node<Action, Boolean> {

    private InputWrapper foodAhead;

    public IfFoodAhead(InputWrapper foodAhead){
        super(2);
        this.foodAhead = foodAhead;
    }

    public IfFoodAhead(Node<Action, Boolean> c1, Node<Action, Boolean> c2, InputWrapper foodAhead){
        super(2);
        children[0] = c1;
        children[1] = c2;
        this.foodAhead = foodAhead;
    }

    @Override
    public Action evaluate() {
        if(foodAhead.foodAhead){
            children[0].evaluate();
        }
        else{
            children[1].evaluate();
        }
        return null;
    }

    @Override
    public Node<Action, Boolean> clone() {
        return new IfFoodAhead(children[0].clone(), children[1].clone(), foodAhead);
    }

    @Override
    public String toString(){
        return "IfFoodAhead(" + children[0].toString() + ", " + children[1].toString() + ")";
    }
}
