package org.idey.algo.web;

import org.idey.algo.iterator.timeseries.TimeWindow;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

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


    public  void visit(User u){
        System.out.println("Visited User "+u.getUserId());
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

    public  long getCount(User u){
        ConcurrentMap<Long,LongAdder> userHitsPerWindow = userHitCount.get(u);
        if(userHitsPerWindow == null){
            return 0;
        }
        LongAdder adder = new LongAdder();
        userHitsPerWindow.values().parallelStream().forEach(
                v -> adder.add(v.sum())
        );
        return adder.sum();
    }

    public static void main(String[] args) throws InterruptedException {
        List<User> list = new ArrayList<>();
        UserHitCount count = new UserHitCount(500,TimeWindow.SECONDS);
        Random r = new Random();
        for(int i=0;i<10;i++){
            list.add(new User(i+1));
        }
        CyclicBarrier barrier = new CyclicBarrier(10, () -> {
            for(User u:list){
                System.out.println(String.format("user %s is visited %d times", u, count.getCount(u)));
            }
        });

        for(int i=0;i<10;i++){
            Thread t = new Thread(() -> {
                try {
                    User u = list.get(r.nextInt(list.size()-1));
                    count.visit(u);
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
            });
            t.start();
        }




    }



}
