package org.idey.algo.theadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

public class SimpleThreadPool {
    private ConcurrentLinkedDeque<RunnableTask> tasks = new ConcurrentLinkedDeque<>();
    private AtomicBoolean execute = new AtomicBoolean(true);
    private List<Thread> workersThread = new ArrayList<>();

    public SimpleThreadPool(final int numberThread) {
        for(int count=0;count<numberThread;count++){
            Thread t = new WorkerThread(String.format("Worker Thread %d",count+1),execute,tasks);
            t.start();
            workersThread.add(t);
        }
    }

    public SimpleThreadPool() {
        this(Runtime.getRuntime().availableProcessors()*2);
    }

    public void addTask(RunnableTask task){
        if(execute.get()){
            tasks.add(task);
        }else{
            throw new ThreadPoolException("Unable to add new task as thread pool in a process of shutdown...");
        }
    }

    public void stop(){
        execute.set(false);
    }


    public void awaitTermination(long timeout) throws TimeoutException {
        if (this.execute.get()) {
            throw new IllegalStateException("Threadpool not terminated before awaiting termination");
        }
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime <= timeout) {
            boolean flag = true;
            for (Thread thread : workersThread) {
                if (thread.isAlive()) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                return;
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new ThreadPoolException("exception",e);
            }
        }
        throw new TimeoutException("Unable to terminate threadpool within the specified timeout (" + timeout + "ms)");
    }

    public void awaitTermination() {
        if (this.execute.get()) {
            throw new IllegalStateException("Threadpool not terminated before awaiting termination");
        }
        while (true) {
            boolean flag = true;
            for (Thread thread : workersThread) {
                if (thread.isAlive()) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                return;
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new ThreadPoolException("exception",e);
            }
        }
    }

}
