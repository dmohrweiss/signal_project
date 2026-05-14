package com.data_management;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PatientTest {

    @Test
    void testGetRecordsWithinRange() {
        Patient patient = new Patient(42);
        patient.addRecord(100.0, "ECG", 1000L);
        patient.addRecord(110.0, "ECG", 2000L);
        patient.addRecord(120.0, "ECG", 3000L);

        List<PatientRecord> records = patient.getRecords(1500L, 3000L);

        assertEquals(2, records.size());
        assertEquals(110.0, records.get(0).getMeasurementValue());
        assertEquals(120.0, records.get(1).getMeasurementValue());
    }
}
