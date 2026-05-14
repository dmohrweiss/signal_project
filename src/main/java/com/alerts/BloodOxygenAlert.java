package com.alerts;

/**
 * Specific Alert subclass for blood oxygen anomalies.
 */
public class BloodOxygenAlert extends Alert {
    private double saturationLevel;

    public BloodOxygenAlert(String patientId, String condition, long timestamp, double saturationLevel) {
        super(patientId, condition, timestamp);
        this.saturationLevel = saturationLevel;
    }

    public double getSaturationLevel() {
        return saturationLevel;
    }
}
