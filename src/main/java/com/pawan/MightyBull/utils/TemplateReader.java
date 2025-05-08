package com.pawan.MightyBull.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author Pawan Saini
 * Created on 19/01/25.
 */
@Slf4j
public class TemplateReader {

    public static String readTemplate(String filePath) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            return String.join("\n", lines); // Combine the lines into a single string
        } catch (IOException e) {
            log.error("Failed to read template from file: {}", filePath, e);
            return null; // Return null or throw an exception as needed
        }
    }
}
