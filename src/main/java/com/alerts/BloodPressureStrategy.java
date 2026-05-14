package com.alerts;

import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.List;

/**
 * Strategy implementation for monitoring blood pressure.
 * Checks for trends and critical thresholds.
 */
public class BloodPressureStrategy implements AlertStrategy {
    private final AlertFactory factory = new BloodPressureAlertFactory();

    @Override
    public Alert checkAlert(Patient patient) {
        List<PatientRecord> systolic = patient.getAllRecords().stream()
                .filter(r -> "SystolicPressure".equals(r.getRecordType()))
                .sorted((a, b) -> Long.compare(a.getTimestamp(), b.getTimestamp()))
                .collect(java.util.stream.Collectors.toList());

        if (systolic.isEmpty()) return null;

        for (PatientRecord record : systolic) {
            if (record.getMeasurementValue() > 180 || record.getMeasurementValue() < 90) {
                return factory.createAlert(String.valueOf(patient.getPatientId()),
                        "Critical blood pressure threshold exceeded",
                        record.getTimestamp());
            }
        }

        return null;
    }
}
