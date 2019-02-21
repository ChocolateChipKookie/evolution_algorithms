package hr.fer.zemris.optjava.dz9.SymbolicRegression;

import hr.fer.zemris.optjava.dz9.GPTree;
import hr.fer.zemris.optjava.dz9.SymbolicRegression.GPTreeNodes.*;
import hr.fer.zemris.optjava.dz9.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class SymbolicRegressionGPTree extends GPTree<Double, Double> {

    private Random rand;

    public SymbolicRegressionGPTree(String operators, String constantRange, int inputs, int depth, boolean full, Random rand){
        terminalSupplier = new ArrayList<>();
        nonTerminalSupplier = new ArrayList<>();
        generalSupplier= new ArrayList<>();

        String[] operator = operators.split(",");
        for (String op: operator) {
            switch (op.trim()){
                case "+":
                    nonTerminalSupplier.add(AddNode::new);
                    generalSupplier.add(AddNode::new);
                    break;
                case "-":
                    nonTerminalSupplier.add(SubNode::new);
                    generalSupplier.add(SubNode::new);
                    break;
                case "*":
                    nonTerminalSupplier.add(MulNode::new);
                    generalSupplier.add(MulNode::new);
                    break;
                case "/":
                    nonTerminalSupplier.add(DivNode::new);
                    generalSupplier.add(DivNode::new);
                    break;
                case "sin":
                    nonTerminalSupplier.add(SinNode::new);
                    generalSupplier.add(SinNode::new);
                    break;
                case "cos":
                    nonTerminalSupplier.add(CosNode::new);
                    generalSupplier.add(CosNode::new);
                    break;
                case "sqrt":
                    nonTerminalSupplier.add(SqrtNode::new);
                    generalSupplier.add(SqrtNode::new);
                    break;
                case "log":
                    nonTerminalSupplier.add(LogNode::new);
                    generalSupplier.add(LogNode::new);
                    break;
                case "exp":
                    nonTerminalSupplier.add(ExpNode::new);
                    generalSupplier.add(ExpNode::new);
                    break;
            }
        }

        if(!constantRange.trim().equals("N/A")){
            String[] range = constantRange.split(",");

            double lowerBound = Double.parseDouble(range[0].trim());
            double upperBound = Double.parseDouble(range[1].trim());

            Supplier<Node<Double, Double>> valueNodeSupplier = () -> new ValueNode(rand.nextDouble() * (upperBound - lowerBound) + lowerBound);
            terminalSupplier.add(valueNodeSupplier);
            generalSupplier.add(valueNodeSupplier);
        }

        Supplier<Node<Double, Double>> inputNodeSupplier = () -> new InputNode(rand.nextInt(inputs));
        terminalSupplier.add(inputNodeSupplier);
        generalSupplier.add(inputNodeSupplier);

        this.rand = rand;

        root = getRandomNonTerminal();
        if(full){
            root.full(depth - 1, this);
        }
        else{
            root.grow(depth - 1, this);
        }
    }

    private SymbolicRegressionGPTree(){}

    @Override
    public Double evaluate(Double[] inputs) {
        setInputs(inputs, root);
        return root.evaluate();
    }

    private static void setInputs(Double[] inputs, Node node){
        if(node instanceof InputNode){
            InputNode n = (InputNode) node;
            n.value = inputs[n.index];
        }

        for(Node c : node.children){
            setInputs(inputs, c);
        }
    }

    private List<Supplier<Node<Double, Double>>> terminalSupplier;

    @Override
    public Node<Double, Double> getRandomTerminal() {
        return terminalSupplier.get(rand.nextInt(terminalSupplier.size())).get();
    }

    private  List<Supplier<Node<Double, Double>>> nonTerminalSupplier;

    @Override
    public Node<Double, Double> getRandomNonTerminal() {
        return nonTerminalSupplier.get(rand.nextInt(nonTerminalSupplier.size())).get();
    }

    @Override
    public SymbolicRegressionGPTree clone() {

        SymbolicRegressionGPTree res = new SymbolicRegressionGPTree();
        res.generalSupplier = this.generalSupplier;
        res.nonTerminalSupplier = this.nonTerminalSupplier;
        res.terminalSupplier = this.terminalSupplier;
        res.rand = this.rand;
        res.root = root.clone();

        return res;
    }

    private List<Supplier<Node<Double, Double>>> generalSupplier;

    @Override
    public Node<Double, Double> getRandomNode() {
        return generalSupplier.get(rand.nextInt(generalSupplier.size())).get();
    }

    @Override
    public String toString() {
        return root.toString();
    }

}
