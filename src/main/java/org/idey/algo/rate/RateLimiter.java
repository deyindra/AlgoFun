package org.idey.algo.rate;

import java.util.concurrent.TimeUnit;

public class RateLimiter {
    private long minTime;
    //holds time of last action (past or future!)
    private volatile long lastSchedAction;
    private volatile boolean forFirstTime;
    public RateLimiter(float maxRate, long duration, TimeUnit unit) {
        if(maxRate <= 0.0f) {
            throw new IllegalArgumentException("Invalid rate");
        }
        if(unit.compareTo(TimeUnit.MILLISECONDS)<0){
            throw new IllegalArgumentException("Invalid time unit");
        }
        this.forFirstTime = true;
        this.minTime = (long)(unit.toMillis(duration) / maxRate);
    }

    public void consume() throws InterruptedException {
        if(forFirstTime){
            synchronized (this){
                forFirstTime = false;
                lastSchedAction = System.currentTimeMillis();
            }
        }else{
            long curTime = System.currentTimeMillis();
            long timeLeft;
            synchronized (this) {
                timeLeft = lastSchedAction + minTime - curTime;
                if (timeLeft > 0) {
                    lastSchedAction += minTime;
                } else {
                    lastSchedAction = curTime;
                }
            }
            if(timeLeft > 0) {
                Thread.sleep(timeLeft);
            }
        }
    }

}
