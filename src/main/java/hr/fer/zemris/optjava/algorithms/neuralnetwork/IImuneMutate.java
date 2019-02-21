package hr.fer.zemris.optjava.algorithms.neuralnetwork;

public interface IImuneMutate<T> {

    T mutate(T child, double mutationFactor);
}
