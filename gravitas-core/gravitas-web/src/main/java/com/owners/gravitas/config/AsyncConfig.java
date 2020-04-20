package com.owners.gravitas.config;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.owners.gravitas.exception.handler.CustomAsyncExceptionHandler;

/**
 * The Class AsyncConfig.
 *
 * @author amits
 */
@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {
	
	@Value("${livevox.async_executor_corepool_size:10}")
    private int asyncExecutorCorePoolSize;
    @Value("${livevox.async_executor_maxpool_size:50}")
    private int asyncExecutorMaxPoolSize;
    @Value("${livevox.async_executor_queue_capacity:150}")
    private int asyncExecutorQueueCapacity;
    @Value("${livevox.async_executor_pool_keepalive:10}")
    private long asyncTaskExecutorPoolKeepAlive;
    
    /*
     * (non-Javadoc)
     * @see org.springframework.scheduling.annotation.AsyncConfigurer#
     * getAsyncExecutor()
     */
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix( "async-" );
        executor.setCorePoolSize( 10 );
        executor.setMaxPoolSize( 10 );
        executor.initialize();
        return executor;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.scheduling.annotation.AsyncConfigurer#
     * getAsyncUncaughtExceptionHandler()
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new CustomAsyncExceptionHandler();
    }

    /**
     * Api executor.
     *
     * @return the executor
     */
    @Bean
    public Executor apiExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix( "apiExecutor-" );
        executor.setCorePoolSize( 5 );
        executor.setMaxPoolSize( 10 );
        executor.initialize();
        return executor;
    }
    
    @Bean
    public Executor lockExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix( "lockExecutor-" );
        executor.setCorePoolSize( 5 );
        executor.setMaxPoolSize( 15 );
        executor.initialize();
        return executor;
    }
    
    @Bean
    public ExecutorService liveVoxThreadPool() {
        BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<Runnable>(asyncExecutorQueueCapacity);
        ThreadFactory defaultThreadFactory = new ThreadFactoryBuilder().setNameFormat("LiveVoxExecutorService-%d").build();
        ExecutorService executorService = new ThreadPoolExecutor(asyncExecutorCorePoolSize, asyncExecutorMaxPoolSize,
                asyncTaskExecutorPoolKeepAlive, TimeUnit.SECONDS, blockingQueue, defaultThreadFactory,
                new ThreadPoolExecutor.CallerRunsPolicy());
        return executorService;
    }
}
