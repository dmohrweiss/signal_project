package com.alerts;

import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.List;

/**
 * Alert condition for critical blood pressure thresholds.
 * Triggers if systolic > 180 or < 90, or diastolic > 120 or < 60.
 */
public class BloodPressureCriticalCondition implements AlertCondition {

    @Override
    public Alert checkCondition(Patient patient) {
        List<PatientRecord> systolic = patient.getAllRecords().stream()
                .filter(r -> "SystolicPressure".equals(r.getRecordType()))
                .collect(java.util.stream.Collectors.toList());

        List<PatientRecord> diastolic = patient.getAllRecords().stream()
                .filter(r -> "DiastolicPressure".equals(r.getRecordType()))
                .collect(java.util.stream.Collectors.toList());

        for (PatientRecord record : systolic) {
            double value = record.getMeasurementValue();
            if (value > 180 || value < 90) {
                return new Alert(String.valueOf(patient.getPatientId()),
                        "Critical Threshold Alert: systolic blood pressure outside safe range",
                        record.getTimestamp());
            }
        }

        for (PatientRecord record : diastolic) {
            double value = record.getMeasurementValue();
            if (value > 120 || value < 60) {
                return new Alert(String.valueOf(patient.getPatientId()),
                        "Critical Threshold Alert: diastolic blood pressure outside safe range",
                        record.getTimestamp());
            }
        }

        return null;
    }
}
