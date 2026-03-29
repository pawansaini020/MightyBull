package com.pawan.MightyBull.services.scrapper;

import com.pawan.MightyBull.config.AsyncConfig;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScreenerScraperAsyncRunner {

    private final ScreenerScraperWorker worker;

    public ScreenerScraperAsyncRunner(ScreenerScraperWorker worker) {
        this.worker = worker;
    }

    @Async(AsyncConfig.SCREENER_SCRAPER_EXECUTOR)
    public void runAsync(List<String> validatedStockIds) {
        worker.execute(validatedStockIds);
    }
}
