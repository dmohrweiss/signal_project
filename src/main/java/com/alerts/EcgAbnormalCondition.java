package com.alerts;

import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

/**
 * Alert condition for abnormal ECG peaks.
 * Triggers if peaks occur far beyond the moving average.
 */
public class EcgAbnormalCondition implements AlertCondition {

    @Override
    public Alert checkCondition(Patient patient) {
        List<PatientRecord> ecgRecords = patient.getAllRecords().stream()
                .filter(r -> "ECG".equals(r.getRecordType()))
                .sorted((a, b) -> Long.compare(a.getTimestamp(), b.getTimestamp()))
                .collect(java.util.stream.Collectors.toList());

        if (ecgRecords.size() < 2) return null;

        Queue<Double> window = new ArrayDeque<>();
        for (PatientRecord record : ecgRecords) {
            double current = record.getMeasurementValue();
            if (!window.isEmpty()) {
                double average = window.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
                if (average > 0 && (current > average * 2 || current - average > 1.0)) {
                    return new Alert(String.valueOf(patient.getPatientId()),
                            "ECG Alert: abnormal peak detected relative to moving average",
                            record.getTimestamp());
                }
            }
            window.add(current);
            if (window.size() > 5) {
                window.remove();
            }
        }
        return null;
    }
}
