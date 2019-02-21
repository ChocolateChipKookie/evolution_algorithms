package hr.fer.zemris.optjava.dz9;

public interface Function<T extends GPTree> {

    void evaluate(T tree);

    int numberOfInputs();

}