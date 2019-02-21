package hr.fer.zemris.optjava.dz9.ArtificialAnt;

import hr.fer.zemris.optjava.dz9.ArtificialAnt.Nodes.*;
import hr.fer.zemris.optjava.dz9.GPTree;
import hr.fer.zemris.optjava.dz9.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class ArtificialAntGPTree extends GPTree<Action, Boolean> {

    private Random rand;

    public ArtificialAntGPTree(int depth, boolean full, PlayingField playingField, Random rand){
        terminalSupplier = new ArrayList<>();
        nonTerminalSupplier = new ArrayList<>();
        generalSupplier= new ArrayList<>();

        terminalSupplier.add(() -> new ActionNode(Action.Left, playingField));
        terminalSupplier.add(() -> new ActionNode(Action.Right, playingField));
        terminalSupplier.add(() -> new ActionNode(Action.Move, playingField));

        inputWrapper = new InputWrapper();
        nonTerminalSupplier.add(() -> new IfFoodAhead(inputWrapper));
        nonTerminalSupplier.add(Prog2Node::new);
        nonTerminalSupplier.add(Prog3Node::new);
        this.rand = rand;

        generalSupplier = new ArrayList<>();
        generalSupplier.addAll(terminalSupplier);
        generalSupplier.addAll(nonTerminalSupplier);

        root = getRandomNonTerminal();

        if(full){
            root.full(depth - 1, this);
        }
        else{
            root.grow(depth - 1, this);
        }
    }

    private ArtificialAntGPTree(){}

    private InputWrapper inputWrapper;

    @Override
    public Action evaluate(Boolean[] inputs) {
        return root.evaluate();
    }


    public InputWrapper getInputWrapper(){
        return inputWrapper;
    }

    private List<Supplier<Node<Action, Boolean>>> terminalSupplier;

    @Override
    public Node<Action, Boolean> getRandomTerminal() {
        return terminalSupplier.get(rand.nextInt(terminalSupplier.size())).get();
    }

    private  List<Supplier<Node<Action, Boolean>>> nonTerminalSupplier;

    @Override
    public Node<Action, Boolean> getRandomNonTerminal() {
        return nonTerminalSupplier.get(rand.nextInt(nonTerminalSupplier.size())).get();
    }

    @Override
    public ArtificialAntGPTree clone() {

        ArtificialAntGPTree res = new ArtificialAntGPTree();
        res.generalSupplier = this.generalSupplier;
        res.nonTerminalSupplier = this.nonTerminalSupplier;
        res.terminalSupplier = this.terminalSupplier;
        res.rand = this.rand;
        res.root = root.clone();
        res.inputWrapper = this.inputWrapper;
        res.fitness = this.fitness;
        return res;
    }

    private List<Supplier<Node<Action, Boolean>>> generalSupplier;

    @Override
    public Node<Action, Boolean> getRandomNode() {
        return generalSupplier.get(rand.nextInt(generalSupplier.size())).get();
    }

    @Override
    public String toString() {
        return root.toString();
    }
}
