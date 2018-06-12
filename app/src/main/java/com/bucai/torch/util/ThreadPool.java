package com.bucai.torch.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zia on 2018/6/12.
 */
public enum ThreadPool {
    instance;

    private ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
    private ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    public ExecutorService getSingleThreadExecutor() {
        return singleThreadExecutor;
    }

    public ExecutorService getCachedThreadPool() {
        return cachedThreadPool;
    }
}
