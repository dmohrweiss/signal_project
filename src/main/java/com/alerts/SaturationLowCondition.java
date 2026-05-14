package com.alerts;

import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.List;

/**
 * Alert condition for low blood saturation.
 * Triggers if saturation < 92%.
 */
public class SaturationLowCondition implements AlertCondition {

    @Override
    public Alert checkCondition(Patient patient) {
        List<PatientRecord> saturation = patient.getAllRecords().stream()
                .filter(r -> "Saturation".equals(r.getRecordType()))
                .collect(java.util.stream.Collectors.toList());

        for (PatientRecord record : saturation) {
            if (record.getMeasurementValue() < 92.0) {
                return new Alert(String.valueOf(patient.getPatientId()),
                        "Low Saturation Alert: oxygen saturation below 92%",
                        record.getTimestamp());
            }
        }
        return null;
    }
}
