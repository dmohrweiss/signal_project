package com.alerts;

import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Alert condition for rapid saturation drop.
 * Triggers if saturation drops by 5% or more within 10 minutes.
 */
public class SaturationRapidDropCondition implements AlertCondition {

    @Override
    public Alert checkCondition(Patient patient) {
        List<PatientRecord> saturation = patient.getAllRecords().stream()
                .filter(r -> "Saturation".equals(r.getRecordType()))
                .sorted((a, b) -> Long.compare(a.getTimestamp(), b.getTimestamp()))
                .collect(java.util.stream.Collectors.toList());

        if (saturation.size() < 2) return null;

        for (int i = 1; i < saturation.size(); i++) {
            PatientRecord previous = saturation.get(i - 1);
            PatientRecord current = saturation.get(i);
            long timeDelta = current.getTimestamp() - previous.getTimestamp();
            if (timeDelta <= TimeUnit.MINUTES.toMillis(10) && previous.getMeasurementValue() - current.getMeasurementValue() >= 5.0) {
                return new Alert(String.valueOf(patient.getPatientId()),
                        "Rapid Drop Alert: saturation dropped 5% or more within 10 minutes",
                        current.getTimestamp());
            }
        }
        return null;
    }
}
