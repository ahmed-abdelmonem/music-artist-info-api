package com.aabdelmonem.artist.configuration;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * All configurations related to task executor
 * 
 * @author ahmed.abdelmonem
 *
 */
@Configuration
@EnableAsync
public class ExecutorConfig {
	
	/**
	 * Executor to manage threads of the artist info API calls
	 */
	@Bean
	public Executor artistInfoAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		// number of threads is less than the corePoolSize, create a new Thread to run a new task.
		executor.setCorePoolSize(3);
		executor.setThreadNamePrefix("info-task-");
		executor.initialize();
		return executor;
	}
	
	/**
	 * Executor for API calls
	 */
	@Bean
	public Executor apiAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		// number of threads is less than the corePoolSize, create a new Thread to run a new task.
		executor.setCorePoolSize(100);
		executor.setThreadNamePrefix("api-task-");
		executor.initialize();
		return executor;
	}
}