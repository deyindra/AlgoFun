package org.idey.algo.theadpool;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicBoolean;

public class WorkerThread extends Thread {
    private AtomicBoolean execute;
    private final ConcurrentLinkedDeque<RunnableTask> tasks;

    public WorkerThread(String name,
                        AtomicBoolean execute,
                        ConcurrentLinkedDeque<RunnableTask> tasks) {
        super(name);
        this.execute = execute;
        this.tasks = tasks;
    }



    @Override
    public void run() {
        try{
            while (execute.get() || !tasks.isEmpty()){
                RunnableTask runnable;
                while ((runnable = tasks.poll()) != null) {
                    runnable.executeTask();
                    Thread.sleep(2000);
                }
            }
        }catch (RuntimeException | InterruptedException ex){
            throw new ThreadPoolException("Exception occurred", ex);
        }
    }
}
