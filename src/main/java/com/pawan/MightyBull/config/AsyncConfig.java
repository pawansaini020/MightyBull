package com.pawan.MightyBull.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

    public static final String SCREENER_SCRAPER_EXECUTOR = "screenerScraperExecutor";

    @Bean(name = SCREENER_SCRAPER_EXECUTOR)
    public Executor screenerScraperExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(3);
        executor.setQueueCapacity(50);
        executor.setThreadNamePrefix("screener-scraper-");
        executor.initialize();
        return executor;
    }
}
