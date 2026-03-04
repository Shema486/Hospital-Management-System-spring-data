package com.hospital.Hms.async;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.util.concurrent.ThreadPoolExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "taskExecutor")
    public Executor taskExecutor()  {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("hms-task-");
        // AbortPolicy will cause a RejectedExecutionException to be thrown when the pool is saturated
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        // wait for tasks on shutdown to give them a chance to complete
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // ensure executor is initialized
        executor.initialize();
        return executor;
    }
}
