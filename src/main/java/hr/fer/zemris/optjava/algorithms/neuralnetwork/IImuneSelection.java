package hr.fer.zemris.optjava.algorithms.neuralnetwork;

public interface IImuneSelection<T> {

    public T[] select(T[] currentIndividuals, int selected);

}
