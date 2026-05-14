package com.alerts;

import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.List;

/**
 * Strategy implementation for monitoring oxygen saturation.
 * Checks for critical drops and low levels.
 */
public class OxygenSaturationStrategy implements AlertStrategy {
    private final AlertFactory factory = new BloodOxygenAlertFactory();

    @Override
    public Alert checkAlert(Patient patient) {
        List<PatientRecord> saturation = patient.getAllRecords().stream()
                .filter(r -> "Saturation".equals(r.getRecordType()))
                .sorted((a, b) -> Long.compare(a.getTimestamp(), b.getTimestamp()))
                .collect(java.util.stream.Collectors.toList());

        if (saturation.isEmpty()) return null;

        for (PatientRecord record : saturation) {
            if (record.getMeasurementValue() < 92.0) {
                return factory.createAlert(String.valueOf(patient.getPatientId()),
                        "Low oxygen saturation below 92%",
                        record.getTimestamp());
            }
        }

        return null;
    }
}
