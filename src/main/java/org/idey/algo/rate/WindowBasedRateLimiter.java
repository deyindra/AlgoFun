package org.idey.algo.rate;

import java.util.concurrent.TimeUnit;

public class WindowBasedRateLimiter {
    private volatile long lastScheduledAction;
    private volatile boolean isFirstTime;
    private final long minTime;
    private volatile long storedPermits;


    public WindowBasedRateLimiter(final long maxRequest, final long duration, final TimeUnit unit){
        if(maxRequest <=0){
            throw new IllegalArgumentException("Invalid Parameter");
        }
        if(unit.compareTo(TimeUnit.MILLISECONDS)<0){
            throw new IllegalArgumentException("Invalid Parameter");
        }
        this.isFirstTime = true;
        this.storedPermits = maxRequest;
        this.minTime = (unit.toMillis(duration)/maxRequest);
    }

    public void acquire() throws InterruptedException{
        if(isFirstTime){
            synchronized(this){
                isFirstTime = false;
                lastScheduledAction = System.currentTimeMillis();
                storedPermits--;
            }
        }else{
            addPermit();
            if(storedPermits > 0) {
                storedPermits --;
            }else{
                synchronized(this) {
                    Thread.sleep(this.minTime);
                    addPermit();
                }
            }
        }
    }

    private void addPermit(){
        final long currentTime = System.currentTimeMillis();
        final long actualParmitTobeAdded = (currentTime - lastScheduledAction)/minTime;
        lastScheduledAction = currentTime;
        storedPermits = storedPermits + actualParmitTobeAdded;
    }

}
