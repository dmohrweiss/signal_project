package com.alerts;

/**
 * Concrete factory for creating blood pressure alerts.
 */
public class BloodPressureAlertFactory extends AlertFactory {
    @Override
    public Alert createAlert(String patientId, String condition, long timestamp) {
        String severity = determineSeverity(condition);
        return new BloodPressureAlert(patientId, condition, timestamp, severity);
    }

    private String determineSeverity(String condition) {
        if (condition.contains("Critical")) {
            return "CRITICAL";
        } else if (condition.contains("Trend")) {
            return "WARNING";
        }
        return "INFO";
    }
}
