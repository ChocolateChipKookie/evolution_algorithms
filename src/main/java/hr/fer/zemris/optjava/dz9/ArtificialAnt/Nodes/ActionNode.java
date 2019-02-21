package hr.fer.zemris.optjava.dz9.ArtificialAnt.Nodes;

import hr.fer.zemris.optjava.dz9.ArtificialAnt.Action;
import hr.fer.zemris.optjava.dz9.ArtificialAnt.PlayingField;
import hr.fer.zemris.optjava.dz9.Node;

public class ActionNode extends Node<Action, Boolean> {

    private Action action;
    private PlayingField playingField;

    public ActionNode(Action action, PlayingField playingField) {
        super(0);
        this.action = action;
        this.playingField = playingField;
    }

    @Override
    public Action evaluate() {
        playingField.move(action);
        return null;
    }

    @Override
    public Node<Action, Boolean> clone() {
        return new ActionNode(action, playingField);
    }

    @Override
    public String toString(){
        return action.toString();
    }

}
