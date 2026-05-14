package com.alerts;

/**
 * Specific Alert subclass for blood pressure anomalies.
 */
public class BloodPressureAlert extends Alert {
    private String severity;

    public BloodPressureAlert(String patientId, String condition, long timestamp, String severity) {
        super(patientId, condition, timestamp);
        this.severity = severity;
    }

    public String getSeverity() {
        return severity;
    }
}
