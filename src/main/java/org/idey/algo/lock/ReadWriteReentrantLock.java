package org.idey.algo.lock;

import java.util.HashMap;
import java.util.Map;

public class ReadWriteReentrantLock {
    private Map<Thread, Integer> readThreads= new HashMap<>();
    private Thread writeThread;
    private int writeRequests;
    private int writeAccess;

    public synchronized void readLock() throws InterruptedException{
        Thread t = Thread.currentThread();
        while (!canGrantReadAccess(t)){
            wait();
        }

        int count = readThreads.getOrDefault(t, 0);
        count++;
        readThreads.put(t, count);
    }

    public synchronized void readUnLock(){
        Thread t = Thread.currentThread();
        if(!isReadThread(t)){
            throw new IllegalMonitorStateException("Thread is not part of read threads");
        }
        int count = readThreads.get(t);
        if(count==1){
            readThreads.remove(t);
        }else{
            readThreads.put(t, --count);
        }
        notifyAll();
    }


    public synchronized void writeLock() throws InterruptedException{
        Thread t = Thread.currentThread();
        writeRequests++;
        while (!canGrantWriteAccess(t)){
            wait();
        }
        writeRequests--;
        writeAccess++;
        writeThread = t;
    }


    public synchronized void writeUnLock(){
        Thread t = Thread.currentThread();
        if(!isWriteThread(t)){
            throw new IllegalMonitorStateException("Thread is not part of write threads");
        }
        writeAccess--;
        if(writeAccess==0){
            writeThread = null;
        }
        notifyAll();
    }

    private boolean canGrantReadAccess(Thread currentThread){
        if(isWriteThread(currentThread)) return true;
        if(hasWriter()) return false;
        if(isReadThread(currentThread)) return true;
        if(hasWriteRequests()) return false;
        return true;
    }

    private boolean canGrantWriteAccess(Thread currentThread){
        if(isOnlyReadThread(currentThread)) return true;
        if(hasReaders()) return false;
        if(writeThread==null) return true;
        if(!isWriteThread(currentThread)) return false;
        return true;
    }





    private boolean isReadThread(Thread currentThread){
        return readThreads.containsKey(currentThread);
    }

    private boolean isWriteThread(Thread currentThread){
        return writeThread == currentThread;
    }

    private boolean hasReaders(){
        return readThreads.size()>0;
    }

    private boolean hasWriter(){
        return writeThread!=null;
    }

    private boolean hasWriteRequests(){
        return writeRequests>0;
    }

    private boolean isOnlyReadThread(Thread currentThread){
        return readThreads.size()==1 &&
                readThreads.containsKey(currentThread);
    }

}
