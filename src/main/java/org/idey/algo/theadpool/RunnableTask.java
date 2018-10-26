package org.idey.algo.theadpool;

public abstract class RunnableTask implements Runnable {
    protected String taskName;
    public RunnableTask(String taskName) {
        this.taskName = taskName;
    }
    protected String name(){
        return String.format("Executing %s on thread %s", this.taskName,Thread.currentThread().getName());
    }
    protected void executeTask(){
        run();
    }
}
