package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ConcurrentHashMap;

/**
 * An implementation of {@link OutputStrategy} that writtes patient data to .txt.
 * Each data label is saved to its own file within a base directory.
 */

//Classes start with capital letters.
public class FileOutputStrategy implements OutputStrategy {

    // Changed from BaseDirectory to baseDirectory
    private String baseDirectory;

    // Changed from file_map to fileMap underscore are not used on google java style guide
    public final ConcurrentHashMap<String, String> fileMap = new ConcurrentHashMap<>();

    // Constructor uses the class name
    public FileOutputStrategy(String baseDirectory) {

        // Changed from BaseDirectory to baseDirectory
        this.baseDirectory = baseDirectory;
    }

    /**
     * Processes the data to a file corresponding to the data name.
     * If the base directory does not exist, it will be created.
     * * @param patientId ID.
     * @param timestamp The time of the reading.
     * @param label     The category of data.
     * @param data      The actusal number.
     * Prints errors to System.err if IO fails.
     */
    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        try {
            // Create the directory
            Files.createDirectories(Paths.get(baseDirectory));
        } catch (IOException e) {
            System.err.println("Error creating base directory: " + e.getMessage());
            return;
        }
        // Changed from FilePath to filePath
        String filePath = fileMap.computeIfAbsent(label, k -> Paths.get(baseDirectory, label + ".txt").toString());

        // Write the data to the file
        try (PrintWriter out = new PrintWriter(
                Files.newBufferedWriter(Paths.get(filePath), StandardOpenOption.CREATE, StandardOpenOption.APPEND))) {
            out.printf("Patient ID: %d, Timestamp: %d, Label: %s, Data: %s%n", patientId, timestamp, label, data);
        } catch (Exception e) {
            System.err.println("Error writing to file " + filePath + ": " + e.getMessage());
        }
    }
}