package hr.fer.zemris.optjava.dz9;

import hr.fer.zemris.optjava.solutions.SingleObjectiveSolution;

public abstract class GPTree<O, I> extends SingleObjectiveSolution {

    protected Node<O, I> root;

    protected GPTree(){}

    public GPTree(Node<O, I> root){
        this.root = root;
    }

    public Node<O, I> getRoot() {
        return root;
    }

    public void setRoot(Node root){
        this.root = root;
    }

    public abstract O evaluate(I[] inputs);

    abstract public Node<O, I> getRandomNode();

    abstract public Node<O, I> getRandomTerminal();

    abstract public Node<O, I> getRandomNonTerminal();

    abstract public GPTree<O, I> clone();

}
