package org.idey.algo.traffic;

import org.idey.algo.iterator.timeseries.TimeWindow;

import java.time.Instant;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class WebTraffic {
    private Instant startTime;
    private Map<Long, Long> trafficCount;
    private Deque<Long> timeQueue;
    private TimeWindow window;
    private int size;

    public WebTraffic(int size) {
        this(TimeWindow.SECONDS, size);
    }

    public WebTraffic(TimeWindow window, int size) {
        this.window = window;
        this.size = size;
        trafficCount = new HashMap<>();
        timeQueue = new LinkedList<>();
    }


    public synchronized void consumedTraffic(){
        Instant currentTime = Instant.now();
        if(startTime==null)
            startTime = currentTime;
        long timeDiff = window.calculate(startTime,currentTime);
        long count = 1;
        if(trafficCount.containsKey(timeDiff)){
            count = trafficCount.get(timeDiff)+1;
            trafficCount.put(timeDiff,count);
        }else{
            long lastCountedTime = timeQueue.isEmpty() ? 0 : timeQueue.peekLast();
            //Only happen at the 1st time
            if(lastCountedTime==timeDiff){
                timeQueue.offer(timeDiff);
                trafficCount.put(timeDiff,count);
            //Immediate next time slot
            }else if(timeDiff==lastCountedTime+1){
                timeQueue.offer(timeDiff);
                trafficCount.put(timeDiff,count);
            //meaning there is a hole after 0th unit we will get traffic in 2nd or 3rd unit
            }else if(timeDiff>lastCountedTime+1){
                long startWindowTime = timeDiff - size +1;
                if(startWindowTime>timeQueue.peekLast()){
                    timeQueue.clear();
                    trafficCount.clear();
                }else if(startWindowTime<=timeQueue.peekLast()){


                }
            }

        }

    }


}
