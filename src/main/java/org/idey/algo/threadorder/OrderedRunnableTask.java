package org.idey.algo.threadorder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

public class OrderedRunnableTask {
    private List<RunnableTask> list;
    private ReentrantLock[] locks;

    public OrderedRunnableTask(List<RunnableTask> list) {
        assert (list!=null && list.size()>0);
        this.list = Collections.unmodifiableList(list);
        locks = new ReentrantLock[this.list.size()-1];
        for(int i=0;i<locks.length;i++){
            locks[i] = new ReentrantLock();
        }
    }

    public void run(int runNumber) throws Exception{
        assert (runNumber>=0 && runNumber<=list.size()-1);
        try{
            if(runNumber!=0){
                locks[runNumber-1].lock();

            }
            if(runNumber!=list.size()-1){
                locks[runNumber].lock();
            }
            list.get(runNumber).run();
        } finally {
            if(runNumber!=list.size()-1){
                locks[runNumber].unlock();
            }
        }
    }

    public static class RunnableThread implements Runnable{
        private int runNumber;
        private OrderedRunnableTask task;

        public RunnableThread(int runNumber, OrderedRunnableTask task) {
            this.runNumber = runNumber;
            this.task = task;
        }

        @Override
        public void run() {
            try {
                task.run(runNumber);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        RunnableTask r1 = new RunnableTask(Optional.empty()) {
            @Override
            public void run() {
                System.out.println(1);
            }
        };

        RunnableTask r2 = new RunnableTask(Optional.empty()) {
            @Override
            public void run() {
                System.out.println(2);
            }
        };
        RunnableTask r3 = new RunnableTask(Optional.empty()) {
            @Override
            public void run() {
                System.out.println(3);
            }
        };

        RunnableTask r4 = new RunnableTask(Optional.empty()) {
            @Override
            public void run() {
                System.out.println(4);
            }
        };

        List<RunnableTask> list = new ArrayList<>();
        list.add(r4);
        list.add(r1);
        list.add(r2);
        list.add(r3);


        OrderedRunnableTask orderedRunnableTask = new OrderedRunnableTask(list);

        for(int i=0;i<list.size();i++){
            Thread t = new Thread(new RunnableThread(i,orderedRunnableTask));
            t.start();
            t.join();
        }

    }
}
