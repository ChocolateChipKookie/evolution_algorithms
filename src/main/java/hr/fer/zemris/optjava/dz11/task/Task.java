package hr.fer.zemris.optjava.dz11.task;

public class Task {
    public Result work() {
        throw new RuntimeException("Not implemented");
    }
    public static Task poison = new Task();
    protected Task(){}
}
