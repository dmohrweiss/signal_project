package com.data_management;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import com.alerts.AlertGenerator;

/**
 * Manages storage and retrieval of patient dat.
 */
public class DataStorage {
    private static DataStorage instance;
    private Map<Integer, Patient> patientMap;

    private DataStorage() {
        this.patientMap = new ConcurrentHashMap<>();
    }

    /**
     * @return the DataStorage instance
     */
    public static synchronized DataStorage getInstance() {
        if (instance == null) {
            instance = new DataStorage();
        }
        return instance;
    }

    /**
     * Adds or updates patient data in the storage.
     *
     * @param patientId        the unique identifier of the patient
     * @param measurementValue the value of the health metric being recorded
     * @param recordType       the type of record
     * @param timestamp        the time at which the measurement was taken
     */
    public void addPatientData(int patientId, double measurementValue, String recordType, long timestamp) {
        Patient patient = patientMap.get(patientId);
        if (patient == null) {
            patient = new Patient(patientId);
            patientMap.put(patientId, patient);
        }
        patient.addRecord(measurementValue, recordType, timestamp);
    }

    /**

     * @param patientId the unique identifier of patient
     * @param startTime the start of the time range, in milliseconds
     * @param endTime   the end of the time range
     * @return a list of PatientRecord objects
     */
    public List<PatientRecord> getRecords(int patientId, long startTime, long endTime) {
        Patient patient = patientMap.get(patientId);
        if (patient != null) {
            return patient.getRecords(startTime, endTime);
        }
        return new ArrayList<>(); // return an empty list if no patient is found
    }

    /**
     * @return a list of all patients
     */
    public List<Patient> getAllPatients() {
        return new ArrayList<>(patientMap.values());
    }

    /**
     * Clears all stored patient data.
     */
    public void clearAllData() {
        patientMap.clear();
    }

    /**
     * The main method for the DataStorage class.
     * 
     * @param args command line arguments: --input <directory> for file input, --websocket <uri> for real-time input
     */
    public static void main(String[] args) {
        String inputDirectory = null;
        String websocketUri = null;
        for (int i = 0; i < args.length; i++) {
            if ("--input".equals(args[i]) && i + 1 < args.length) {
                inputDirectory = args[++i];
            } else if ("--websocket".equals(args[i]) && i + 1 < args.length) {
                websocketUri = args[++i];
            }
        }

        if (inputDirectory == null && websocketUri == null) {
            printUsage();
            return;
        }

        DataStorage storage = DataStorage.getInstance();
        try {
            DataReader reader;
            if (websocketUri != null) {
                reader = new WebSocketDataReader(websocketUri);
                reader.readData(storage);
                // For real-time, keep the program running to process data
                // Periodically evaluate alerts
                AlertGenerator alertGenerator = new AlertGenerator(storage);
                while (true) {
                    Thread.sleep(10000); // Evaluate every 10 seconds
                    for (Patient patient : storage.getAllPatients()) {
                        alertGenerator.evaluateData(patient);
                    }
                }
            } else {
                reader = new FileDataReader(inputDirectory);
                reader.readData(storage);
                AlertGenerator alertGenerator = new AlertGenerator(storage);
                for (Patient patient : storage.getAllPatients()) {
                    alertGenerator.evaluateData(patient);
                }
                System.out.println("Processed " + storage.getAllPatients().size() + " patients and evaluated alert conditions.");
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void printUsage() {
        System.out.println("Usage: java com.data_management.DataStorage [--input <output_directory>] [--websocket <uri>]");
        System.out.println("Reads patient data from files or WebSocket and evaluates alert conditions.");
        System.out.println("  --input <directory>    Read data from files in the specified directory");
        System.out.println("  --websocket <uri>      Read real-time data from WebSocket server at the specified URI (e.g., ws://localhost:8080)");
    }
}
