package hr.fer.zemris.optjava.dz9;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SubtreeSwitchMutation<O, I>{

    private int maxDepth;
    private int maxSize;
    private int maxNewDepth;
    private Random rand;



    public SubtreeSwitchMutation(int maxDepth, int maxSize, int maxNewDepth, Random rand){
        this.maxDepth = maxDepth;
        this.maxSize = maxSize;
        this.maxNewDepth = maxNewDepth;
        this.rand = rand;
    }

    public GPTree<O, I> mutate(GPTree<O, I> tree){

        GPTree<O, I> res = tree.clone();
        Node<O, I> root = res.root;
        root.linkParents();
        root.calculateSubtreeSize();
        root.calculateSubtreeDepths();

        List<Node<O, I>> list = new ArrayList<>();
        fillList(root, list);

        Node<O, I> toBeSwapped = list.get(rand.nextInt(list.size()));

        int maxNewSize = maxSize - root.getSubtreeSize() + toBeSwapped.getSubtreeSize();
        int maxNewDepth = maxDepth - toBeSwapped.getDepth();

        Node<O, I> swapee = null;

        for(int i = 0; i < 10; ++i){
            swapee = tree.getRandomNode();

            swapee.grow(1 + rand.nextInt(maxNewDepth), tree);
            swapee.calculateSubtreeSize();
            swapee.calculateHeight();
            if(swapee.getSubtreeSize() < maxNewSize && swapee.getHeight()< maxNewDepth){
                break;
            }
            swapee = null;
        }

        if(swapee != null){
            if(toBeSwapped.parent != null){
                toBeSwapped.parent.switchChild(toBeSwapped, swapee);
                toBeSwapped.parent.linkParents();
            }
            else{
                res.setRoot(swapee);
            }
        }

        return res;
    }

    private void fillList(Node<O, I> node, List<Node<O, I>> list){
        list.add(node);
        for(Node<O, I> child : node.children) {
            fillList(child, list);
        }
    }
}
