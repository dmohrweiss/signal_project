package com.alerts;

/**
 * Factory Method Pattern: Base factory class for creating alerts.
 * Subclasses override createAlert to return specific alert types.
 */
public abstract class AlertFactory {
    /**
     * Factory method to create alerts.
     * @param patientId the patient ID
     * @param condition the alert condition
     * @param timestamp the timestamp
     * @return an Alert instance of the appropriate type
     */
    public abstract Alert createAlert(String patientId, String condition, long timestamp);
}
