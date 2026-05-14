package com.alerts;

/**
 * Concrete factory for creating ECG alerts.
 */
public class EcgAlertFactory extends AlertFactory {
    @Override
    public Alert createAlert(String patientId, String condition, long timestamp) {
        double heartRate = extractHeartRate(condition);
        return new EcgAlert(patientId, condition, timestamp, heartRate);
    }

    private double extractHeartRate(String condition) {
        return 0.0;
    }
}
