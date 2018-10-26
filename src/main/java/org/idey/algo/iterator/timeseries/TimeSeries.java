package org.idey.algo.iterator.timeseries;


import org.idey.algo.util.AssertJ;

import java.time.Instant;

/**
 * TimeSeries data which will contain {@link Comparable} object and {@link Instant}
 * @param <T> object which implements {@link Comparable}
 */

public class TimeSeries<T extends Comparable<T>> implements Comparable<TimeSeries<T>> {
    private T object;
    private Instant ts;

    /**
     * @param object {@link Comparable} and with {@link Instant#now()}
     */
    public TimeSeries(T object) {
        this(object, Instant.now());
    }

    public TimeSeries(T object, Instant d){
        AssertJ.notNull(object, "Object can not be null");
        AssertJ.notNull(d, "Date can not be null");
        this.object = object;
        this.ts = d;
    }

    public T getObject() {
        return object;
    }

    public Instant getTs() {
        return ts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimeSeries<?> that = (TimeSeries<?>) o;

        return (object != null ? object.equals(that.object) : that.object == null)
                && (ts != null ? ts.equals(that.ts) : that.ts == null);
    }

    @Override
    public int hashCode() {
        int result = object != null ? object.hashCode() : 0;
        result = 31 * result + (ts != null ? ts.hashCode() : 0);
        return result;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public int compareTo(TimeSeries<T> o) {
        if(o==null){
            return 1;
        }else if(this==o){
            return 0;
        }else{
            Instant thisDate = this.ts;
            Instant otherDate = o.ts;
            if(thisDate==null){
                return -1;
            }else{
                int retValue =  thisDate.compareTo(otherDate);
                if(retValue==0){
                    T obj = this.object;
                    T anotherObject = o.object;
                    if(obj==null){
                        return -1;
                    }else{
                        return obj.compareTo(anotherObject);
                    }
                }
                return retValue;
            }
        }
    }

    @Override
    public String toString() {
        return "TimeSeries{" +
                "object=" + object +
                ", ts=" + ts +
                '}';
    }
}
