package hr.fer.zemris.optjava.dz9;

public abstract class Node<O, I> {

    private int depth = -1;
    private int height = -1;
    private int subtreeSize;
    public Node<O, I>[] children;
    public Node<O, I> parent;


    public Node(int children){
        this.children = new Node[children];
    }

    abstract public O evaluate();

    abstract public Node<O, I> clone();

    public void grow(int depth, GPTree<O, I> tree){
        if(depth <= 1){
            for(int i =0; i < children.length; ++i){
                children[i] = tree.getRandomTerminal();
            }
        }else{
            for(int i =0; i < children.length; ++i){
                children[i] = tree.getRandomNode();
                children[i].grow(depth-1, tree);
            }
        }
    }

    public void full(int depth, GPTree<O, I> tree){
        if(depth <= 1){
            for(int i =0; i < children.length; ++i){
                children[i] = tree.getRandomTerminal();
            }
        }else{
            for(int i =0; i < children.length; ++i){
                children[i] = tree.getRandomNonTerminal();
                children[i].full(depth-1, tree);
            }
        }
    }

    public void switchChild(Node<O, I> child, Node<O, I> newChild){
        for(int i = 0; i < children.length; ++i){
            if(children[i] == child){
                children[i] = newChild;
                break;
            }
        }
    }

    public void linkParents(){
        for(Node child : children){
            child.parent = this;
            child.linkParents();
        }
    }

    //Depth

    public void calculateDepth(){
        if(parent == null) depth = 0;
        else{
            if(parent.depth == -1){
                parent.calculateDepth();
            }
            depth = parent.depth + 1;
        }
    }

    public void calculateSubtreeDepths(){
        calculateDepth();
        for (Node<O, I> child : children) {
            child.calculateSubtreeDepths();
        }
    }

    public int getDepth() {
        if(depth == -1){
            calculateDepth();
        }
        return depth;
    }

    //Height
    public void calculateHeight(){
        if(children.length == 0){
            height = 0;
        }
        else{
            int h = 0;
            for(Node<O, I> child : children){
                child.calculateHeight();
                if(child.getHeight() > h){
                    h = child.getHeight();
                }
            }
            height = h + 1;
        }
    }

    public int getHeight() {
        return height;
    }

    //SubtreeSize

    public void calculateSubtreeSize(){
        subtreeSize = 1;
        if(children.length != 0) {
            for(Node<O, I> child : children){
                child.calculateSubtreeSize();
                subtreeSize += child.subtreeSize;
            }
        }
    }

    public int getSubtreeSize() {
        return subtreeSize;
    }
}
