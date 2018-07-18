package org.idey.algo.iterator.timeseries;

import org.idey.algo.util.AssertJ;

import java.time.Instant;
import java.time.temporal.ChronoUnit;


/**
 * TimeWindow which will define difference between two {@link Instant}
 * @see TimeWindow#SECONDS
 * @see TimeWindow#MINUTES
 * @see TimeWindow#HOURS
 * @see TimeWindow#DAYS
 */
public enum TimeWindow {
    //Return time difference in seconds
    SECONDS{
        @Override
        public long calculate(Instant t1, Instant t2) {
            checkValidity(t1, t2);
            return ChronoUnit.SECONDS.between(t1, t2);
        }
    },

    //Return time difference in minutes
    MINUTES{
        @Override
        public long calculate(Instant t1, Instant t2) {
            checkValidity(t1, t2);
            return ChronoUnit.MINUTES.between(t1, t2);
        }
    },

    //Return time difference in hours
    HOURS{
        @Override
        public long calculate(Instant t1, Instant t2) {
            checkValidity(t1, t2);
            return ChronoUnit.HOURS.between(t1, t2);
        }
    },

    //Return time difference in days
    DAYS{
        @Override
        public long calculate(Instant t1, Instant t2) {
            checkValidity(t1, t2);
            return ChronoUnit.DAYS.between(t1, t2);
        }
    };

    private static void checkValidity(Instant t1, Instant t2){
        AssertJ.notNull(t1, "Duration can not be null");
        AssertJ.notNull(t2, "Duration can not be null");
    }


    public abstract long calculate(final Instant t1, final Instant t2);


    public static void main(String[] args) throws InterruptedException {
        Instant i1 = Instant.now();
        Thread.sleep(2005L);
        Instant i2 = Instant.now();

        System.out.println(TimeWindow.SECONDS.calculate(i1,i2));
    }

}
