package com.pawan.MightyBull.services.scrapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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

/**
 * Runs the screener Python process synchronously (invoked from {@link ScreenerScraperAsyncRunner}).
 */
@Slf4j
@Component
public class ScreenerScraperWorker {

    private final String pythonExecutable;
    private final String screenerScriptPath;
    private final long timeoutMinutes;

    public ScreenerScraperWorker(
            @Value("${screener.scrapper.venv-path:}") String venvPath,
            @Value("${screener.scrapper.python-executable:python3}") String systemPythonFallback,
            @Value("${screener.scrapper.script-path}") String screenerScriptPath,
            @Value("${screener.scrapper.timeout-minutes:30}") long timeoutMinutes) {
        this.pythonExecutable = resolvePythonExecutable(venvPath, systemPythonFallback);
        this.screenerScriptPath = screenerScriptPath;
        this.timeoutMinutes = timeoutMinutes;
    }

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

    public void execute(List<String> validatedStockIds) {
        List<String> command = new ArrayList<>(2 + validatedStockIds.size());
        command.add(pythonExecutable);
        command.add(screenerScriptPath);
        command.addAll(validatedStockIds);

        log.info("SCRAPPER_SERVICE ::: starting screener scraper (async): {} {}", pythonExecutable,
                command.subList(1, command.size()));

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
