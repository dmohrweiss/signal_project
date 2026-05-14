package com.alerts;

import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.List;

/**
 * Strategy implementation for monitoring heart rate via ECG.
 * Checks for abnormal patterns and anomalies.
 */
public class HeartRateStrategy implements AlertStrategy {
    private final AlertFactory factory = new EcgAlertFactory();

    @Override
    public Alert checkAlert(Patient patient) {
        List<PatientRecord> ecgRecords = patient.getAllRecords().stream()
                .filter(r -> "ECG".equals(r.getRecordType()))
                .sorted((a, b) -> Long.compare(a.getTimestamp(), b.getTimestamp()))
                .collect(java.util.stream.Collectors.toList());

        if (ecgRecords.size() < 2) return null;

        for (int i = 1; i < ecgRecords.size(); i++) {
            double prev = ecgRecords.get(i - 1).getMeasurementValue();
            double curr = ecgRecords.get(i).getMeasurementValue();
            if (Math.abs(curr - prev) > 2.0) {
                return factory.createAlert(String.valueOf(patient.getPatientId()),
                        "Abnormal heart rate detected",
                        ecgRecords.get(i).getTimestamp());
            }
        }

        return null;
    }
}
