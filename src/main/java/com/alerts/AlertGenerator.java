package com.alerts;

import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * class is responsible for monitoring patient data
 * and generating alerts when conditions from the project 
 * Document 3 from the project define.
 */
public class AlertGenerator {
    private final DataStorage dataStorage;
    private final List<Alert> triggeredAlerts = new ArrayList<>();
    private final List<AlertCondition> conditions = new ArrayList<>();

    public AlertGenerator(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
        initializeConditions();
    }

    private void initializeConditions() {
        conditions.add(new BloodPressureTrendCondition());
        conditions.add(new BloodPressureCriticalCondition());
        conditions.add(new HypotensiveHypoxemiaCondition());
        conditions.add(new SaturationLowCondition());
        conditions.add(new SaturationRapidDropCondition());
        conditions.add(new EcgAbnormalCondition());
        conditions.add(new ManualAlertCondition());
    }

    public List<Alert> getTriggeredAlerts() {
        return new ArrayList<>(triggeredAlerts);
    }

    public void clearAlerts() {
        triggeredAlerts.clear();
    }

    public void evaluateData(Patient patient) {
        if (patient == null) {
            return;
        }

        for (AlertCondition condition : conditions) {
            Alert alert = condition.checkCondition(patient);
            if (alert != null) {
                triggerAlert(alert);
            }
        }
    }

    private void triggerAlert(Alert alert) {
        if (alert == null) {
            return;
        }
        triggeredAlerts.add(alert);
        System.out.println("ALERT: " + alert.getPatientId() + " -> " + alert.getCondition() + " @ "
                + alert.getTimestamp());
    }
}
