package com.cardio_generator.outputs;
/**
 * Defines the handling of generated health data.
 * This interface allows the simulator to remain separated from the 
 * destination of data.
 */
public interface OutputStrategy {
    /**
     * Passes or "computes" depending on the implementation a specific measurement for a patient.
     *
     * @param patientId ID.
     * @param timestamp The time the measurement occurred.
     * @param label     The type of data.
     * @param data      The actual number of the measurement.
     */
    void output(int patientId, long timestamp, String label, String data);
}
