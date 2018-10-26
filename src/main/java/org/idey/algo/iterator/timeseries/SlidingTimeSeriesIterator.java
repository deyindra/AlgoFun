package org.idey.algo.iterator.timeseries;

import org.idey.algo.iterator.peek.PeekIterator;
import org.idey.algo.iterator.peek.SimplePeekIterator;
import org.idey.algo.util.AssertJ;

import java.time.Instant;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * {@link TimeSeries} based Sliding window {@link Iterator} which will return all elements based on
 * {@link SlidingTimeSeriesIterator#size} and then slide by {@link SlidingTimeSeriesIterator#step}
 */
public class SlidingTimeSeriesIterator<T extends Comparable<T>> implements Iterator<Map<Long, SortedSet<TimeSeries<T>>>> {

    private final TimeWindow timeWindow;
    private final PeekIterator<TimeSeries<T>> iterator;
    private final int size;
    //Steps from from where window start
    private final int step;
    private final Deque<Long> deque;
    private final Map<Long, SortedSet<TimeSeries<T>>> map;
    private final Instant startDuration;
    private boolean hasNext;

    /**
     *
     * @param timeWindow unit size of time window it will be either {@link TimeWindow#SECONDS},
     *                  {@link TimeWindow#MINUTES}, {@link TimeWindow#HOURS}, {@link TimeWindow#DAYS}
     * @param iterator {@link TimeSeries} {@link Iterator}
     * @param size size of the time window
     * @param step Step by which window will slide
     */
    public SlidingTimeSeriesIterator(TimeWindow timeWindow,
                                     Iterator<TimeSeries<T>> iterator,
                                     int size, int step) {

        AssertJ.notNull(timeWindow, "Invalid time window");
        AssertJ.notNull(iterator, "ietartor can not be null");
        AssertJ.assertTrue(i->i>0, size, "size can not be zero or negative");
        AssertJ.assertTrue(i->i>0, step, "step can not be zero or negative");
        this.timeWindow = timeWindow;
        this.iterator = new SimplePeekIterator<>(iterator);
        this.size = size;
        this.step = step;
        map = new HashMap<>();
        deque = new LinkedList<>();
        hasNext=false;
        startDuration = this.iterator.peek().getTs();
        setAdvance();
    }

    @Override
    public boolean hasNext() {
        return hasNext;
    }

    @Override
    public Map<Long, SortedSet<TimeSeries<T>>> next() {
        if(!hasNext()){
            throw new NoSuchElementException("No more elements");
        }
        Map<Long,SortedSet<TimeSeries<T>>> returnMap = Collections.unmodifiableMap(new HashMap<>(map));
        setAdvance();
        return returnMap;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Invalid operations....");
    }

    private void setAdvance() {
        hasNext = false;
        long nextValue=0;
        if (iterator.hasNext()) {
            if (!deque.isEmpty()) {
                if(size>step){
                    for(int count=0;!deque.isEmpty() && count<step; count++){
                        long value = deque.poll();
                        map.remove(value);
                    }
                    nextValue = deque.peekLast();
                }else {
                    nextValue = deque.peekLast();
                    long endStepValue = nextValue + step - size;
                    deque.clear();
                    map.clear();
                    while(iterator.hasNext()){
                        nextValue = calculateTimeDifference();
                        if(nextValue>endStepValue){
                            nextValue = endStepValue;
                            break;
                        }
                        iterator.next();
                    }
                }
            }
            //Fill the gap here
            while (iterator.hasNext() && deque.size()<size){
                long timeDifference = calculateTimeDifference();
                if(nextValue+1<timeDifference){
                    nextValue++;
                    while (nextValue<timeDifference && deque.size()<size){
                        deque.add(nextValue);
                        populateMap(nextValue);
                        nextValue++;
                    }
                }
                if(deque.size()<size){
                    if(!map.containsKey(timeDifference)){
                        deque.add(timeDifference);
                    }
                    populateMap(timeDifference,Optional.of(iterator.next()));
                    nextValue = timeDifference;
                }
            }
            if(!deque.isEmpty()) {
                hasNext = true;
            }
        }
    }

    private long calculateTimeDifference(){
        TimeSeries<T> currentTsData = iterator.peek();
        return timeWindow.calculate(startDuration,currentTsData.getTs());
    }

    private void populateMap(long count){
        populateMap(count,Optional.empty());
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private void populateMap(long count, Optional<TimeSeries<T>> timeSeries){
        SortedSet<TimeSeries<T>> sortedSet = map.computeIfAbsent(count, k -> new TreeSet<>());
        timeSeries.ifPresent(sortedSet::add);
    }

}
