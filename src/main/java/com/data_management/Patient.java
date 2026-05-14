package com.data_management;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a patient and manages their medical records.
 */
public class Patient {
    private int patientId;
    private List<PatientRecord> patientRecords;

    /**
     * @param patientId the unique identifier for the patient
     */
    public Patient(int patientId) {
        this.patientId = patientId;
        this.patientRecords = new ArrayList<>();
    }

    /**
     * Adds a new record to this patient's list of medical records.
     * @param measurementValue the measurement value to store in the record
     * @param recordType       the type of record, e.g., "HeartRate"
     * @param timestamp        the time at which the measurement was taken
     */
    public void addRecord(double measurementValue, String recordType, long timestamp) {
        PatientRecord record = new PatientRecord(this.patientId, measurementValue, recordType, timestamp);
        this.patientRecords.add(record);
    }

    /**
     * Retrieves a list of PatientRecord objects for this patient
     * @param startTime the start of the time range, in milliseconds since UNIX
     * @param endTime   the end of the time range, in milliseconds since UNIX epoch
     * @return a list of PatientRecord objects that fall within the specified time
     */
    public List<PatientRecord> getRecords(long startTime, long endTime) {
        List<PatientRecord> filtered = new ArrayList<>();
        for (PatientRecord record : patientRecords) {
            if (record.getTimestamp() >= startTime && record.getTimestamp() <= endTime) {
                filtered.add(record);
            }
        }
        filtered.sort((first, second) -> Long.compare(first.getTimestamp(), second.getTimestamp()));
        return filtered;
    }

    /**
     * @return a new list containing every patient record
     */
    public List<PatientRecord> getAllRecords() {
        return new ArrayList<>(patientRecords);
    }

    /**
     * @return the patient ID
     */
    public int getPatientId() {
        return patientId;
    }
}
