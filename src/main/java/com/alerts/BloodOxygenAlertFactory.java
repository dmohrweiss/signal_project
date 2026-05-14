package com.alerts;

/**
 * Concrete factory for creating blood oxygen alerts.
 */
public class BloodOxygenAlertFactory extends AlertFactory {
    @Override
    public Alert createAlert(String patientId, String condition, long timestamp) {
        double saturationLevel = extractSaturationLevel(condition);
        return new BloodOxygenAlert(patientId, condition, timestamp, saturationLevel);
    }

    private double extractSaturationLevel(String condition) {
        if (condition.contains("92%")) {
            return 92.0;
        }
        return 0.0;
    }
}
