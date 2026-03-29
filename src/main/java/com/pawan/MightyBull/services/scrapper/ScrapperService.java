package com.pawan.MightyBull.services.scrapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Pattern;

@Slf4j
@Service
public class ScrapperService {

    /**
     * Allowed characters for NSE/BSE-style symbols passed as process arguments (no shell, fixed executable).
     */
    private static final Pattern STOCK_ID_PATTERN = Pattern.compile("^[A-Za-z0-9&.-]{1,32}$");

    private final String pythonExecutable;
    private final String screenerScriptPath;
    private final long timeoutMinutes;

    public ScrapperService(
            @Value("${screener.scrapper.venv-path:}") String venvPath,
            @Value("${screener.scrapper.python-executable:python3}") String systemPythonFallback,
            @Value("${screener.scrapper.script-path}") String screenerScriptPath,
            @Value("${screener.scrapper.timeout-minutes:30}") long timeoutMinutes) {
        this.pythonExecutable = resolvePythonExecutable(venvPath, systemPythonFallback);
        this.screenerScriptPath = screenerScriptPath;
        this.timeoutMinutes = timeoutMinutes;
    }

    /**
     * Uses {@code <venv>/bin/python} (or {@code python3}, or Windows {@code Scripts/python.exe}).
     * If {@code venv-path} is blank, uses {@code python-executable} (default {@code python3}).
     */
    private static String resolvePythonExecutable(String venvPath, String systemPythonFallback) {
        if (venvPath == null || venvPath.isBlank()) {
            return systemPythonFallback;
        }
        Path root = Paths.get(venvPath.trim()).toAbsolutePath().normalize();
        for (String name : List.of("python", "python3")) {
            Path candidate = root.resolve("bin").resolve(name);
            if (Files.isExecutable(candidate)) {
                return candidate.toString();
            }
        }
        Path windows = root.resolve("Scripts").resolve("python.exe");
        if (Files.isRegularFile(windows)) {
            return windows.toAbsolutePath().normalize().toString();
        }
        throw new IllegalStateException(
                "no interpreter in venv (expected bin/python or bin/python3 under): " + root);
    }

    public void triggerScreenerStockScrapper(List<String> stockIds) {
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

        List<String> command = new ArrayList<>(2 + validated.size());
        command.add(pythonExecutable);
        command.add(screenerScriptPath);
        command.addAll(validated);

        log.info("SCRAPPER_SERVICE ::: starting screener scraper: {} {}", pythonExecutable, command.subList(1, command.size()));

        ProcessBuilder pb = new ProcessBuilder(command);
        pb.redirectErrorStream(true);

        try {
            Process process = pb.start();
            CompletableFuture<byte[]> outputFuture = CompletableFuture.supplyAsync(() -> {
                try {
                    return process.getInputStream().readAllBytes();
                } catch (IOException e) {
                    return new byte[0];
                }
            });
            boolean finished = process.waitFor(timeoutMinutes, TimeUnit.MINUTES);
            if (!finished) {
                process.destroyForcibly();
                try {
                    outputFuture.get(1, TimeUnit.MINUTES);
                } catch (ExecutionException | TimeoutException ignored) {
                    // best-effort drain after kill
                }
                throw new IllegalStateException("screener scraper timed out after " + timeoutMinutes + " minutes");
            }
            int exit = process.exitValue();
            byte[] output = outputFuture.get();
            String logText = new String(output, StandardCharsets.UTF_8).trim();
            if (!logText.isEmpty()) {
                log.info("SCRAPPER_SERVICE ::: screener scraper output:\n{}", logText);
            }
            if (exit != 0) {
                throw new IllegalStateException("screener scraper exited with code " + exit);
            }
        } catch (IOException e) {
            log.error("SCRAPPER_SERVICE ::: failed to run screener scraper", e);
            throw new IllegalStateException("failed to run screener scraper: " + e.getMessage(), e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("screener scraper was interrupted", e);
        } catch (ExecutionException e) {
            Throwable cause = e.getCause() != null ? e.getCause() : e;
            log.error("SCRAPPER_SERVICE ::: error reading screener scraper output", cause);
            throw new IllegalStateException("failed to read screener scraper output", cause);
        }
    }
}
