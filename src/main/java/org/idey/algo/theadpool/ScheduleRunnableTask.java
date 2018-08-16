package org.idey.algo.theadpool;

import java.util.concurrent.TimeUnit;

public abstract class ScheduleRunnableTask extends RunnableTask {
    private final long scheduledInterval;


    public ScheduleRunnableTask(String taskName, TimeUnit unit, long duration) {
        super(taskName);
        this.scheduledInterval = unit.convert(duration,TimeUnit.MILLISECONDS);
    }
}
