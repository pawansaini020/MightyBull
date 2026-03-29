package com.pawan.MightyBull.services.scrapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Slf4j
@Service
public class ScrapperService {

    private static final Pattern STOCK_ID_PATTERN = Pattern.compile("^[A-Za-z0-9&.-]{1,32}$");

    private final ScreenerScraperAsyncRunner asyncRunner;

    public ScrapperService(ScreenerScraperAsyncRunner asyncRunner) {
        this.asyncRunner = asyncRunner;
    }

    /**
     * Validates input and starts the Python screener scraper on a background thread pool.
     * Returns as soon as the job is queued (does not wait for Python to finish).
     */
    public void triggerScreenerStockScrapper(List<String> stockIds) {
        List<String> validated = validateStockIds(stockIds);
        log.info("SCRAPPER_SERVICE ::: queueing screener scraper for {} stock(s)", validated.size());
        asyncRunner.runAsync(validated);
    }

    private static List<String> validateStockIds(List<String> stockIds) {
        if (stockIds == null || stockIds.isEmpty()) {
            throw new IllegalArgumentException("StockIds must not be null or empty");
        }
        List<String> validated = new ArrayList<>(stockIds.size());
        for (String id : stockIds) {
            if (id == null || id.isBlank()) {
                throw new IllegalArgumentException("stock id must not be null or blank");
            }
            String trimmed = id.trim();
            if (!STOCK_ID_PATTERN.matcher(trimmed).matches()) {
                throw new IllegalArgumentException("invalid stock id format: " + trimmed);
            }
            validated.add(trimmed);
        }
        return validated;
    }
}
