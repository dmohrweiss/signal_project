package com.data_management;

/**
 * Represents a single record of patient data at a specific point in time.
 */
public class PatientRecord {
    private int patientId;
    private String recordType;
    private double measurementValue; 
    private long timestamp;

    /**
     * @param patientId        the unique identifier for the patient
     * @param measurementValue the numerical value of the recorded measurement
     * @param recordType       the type of measurement
     */
    public PatientRecord(int patientId, double measurementValue, String recordType, long timestamp) {
        this.patientId = patientId;
        this.measurementValue = measurementValue;
        this.recordType = recordType;
        this.timestamp = timestamp;
    }

    /**
     * @return the patient ID
     */
    public int getPatientId() {
        return patientId;
    }

    /**
     * @return the measurement value
     */
    public double getMeasurementValue() {
        return measurementValue;
    }

    /**
     * @return the timestamp in milliseconds since epoch
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * @return the record type
     */
    public String getRecordType() {
        return recordType;
    }
}
