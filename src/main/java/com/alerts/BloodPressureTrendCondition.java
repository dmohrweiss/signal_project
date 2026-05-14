package com.alerts;

import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.List;

/**
 * Trigger an alert if the patient's blood pressure (systolic or diastolic)
 *  shows a consistent increase or decrease across three consecutive readings
 *  where each reading changes by more than 10 mmHg from the last.
 */
public class BloodPressureTrendCondition implements AlertCondition {

    @Override
    public Alert checkCondition(Patient patient) {
        List<PatientRecord> systolic = patient.getAllRecords().stream()
                .filter(r -> "SystolicPressure".equals(r.getRecordType()))
                .sorted((a, b) -> Long.compare(a.getTimestamp(), b.getTimestamp()))
                .collect(java.util.stream.Collectors.toList());

        List<PatientRecord> diastolic = patient.getAllRecords().stream()
                .filter(r -> "DiastolicPressure".equals(r.getRecordType()))
                .sorted((a, b) -> Long.compare(a.getTimestamp(), b.getTimestamp()))
                .collect(java.util.stream.Collectors.toList());

        Alert systolicAlert = checkTrend(systolic, "SystolicPressure", patient.getPatientId());
        if (systolicAlert != null) return systolicAlert;

        Alert diastolicAlert = checkTrend(diastolic, "DiastolicPressure", patient.getPatientId());
        return diastolicAlert;
    }

    private Alert checkTrend(List<PatientRecord> readings, String label, int patientId) {
        if (readings.size() < 3) return null;

        for (int i = 2; i < readings.size(); i++) {
            double first = readings.get(i - 2).getMeasurementValue();
            double second = readings.get(i - 1).getMeasurementValue();
            double third = readings.get(i).getMeasurementValue();
            double delta1 = second - first;
            double delta2 = third - second;

            if (Math.abs(delta1) > 10 && Math.abs(delta2) > 10 && delta1 * delta2 > 0) {
                String direction = delta1 > 0 ? "increasing" : "decreasing";
                return new Alert(String.valueOf(patientId),
                        String.format("Trend Alert: %s %s over three readings", label, direction),
                        readings.get(i).getTimestamp());
            }
        }
        return null;
    }
}
