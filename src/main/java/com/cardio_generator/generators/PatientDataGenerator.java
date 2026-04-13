package com.cardio_generator.generators;

import com.cardio_generator.outputs.OutputStrategy;
/**
 * A common interface for all health metric generators.
 * Implementations simulate specific physiological data 
 *  and pass them.
 */
public interface PatientDataGenerator {
    /**
     * Generates a single data point for a specific patient.
     *
     * @param patientId      ID
     * @param outputStrategy The OutputStrategy to use for passing the data.
     */
    void generate(int patientId, OutputStrategy outputStrategy);
}
