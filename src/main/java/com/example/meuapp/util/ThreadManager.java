package com.example.meuapp.util;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ThreadManager {
    public static final int MAX_NUMBER_OF_THREADS = 4;
    private static Executor executor;

    public static Executor getExecutor() {
        if (executor == null) {
            executor = Executors.newFixedThreadPool(MAX_NUMBER_OF_THREADS);
        }
        return executor;
    }
}
