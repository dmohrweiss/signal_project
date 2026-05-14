package com.alerts;

import com.data_management.Patient;

/**
 * Interface for defining alert conditions.
 * Each condition checks if a specific alert threshold is met for a patient.
 */
public interface AlertCondition {
    /**
     * Checks if the alert condition is met for the given patient.
     * @param patient the patient to evaluate
     * @return an Alert if the condition is met, null otherwise
     */
    Alert checkCondition(Patient patient);
}
