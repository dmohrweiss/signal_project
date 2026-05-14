package com.data_management;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileDataReaderTest {

    @Test
    void testReadDataFromFile(@TempDir Path tempDir) throws IOException {
        String content = "Patient ID: 1, Timestamp: 1000, Label: SystolicPressure, Data: 130.0\n"
                + "Patient ID: 1, Timestamp: 2000, Label: Saturation, Data: 90%\n"
                + "Patient ID: 1, Timestamp: 3000, Label: Alert, Data: triggered\n";
        Path inputFile = tempDir.resolve("data.txt");
        Files.writeString(inputFile, content);

        DataStorage storage = DataStorage.getInstance();
        storage.clearAllData();
        FileDataReader reader = new FileDataReader(tempDir.toString());
        reader.readData(storage);

        List<PatientRecord> records = storage.getRecords(1, 0, Long.MAX_VALUE);
        assertEquals(3, records.size());
        assertTrue(records.stream().anyMatch(r -> r.getRecordType().equals("SystolicPressure")
                && r.getMeasurementValue() == 130.0));
        assertTrue(records.stream().anyMatch(r -> r.getRecordType().equals("Saturation")
                && r.getMeasurementValue() == 90.0));
        assertTrue(records.stream().anyMatch(r -> r.getRecordType().equals("Alert")
                && r.getMeasurementValue() == 1.0));
    }
}
