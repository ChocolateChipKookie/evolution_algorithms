package hr.fer.zemris.optjava.dz6;

import hr.fer.zemris.optjava.algorithms.neuralnetwork.IImuneSelection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ImuneSelection implements IImuneSelection<CloneSolution> {

    @Override
    public CloneSolution[] select(CloneSolution[] currentIndividuals, int selected) {
        CloneSolution[] result = new CloneSolution[selected];
        List<CloneSolution> solutions = new ArrayList<>();

        Collections.addAll(solutions, currentIndividuals);

        solutions.sort((CloneSolution t, CloneSolution t1) -> {
            if(t.value > t1.value) return 1;
            else if(t.value < t1.value) return -1;
            else return 0;
        });

        for(int i = 0; i < result.length; ++i){
            result[i] = solutions.get(i);
        }

        return result;
    }
}
