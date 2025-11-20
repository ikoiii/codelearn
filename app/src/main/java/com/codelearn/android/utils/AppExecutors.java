package com.codelearn.android.utils;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Global executor pools for the whole application.
 *
 * Groups of threads that will execute in parallel.
 */
public class AppExecutors {

    private static final int THREAD_COUNT = 3;

    private final Executor diskIO;
    private final Executor networkIO;
    private final Executor mainThread;
    private final ScheduledExecutorService scheduledExecutor;

    private AppExecutors(Executor diskIO, Executor networkIO, Executor mainThread,
                        ScheduledExecutorService scheduledExecutor) {
        this.diskIO = diskIO;
        this.networkIO = networkIO;
        this.mainThread = mainThread;
        this.scheduledExecutor = scheduledExecutor;
    }

    /**
     * Returns an instance of AppExecutors with default thread pool configurations
     * @return singleton AppExecutors instance
     */
    public static AppExecutors getInstance() {
        return InstanceHolder.INSTANCE;
    }

    /**
     * Executor for disk IO operations (database access)
     * @return disk IO executor
     */
    public Executor diskIO() {
        return diskIO;
    }

    /**
     * Executor for network operations
     * @return network executor
     */
    public Executor networkIO() {
        return networkIO;
    }

    /**
     * Executor for main thread operations
     * @return main thread executor
     */
    public Executor mainThread() {
        return mainThread;
    }

    /**
     * Scheduled executor for delayed operations
     * @return scheduled executor
     */
    public ScheduledExecutorService scheduledExecutor() {
        return scheduledExecutor;
    }

    /**
     * Execute a task on the disk IO thread
     * @param command task to execute
     */
    public void executeDiskIO(Runnable command) {
        diskIO.execute(command);
    }

    /**
     * Execute a task on the network thread
     * @param command task to execute
     */
    public void executeNetworkIO(Runnable command) {
        networkIO.execute(command);
    }

    /**
     * Execute a task on the main thread
     * @param command task to execute
     */
    public void executeMainThread(Runnable command) {
        mainThread.execute(command);
    }

    /**
     * Schedule a task to run after a delay
     * @param command task to execute
     * @param delay delay in milliseconds
     */
    public void scheduleDelayed(Runnable command, long delay) {
        scheduledExecutor.schedule(command, delay, java.util.concurrent.TimeUnit.MILLISECONDS);
    }

    /**
     * Shutdown all executors gracefully
     */
    public void shutdown() {
        if (scheduledExecutor != null && !scheduledExecutor.isShutdown()) {
            scheduledExecutor.shutdown();
        }
    }

    /**
     * Singleton instance holder
     */
    private static class InstanceHolder {
        private static final AppExecutors INSTANCE = new AppExecutors(
                Executors.newSingleThreadExecutor(), // Disk IO - single thread to avoid concurrency issues
                Executors.newFixedThreadPool(THREAD_COUNT), // Network - multiple threads for parallel requests
                new MainThreadExecutor(), // Main thread - posts to Android main looper
                Executors.newSingleThreadScheduledExecutor() // Scheduled executor for delayed tasks
        );
    }

    /**
     * Executor for main thread operations using Android's main looper
     */
    private static class MainThreadExecutor implements Executor {
        private android.os.Handler mainThreadHandler = new android.os.Handler(android.os.Looper.getMainLooper());

        @Override
        public void execute(Runnable command) {
            mainThreadHandler.post(command);
        }
    }

    /**
     * Run a task on main thread with a delay
     * @param command task to execute
     * @param delayMillis delay in milliseconds
     */
    public void postToMainThreadDelayed(Runnable command, long delayMillis) {
        mainThreadHandler.postDelayed(command, delayMillis);
    }

    private android.os.Handler mainThreadHandler = new android.os.Handler(android.os.Looper.getMainLooper());

    /**
     * Cancel pending main thread tasks
     * @param command task to cancel
     */
    public void cancelMainThreadTask(Runnable command) {
        mainThreadHandler.removeCallbacks(command);
    }

    /**
     * Check if current thread is main thread
     * @return true if current thread is main thread
     */
    public boolean isMainThread() {
        return android.os.Looper.myLooper() == android.os.Looper.getMainLooper();
    }
}