package com.alerts;

import com.data_management.Patient;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AlertGeneratorTest {

    @Test
    void testBloodPressureIncreasingTrendAlert() {
        AlertGenerator alertGenerator = new AlertGenerator(null);
        Patient patient = new Patient(1);
        patient.addRecord(100.0, "SystolicPressure", 1000L);
        patient.addRecord(115.0, "SystolicPressure", 2000L);
        patient.addRecord(130.0, "SystolicPressure", 3000L);

        alertGenerator.evaluateData(patient);

        List<Alert> alerts = alertGenerator.getTriggeredAlerts();
        assertEquals(1, alerts.size());
        assertTrue(alerts.get(0).getCondition().contains("Trend Alert"));
        assertTrue(alerts.get(0).getCondition().contains("SystolicPressure"));
    }

    @Test
    void testLowSaturationAndCombinedHypotensiveHypoxemiaAlert() {
        AlertGenerator alertGenerator = new AlertGenerator(null);
        Patient patient = new Patient(2);
        patient.addRecord(85.0, "SystolicPressure", 1000L);
        patient.addRecord(91.0, "Saturation", 1200L);

        alertGenerator.evaluateData(patient);

        List<Alert> alerts = alertGenerator.getTriggeredAlerts();
        assertTrue(alerts.stream().anyMatch(a -> a.getCondition().contains("Low Saturation Alert")));
        assertTrue(alerts.stream().anyMatch(a -> a.getCondition().contains("Hypotensive Hypoxemia Alert")));
    }

    @Test
    void testRapidDropSaturationAlert() {
        AlertGenerator alertGenerator = new AlertGenerator(null);
        Patient patient = new Patient(3);
        patient.addRecord(97.0, "Saturation", 1000L);
        patient.addRecord(91.0, "Saturation", 500000L);

        alertGenerator.evaluateData(patient);

        List<Alert> alerts = alertGenerator.getTriggeredAlerts();
        assertTrue(alerts.stream().anyMatch(a -> a.getCondition().contains("Rapid Drop Alert")));
    }

    @Test
    void testManualAlertRecordTrigger() {
        AlertGenerator alertGenerator = new AlertGenerator(null);
        Patient patient = new Patient(4);
        patient.addRecord(1.0, "Alert", 1000L);

        alertGenerator.evaluateData(patient);

        List<Alert> alerts = alertGenerator.getTriggeredAlerts();
        assertEquals(1, alerts.size());
        assertTrue(alerts.get(0).getCondition().contains("Manual Alert Triggered"));
    }
}
