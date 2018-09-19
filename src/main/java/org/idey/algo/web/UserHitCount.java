package org.idey.algo.web;

import org.idey.algo.iterator.timeseries.TimeWindow;

import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import java.util.concurrent.atomic.LongAdder;

public class UserHitCount {
    private ConcurrentMap<User, ConcurrentMap<Long, LongAdder>> userHitCount;
    private final long windowSize;
    private final TimeWindow unit;

    public UserHitCount(long windowSize, TimeWindow unit) {
        assert (windowSize>0);
        assert (unit!=null);
        userHitCount = new ConcurrentHashMap<>();
        this.windowSize = windowSize;
        this.unit = unit;
    }


    public void visit(User u){
        final Instant userStartTime = u.getCurrentTime();
        ConcurrentMap<Long,LongAdder> userHitsPerWindow = userHitCount.computeIfAbsent(u, user -> new ConcurrentHashMap<>());
        long difference = unit.calculate(userStartTime,Instant.now());
        if(!userHitsPerWindow.isEmpty()){
            long endUnitTobeRemoved = difference-windowSize;
            long startKey = userHitsPerWindow.keySet().iterator().next();
            for(long i=startKey;i<=endUnitTobeRemoved;i++){
                userHitsPerWindow.remove(i);
            }
        }
        LongAdder adder = userHitsPerWindow.computeIfAbsent(difference,d->new LongAdder());
        adder.add(1L);
        userHitsPerWindow.put(difference,adder);
        userHitCount.put(u,userHitsPerWindow);
    }

    public synchronized long getCount(User u){
        ConcurrentMap<Long,LongAdder> userHitsPerWindow = userHitCount.get(u);
        if(userHitsPerWindow == null){
            throw new IllegalArgumentException("Invalid User");
        }
        LongAdder adder = new LongAdder();
        userHitsPerWindow.values().parallelStream().forEach(
                v -> adder.add(v.sum())
        );
        return adder.sum();
    }

    public static void main(String[] args) throws InterruptedException {
        User u = new User(1);
        UserHitCount count = new UserHitCount(70,TimeWindow.SECONDS);
        for(int i=0;i<5;i++){
            Thread t = new Thread(() -> {
                count.visit(u);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });

            t.start();
            t.join();
        }

        System.out.println(count.getCount(u));
    }



}
