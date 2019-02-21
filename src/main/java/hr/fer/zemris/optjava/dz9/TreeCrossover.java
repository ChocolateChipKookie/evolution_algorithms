package hr.fer.zemris.optjava.dz9;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TreeCrossover<O, I> {

    private int maxDepth;
    private int maxSize;
    private Random rand;


    public TreeCrossover(int maxDepth, int maxSize, Random rand){
        this.maxDepth = maxDepth;
        this.maxSize = maxSize;
        this.rand = rand;
    }

    public GPTree<O, I> crossover(GPTree<O, I>[] parents) {

        GPTree<O, I> acceptor = parents[0].clone();
        GPTree<O, I> donor = parents[1];
        Node<O, I> result = acceptor.root;
        result.linkParents();
        Node<O, I> donorRoot = donor.getRoot();
        donorRoot.calculateSubtreeSize();
        donorRoot.calculateSubtreeDepths();

        List<Node<O, I>> list = new ArrayList<>();
        fillList(result, list);

        List<Node<O, I>> candidates = new ArrayList<>();
        Node<O, I> toBeSwapped = null;
        do{
            toBeSwapped= list.get(rand.nextInt(list.size()));
            result.calculateSubtreeSize();
            int maxNewSize = maxSize - result.getSubtreeSize() + toBeSwapped.getSubtreeSize();
            int maxNewDepth = maxDepth - toBeSwapped.getDepth();

            donorRoot.calculateHeight();
            getCandidates(candidates, donorRoot, maxNewDepth, maxNewSize);

        }while (candidates.size() == 0);
        Node<O, I> swapee = candidates.get(rand.nextInt(candidates.size())).clone();

        if(toBeSwapped.parent == null){
            acceptor.setRoot(swapee);
            return acceptor;
        }

        toBeSwapped.parent.switchChild(toBeSwapped, swapee);
        toBeSwapped.parent.linkParents();

        return acceptor;
    }

    private void getCandidates(List<Node<O, I>> candidates, Node<O, I> currentNode, int maxNewDepth, int maxNewSize){

        if(currentNode.getSubtreeSize() < maxNewSize && currentNode.getHeight()< maxNewDepth){
            candidates.add(currentNode);
        }
        for(Node<O, I> child : currentNode.children) {
            getCandidates(candidates, child, maxNewDepth, maxNewSize);
        }
    }

    private void fillList(Node<O, I> node, List<Node<O, I>> list){
        list.add(node);
        for(Node<O, I> child : node.children) {
            fillList(child, list);
        }
    }

}
