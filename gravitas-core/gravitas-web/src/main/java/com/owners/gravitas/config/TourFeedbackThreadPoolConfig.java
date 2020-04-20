package com.owners.gravitas.config;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

/**
 * The Class TourFeedbackThreadPoolConfig.
 *
 * @author rajputbh
 */
@Configuration
public class TourFeedbackThreadPoolConfig {

    @Value("${gravitas.tour_feedback.executor_corepool_size:10}")
    private int asyncExecutorCorePoolSize;
    @Value("${gravitas.tour_feedback.executor_maxpool_size:50}")
    private int asyncExecutorMaxPoolSize;
    @Value("${gravitas.tour_feedback.executor_queue_capacity:150}")
    private int asyncExecutorQueueCapacity;
    @Value("${gravitas.tour_feedback.executor_pool_keepalive:10}")
    private long asyncTaskExecutorPoolKeepAlive;

    @Bean
    public ExecutorService tourFeedbackEmailThreadPool() {
        BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<Runnable>(asyncExecutorQueueCapacity);
        ThreadFactory defaultThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("tourfeedbackEmailExecutorService-%d").build();
        ExecutorService executorService = new ThreadPoolExecutor(asyncExecutorCorePoolSize, asyncExecutorMaxPoolSize,
                asyncTaskExecutorPoolKeepAlive, TimeUnit.SECONDS, blockingQueue, defaultThreadFactory,
                new ThreadPoolExecutor.CallerRunsPolicy());
        return executorService;
    }

    @Bean
    public ExecutorService tourFeedbackS3EventThreadPool() {
        BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<Runnable>(asyncExecutorQueueCapacity);
        ThreadFactory defaultThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("tourfeedbackS3EventExecutorService-%d").build();
        ExecutorService executorService = new ThreadPoolExecutor(asyncExecutorCorePoolSize, asyncExecutorMaxPoolSize,
                asyncTaskExecutorPoolKeepAlive, TimeUnit.SECONDS, blockingQueue, defaultThreadFactory,
                new ThreadPoolExecutor.CallerRunsPolicy());
        return executorService;
    }
}
