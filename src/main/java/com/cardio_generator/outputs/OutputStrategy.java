package com.cardio_generator.outputs;
/**
 * Defines the handling of generated health data.
 */
public interface OutputStrategy {
    /**
     * @param patientId ID.
     * @param timestamp The time the measurement occurred.
     * @param label     The type of data.
     * @param data      The actual number of the measurement.
     */
    void output(int patientId, long timestamp, String label, String data);
}
