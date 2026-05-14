package com.alerts;

import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Alert condition for Hypotensive Hypoxemia.
 * Triggers if systolic < 90 and saturation < 92% within 10 minutes.
 */
public class HypotensiveHypoxemiaCondition implements AlertCondition {

    @Override
    public Alert checkCondition(Patient patient) {
        List<PatientRecord> systolic = patient.getAllRecords().stream()
                .filter(r -> "SystolicPressure".equals(r.getRecordType()))
                .collect(java.util.stream.Collectors.toList());

        List<PatientRecord> saturation = patient.getAllRecords().stream()
                .filter(r -> "Saturation".equals(r.getRecordType()))
                .collect(java.util.stream.Collectors.toList());

        if (systolic.isEmpty() || saturation.isEmpty()) return null;

        for (PatientRecord bpRecord : systolic) {
            if (bpRecord.getMeasurementValue() >= 90.0) continue;
            for (PatientRecord satRecord : saturation) {
                if (satRecord.getMeasurementValue() < 92.0
                        && Math.abs(bpRecord.getTimestamp() - satRecord.getTimestamp()) <= TimeUnit.MINUTES.toMillis(10)) {
                    return new Alert(String.valueOf(patient.getPatientId()),
                            "Hypotensive Hypoxemia Alert: low systolic pressure and low oxygen saturation",
                            Math.max(bpRecord.getTimestamp(), satRecord.getTimestamp()));
                }
            }
        }
        return null;
    }
}
