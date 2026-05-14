package com.alerts;

import com.data_management.DataStorage;
import com.data_management.Patient;
import com.cardio_generator.HealthDataSimulator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DesignPatternTest {

    // Factory Method Pattern Tests
    @Test
    void testBloodPressureAlertFactory() {
        AlertFactory factory = new BloodPressureAlertFactory();
        Alert alert = factory.createAlert("1", "Critical blood pressure", 1000);
        assertNotNull(alert);
        assertTrue(alert instanceof BloodPressureAlert);
        assertEquals("1", alert.getPatientId());
    }

    @Test
    void testBloodOxygenAlertFactory() {
        AlertFactory factory = new BloodOxygenAlertFactory();
        Alert alert = factory.createAlert("2", "Low oxygen saturation 92%", 1000);
        assertNotNull(alert);
        assertTrue(alert instanceof BloodOxygenAlert);
    }

    @Test
    void testEcgAlertFactory() {
        AlertFactory factory = new EcgAlertFactory();
        Alert alert = factory.createAlert("3", "Abnormal heart rate", 1000);
        assertNotNull(alert);
        assertTrue(alert instanceof EcgAlert);
    }

    // Strategy Pattern Tests
    @Test
    void testBloodPressureStrategy() {
        Patient patient = new Patient(1);
        patient.addRecord(185.0, "SystolicPressure", 1000);

        AlertStrategy strategy = new BloodPressureStrategy();
        Alert alert = strategy.checkAlert(patient);
        assertNotNull(alert);
        assertTrue(alert.getCondition().contains("Critical"));
    }

    @Test
    void testOxygenSaturationStrategy() {
        Patient patient = new Patient(2);
        patient.addRecord(85.0, "Saturation", 1000);

        AlertStrategy strategy = new OxygenSaturationStrategy();
        Alert alert = strategy.checkAlert(patient);
        assertNotNull(alert);
        assertTrue(alert.getCondition().contains("Low"));
    }

    @Test
    void testHeartRateStrategy() {
        Patient patient = new Patient(3);
        patient.addRecord(0.5, "ECG", 1000);
        patient.addRecord(3.5, "ECG", 2000);

        AlertStrategy strategy = new HeartRateStrategy();
        Alert alert = strategy.checkAlert(patient);
        assertNotNull(alert);
    }

    // Decorator Pattern Tests
    @Test
    void testPriorityAlertDecorator() {
        Alert baseAlert = new Alert("1", "Test condition", 1000);
        Alert decoratedAlert = new PriorityAlertDecorator(baseAlert, "HIGH");

        assertTrue(decoratedAlert.getCondition().contains("[HIGH]"));
        assertEquals("1", decoratedAlert.getPatientId());
        assertEquals(1000, decoratedAlert.getTimestamp());
    }

    @Test
    void testRepeatedAlertDecorator() {
        Alert baseAlert = new Alert("2", "Test condition", 1000);
        Alert decoratedAlert = new RepeatedAlertDecorator(baseAlert, 3, 5000);

        assertTrue(decoratedAlert.getCondition().contains("Repeat x3"));
        assertTrue(decoratedAlert.getCondition().contains("5000ms"));
    }

    @Test
    void testMultipleDecorators() {
        Alert baseAlert = new Alert("3", "Test condition", 1000);
        Alert prioritized = new PriorityAlertDecorator(baseAlert, "CRITICAL");
        Alert repeated = new RepeatedAlertDecorator(prioritized, 2, 10000);

        String condition = repeated.getCondition();
        assertTrue(condition.contains("[CRITICAL]"));
        assertTrue(condition.contains("Repeat x2"));
    }

    // Singleton Pattern Tests
    @Test
    void testDataStorageSingleton() {
        DataStorage storage1 = DataStorage.getInstance();
        DataStorage storage2 = DataStorage.getInstance();

        assertSame(storage1, storage2);
    }

    @Test
    void testHealthDataSimulatorSingleton() {
        HealthDataSimulator simulator1 = HealthDataSimulator.getInstance();
        HealthDataSimulator simulator2 = HealthDataSimulator.getInstance();

        assertSame(simulator1, simulator2);
    }

    @Test
    void testDataStorageSingletonPersistence() {
        DataStorage storage = DataStorage.getInstance();
        storage.addPatientData(1, 100.0, "Test", 1000);

        DataStorage storage2 = DataStorage.getInstance();
        assertTrue(storage2.getAllPatients().size() > 0);
    }
}
