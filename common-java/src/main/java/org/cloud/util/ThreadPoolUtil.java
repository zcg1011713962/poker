package org.cloud.util;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class ThreadPoolUtil {
    private static AtomicInteger mThreadNum = new AtomicInteger(0);
    private static final String VIDEO_EXECUTOR_NAME = "videoExecutor";
    private static final String VIDEO_SCHEDULER_EXECUTOR_NAME = "videoSchedulerExecutor";
    private static int corePoolSize = 10;
    private static int maxPoolSize = 200;
    private static int queueCapacity = 50;
    private static int keepAliveSeconds = 60;
	private static ThreadPoolExecutor videoExecutor;
    private static ScheduledThreadPoolExecutor videoSchedulerExecutor;

    private ThreadPoolUtil(){
    }


    public static ThreadPoolExecutor videoExecutor(){
	    if(videoExecutor == null){
            synchronized(ThreadPoolUtil.class) {
                if(videoExecutor == null) {
                    initVideoExecutor();
                }
            }
        }
        return videoExecutor;
    }


    public static ScheduledThreadPoolExecutor videoSchedulerExecutor(){
        if(videoSchedulerExecutor == null){
            synchronized(ThreadPoolUtil.class) {
                if(videoSchedulerExecutor == null) {
                    initVideoSchedulerExecutor();
                }
            }
        }
        return videoSchedulerExecutor;
    }

    /**
     * 普通任务线程池
     *
     * @return
     */
    private static ThreadPoolExecutor initVideoExecutor() {
        LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(queueCapacity);
        TaskExecutorFactory threadFactory = new TaskExecutorFactory(VIDEO_EXECUTOR_NAME);
        TaskExecutorRejectedExecution rejected = new TaskExecutorRejectedExecution(VIDEO_EXECUTOR_NAME);
        videoExecutor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveSeconds, TimeUnit.SECONDS, queue, threadFactory, rejected);
        return videoExecutor;
    }

    /**
     * 定时任务线程池
     * DelayedWorkQueue
     * @return
     */
    private static ScheduledThreadPoolExecutor initVideoSchedulerExecutor() {
        TaskExecutorFactory threadFactory = new TaskExecutorFactory(VIDEO_SCHEDULER_EXECUTOR_NAME);
        TaskExecutorRejectedExecution rejected = new TaskExecutorRejectedExecution(VIDEO_SCHEDULER_EXECUTOR_NAME);
        videoSchedulerExecutor = new ScheduledThreadPoolExecutor(corePoolSize, threadFactory, rejected);
        return videoSchedulerExecutor;
    }


    /**
     * 任务线程池工厂
     */
    private static class TaskExecutorFactory implements ThreadFactory {
        private String name;

        public TaskExecutorFactory(String name) {
            this.name = name;
        }

        @Override
        public Thread newThread(Runnable runable) {
            Thread thread = new Thread(runable, name + mThreadNum.incrementAndGet());
            return thread;
        }

    }

    /**
     * 任务线程拒绝策略
     */
    private static class TaskExecutorRejectedExecution implements RejectedExecutionHandler {
        private String name;

        public TaskExecutorRejectedExecution(String name) {
            this.name = name;
        }

        @Override
        public void rejectedExecution(Runnable runable, ThreadPoolExecutor executor) {
            log.error("{} 线程池拒绝 队列大小{} 活跃线程{}", name, executor.getQueue().size(), executor.getActiveCount());
        }
    }

}
