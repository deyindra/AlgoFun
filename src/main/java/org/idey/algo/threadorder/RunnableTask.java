package org.idey.algo.threadorder;

import java.util.Optional;

public abstract class RunnableTask {
    protected Optional<Context> ctx;

    protected RunnableTask(Optional<Context> ctx) {
        this.ctx = ctx;
    }
    public abstract void run() throws Exception;
}
