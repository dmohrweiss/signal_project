package com.cardio_generator.generators;

import java.util.Random;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * Generates simulated blood level data for patients.
 */
public class BloodLevelsDataGenerator implements PatientDataGenerator {
    private static final Random random = new Random();
    private final double[] baselineCholesterol;
    private final double[] baselineWhiteCells;
    private final double[] baselineRedCells;

    /**
     * Constructs the generator and initializes baseline values for all patients.
     * * @param patientCount The total number of patients.
     */
    public BloodLevelsDataGenerator(int patientCount) {
        // Initialize arrays to store baseline values for each patient
        baselineCholesterol = new double[patientCount + 1];
        baselineWhiteCells = new double[patientCount + 1];
        baselineRedCells = new double[patientCount + 1];

        // Generate baseline values for each patient
        for (int i = 1; i <= patientCount; i++) {
            baselineCholesterol[i] = 150 + random.nextDouble() * 50; // Initial random baseline
            baselineWhiteCells[i] = 4 + random.nextDouble() * 6; // Initial random baseline
            baselineRedCells[i] = 4.5 + random.nextDouble() * 1.5; // Initial random baseline
        }
    }

    /**
     * Generates a saturation reading by applying a small random incrementation or decrementation 
     * to the patient's last value.
     * * @param patientId ID
     * @param outputStrategy The strategy used to send the resulting value.
     * @return The new saturation value is sent to the output strategy as a string 
     * appended with '%'.
     * Prints errors to System.err if generation fails.
     */
    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            // Generate values around the baseline for realism
            double cholesterol = baselineCholesterol[patientId] + (random.nextDouble() - 0.5) * 10; // Small variation
            double whiteCells = baselineWhiteCells[patientId] + (random.nextDouble() - 0.5) * 1; // Small variation
            double redCells = baselineRedCells[patientId] + (random.nextDouble() - 0.5) * 0.2; // Small variation

            // Output the generated values
            outputStrategy.output(patientId, System.currentTimeMillis(), "Cholesterol", Double.toString(cholesterol));
            outputStrategy.output(patientId, System.currentTimeMillis(), "WhiteBloodCells",
                    Double.toString(whiteCells));
            outputStrategy.output(patientId, System.currentTimeMillis(), "RedBloodCells", Double.toString(redCells));
        } catch (Exception e) {
            System.err.println("An error occurred while generating blood levels data for patient " + patientId);
            e.printStackTrace(); // This will print the stack trace to help identify where the error occurred.
        }
    }
}
