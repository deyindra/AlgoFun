package org.idey.algo.traffic;

import org.idey.algo.iterator.timeseries.TimeWindow;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


public class WebTraffic {
    private Instant startTime;
    private ConcurrentMap<Long, Long> trafficCount;
    private TimeWindow window;
    private int windowSize;
    private int slidingSize;

    public WebTraffic(int windowSize) {
        this(TimeWindow.SECONDS, windowSize);
    }

    public WebTraffic(TimeWindow window, int windowSize) {
        this.window = window;
        this.windowSize = windowSize;
        trafficCount = new ConcurrentHashMap<>();
    }


    public synchronized void consumedTraffic(final Instant currentTime){
        if(startTime==null)
            startTime = currentTime;
        else if(startTime.compareTo(currentTime)>0){
            throw new IllegalArgumentException("Invalid Current time"+currentTime.toString());
        }
        long timeDiff = window.calculate(startTime,currentTime)+1;
        long count = trafficCount.getOrDefault(timeDiff,0L);
        trafficCount.put(timeDiff,++count);
    }


    public synchronized Map<Long, Long> getTrafficData(){
        Instant currentTime = Instant.now();
        long endTime = window.calculate(startTime,currentTime);
        long startTime = endTime - windowSize + 1;
        if(startTime<=0L){
            startTime = 1L;
        }
        Map<Long, Long> map = new HashMap<>();
        while (startTime<=endTime){
            map.put(startTime, trafficCount.getOrDefault(startTime,0L));
            startTime++;
        }
        return Collections.unmodifiableMap(map);
    }

}
