package com.rainstorm.whoa.utlis;

import android.support.annotation.NonNull;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by liys on 2019-01-25.
 */

public class ThreadManager {
    private static int CORE_NUM = Runtime.getRuntime().availableProcessors();
    private static final int KEEP_ALIVE_TIME = 1;

    private static BlockingDeque<Runnable> taskQueue = new LinkedBlockingDeque<>();

    public static ThreadPoolExecutor get() {

        return new ThreadPoolExecutor(CORE_NUM,
                CORE_NUM * 2,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                taskQueue,
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );
    }

    private static class BackgroundFactory implements ThreadFactory {
        @Override
        public Thread newThread(@NonNull Runnable r) {
            return null;
        }
    }
}
