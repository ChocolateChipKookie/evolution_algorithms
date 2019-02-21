package hr.fer.zemris.optjava.dz6;
import hr.fer.zemris.optjava.algorithms.neuralnetwork.IFunction;
import hr.fer.zemris.optjava.algorithms.neuralnetwork.IInertia;
import hr.fer.zemris.optjava.algorithms.neuralnetwork.IOptAlgorithm;
import hr.fer.zemris.optjava.algorithms.neuralnetwork.WeightSolution;

import java.util.Random;
import java.util.stream.IntStream;

public class ParticleSwarmOptimisation implements IOptAlgorithm<WeightSolution> {

    private int neighbourhood;
    private boolean globalNeighbourhood;
    private double c1, c2;
    private IInertia inertiaFunc;
    private IFunction func;
    private Particle[] particles;
    private SolutionBoundaries boundaries;
    private Random rand;
    private double acceptableError;

    private WeightSolution bestSolution = null;

    /**
     *
     * @param inertiaFunction Function that describes the inertion of the particle
     * @param c1 Personal best constant
     * @param c2 Global/Local best constant
     * @param neighbourhood Neighbourhood size, -1 if global
     */
    public ParticleSwarmOptimisation(IFunction func,
                                     IInertia inertiaFunction,
                                     double c1,
                                     double c2,
                                     SolutionBoundaries boundaries,
                                     int particles,
                                     double acceptableError,
                                     int neighbourhood){
        this.rand = new Random();
        this.func = func;
        this.inertiaFunc = inertiaFunction;
        this.c1 = c1;
        this.c2 = c2;
        this.boundaries = boundaries;
        this.particles = new Particle[particles];
        this.acceptableError = acceptableError;
        IntStream.range(0, particles).forEach(i -> this.particles[i] = new Particle(func.solutionSize(), boundaries, rand));
        if(neighbourhood == -1){
            globalNeighbourhood = true;
        }
        else{
            globalNeighbourhood = false;
            this.neighbourhood = neighbourhood;
        }
    }

    public ParticleSwarmOptimisation(IFunction func,
                                     IInertia inertiaFunction,
                                     double c1,
                                     double c2,
                                     SolutionBoundaries boundaries,
                                     int particles,
                                     double acceptableError){
        this(func, inertiaFunction,c1, c2, boundaries, particles, acceptableError,-1);
    }

    @Override
    public WeightSolution run(){

        //I iteracija se ponavlja proces
        WeightSolution globalBest = new WeightSolution(0);
        globalBest.value = Double.MAX_VALUE;


        for(int i = 0; i < inertiaFunc.getIterations(); ++i){
            //Izracunati i updateati sve velicine particlima

            for (Particle particle : particles) {
                //izracunati value
                particle.position.value = func.valueAt(particle.position.weights);
                if (particle.position.value < particle.personalBest.value) {
                    //Updateati best ako treba
                    particle.personalBest = particle.position.clone();

                    if (particle.position.value < globalBest.value) {
                        globalBest = particle.position.clone();
                    }
                }
            }

            if(i%100 == 0){
                System.out.println(globalBest.value);
            }

            if(globalBest.value < acceptableError)break;

            double inertia = inertiaFunc.getInertia();
            for (Particle particle : particles) {
                WeightSolution best;
                if (globalNeighbourhood) {
                    best = globalBest;
                } else {
                    best = localBest(i, neighbourhood);
                }
                for (int k = 0; k < particle.speed.length; ++k) {
                    particle.speed[k] =
                            inertia * particle.speed[k] +
                                    rand.nextDouble() * c1 * (particle.personalBest.weights[k] - particle.position.weights[k]) +
                                    rand.nextDouble() * c2 * (best.weights[k] - particle.personalBest.weights[k]);
                    if (particle.speed[k] > boundaries.maxSpeed) particle.speed[k] = boundaries.maxSpeed;
                    else if (particle.speed[k] < boundaries.minSpeed) particle.speed[k] = boundaries.minSpeed;
                    particle.position.weights[k] += particle.speed[k];
                }
            }
        }
        bestSolution = globalBest.clone();
        return globalBest;
    }

    @Override
    public WeightSolution getSolution() {
        return bestSolution;
    }

    @Override
    public double[] getSolutionVector() {
        return bestSolution.weights;
    }

    private WeightSolution localBest(int i, int neighbourhood){
        double best = Double.MAX_VALUE;
        WeightSolution bestSolution = null;

        for(int j = -neighbourhood; j <= neighbourhood; ++j){
            Particle particle = particles[ (i + j + particles.length)% particles.length];
            if(particle.personalBest.value < best){
                best = particle.personalBest.value;
                bestSolution = particle.personalBest;
            }
        }
        return bestSolution.clone();
    }

}
