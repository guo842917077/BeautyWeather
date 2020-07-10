package com.crazyorange.beauty.comm.network.executor;

import java.util.concurrent.Executor;

/**
 * @author guojinlong
 * 只复用了接口，没有做实际的
 */
public class DefaultExecutor implements Executor {
    private static final DefaultExecutor NOTHING_EXECUTOR = new DefaultExecutor();

    public static DefaultExecutor get() {
        return NOTHING_EXECUTOR;
    }

    private DefaultExecutor() {
    }

    @Override
    public void execute(Runnable command) {
        command.run();
    }
}
