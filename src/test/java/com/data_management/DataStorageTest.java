package com.data_management;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataStorageTest {

    @Test
    void testAddAndGetRecords() {
        DataStorage storage = DataStorage.getInstance();
        storage.clearAllData();

        storage.addPatientData(1, 120.0, "SystolicPressure", 1714376789050L);
        storage.addPatientData(1, 80.0, "DiastolicPressure", 1714376789051L);

        List<PatientRecord> records = storage.getRecords(1, 1714376789050L, 1714376789051L);

        assertEquals(2, records.size());
        assertEquals("SystolicPressure", records.get(0).getRecordType());
        assertEquals(120.0, records.get(0).getMeasurementValue());
        assertEquals("DiastolicPressure", records.get(1).getRecordType());
    }
}
