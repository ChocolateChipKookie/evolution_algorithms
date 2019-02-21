package hr.fer.zemris.optjava.dz11.task;

import hr.fer.zemris.optjava.dz11.task.Result;
import hr.fer.zemris.optjava.dz11.task.Task;

import java.util.concurrent.BlockingQueue;

public class WorkerThread extends Thread{

    private BlockingQueue<Task> tasks;
    private BlockingQueue<Result> results;

    public WorkerThread(BlockingQueue<Task> tasks, BlockingQueue<Result> results){
        this.tasks = tasks;
        this.results = results;
    }

    @Override
    public void run(){
        while(true){
            Task t = null;
            try {
                t = tasks.take();
                if(t == Task.poison)break;
                results.put(t.work());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
