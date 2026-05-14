package com.alerts;

import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.List;

/**
 * Alert condition for manual alerts triggered by patients or nurses.
 * Triggers if Alert record value is 1.0 (triggered).
 */
public class ManualAlertCondition implements AlertCondition {

    @Override
    public Alert checkCondition(Patient patient) {
        List<PatientRecord> alertRecords = patient.getAllRecords().stream()
                .filter(r -> "Alert".equals(r.getRecordType()))
                .collect(java.util.stream.Collectors.toList());

        for (PatientRecord record : alertRecords) {
            if (record.getMeasurementValue() == 1.0) {
                return new Alert(String.valueOf(patient.getPatientId()),
                        "Manual Alert Triggered: patient or nurse pressed the alert button",
                        record.getTimestamp());
            }
        }
        return null;
    }
}
