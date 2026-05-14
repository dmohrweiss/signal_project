package com.alerts;

import com.data_management.Patient;

/**
 * Strategy Pattern: Interface for alert checking strategies.
 */
public interface AlertStrategy {
    /**
     * Checks if an alert should be triggered based on patient data.
     * @param patient the patient to evaluate
     * @return an Alert if the condition is met, null otherwise
     */
    Alert checkAlert(Patient patient);
}
