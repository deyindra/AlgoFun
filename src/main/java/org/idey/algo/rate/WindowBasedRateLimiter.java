package org.idey.algo.rate;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class WindowBasedRateLimiter {
    private volatile long lastScheduledAction;
    private volatile boolean isFirstTime;
    //Minimum time needed between 2 permits
    private final long minTime;
    private AtomicLong storedPermits;


    public WindowBasedRateLimiter(final long maxRequest, final long duration, final TimeUnit unit){
        if(maxRequest <=0){
            throw new IllegalArgumentException("Invalid Parameter");
        }
        if(unit.compareTo(TimeUnit.MILLISECONDS)<0){
            throw new IllegalArgumentException("Invalid Parameter");
        }
        this.isFirstTime = true;
        this.storedPermits = new AtomicLong(maxRequest);
        this.minTime = (unit.toMillis(duration)/maxRequest);
    }

    public void acquire() throws InterruptedException{
        if(isFirstTime){
            synchronized(this){
                isFirstTime = false;
                lastScheduledAction = System.currentTimeMillis();
                storedPermits.decrementAndGet();
            }
        }else{
            addPermit();
            if(storedPermits.get() > 0) {
                storedPermits.decrementAndGet();
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
        final long numberOfPermitTobeAdded = (currentTime - lastScheduledAction)/minTime;
        lastScheduledAction = currentTime;
        storedPermits.addAndGet(numberOfPermitTobeAdded);
    }

    public static void main(String[] args) throws InterruptedException {
        WindowBasedRateLimiter rateLimiter = new WindowBasedRateLimiter(2,1, TimeUnit.SECONDS);
        rateLimiter.acquire();
        System.out.println("Hello");
        rateLimiter.acquire();
        System.out.println("Hello");
        rateLimiter.acquire();
        System.out.println("Hello");
        rateLimiter.acquire();
        System.out.println("Hello");

    }
}
