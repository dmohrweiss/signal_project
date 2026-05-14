package com.data_management;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * WebSocketDataReader implements the DataReader interface to handle real-time data
 * from a WebSocket server.
 */
public class WebSocketDataReader implements DataReader {
    private static final Pattern DATA_PATTERN = Pattern.compile("Patient ID:\\s*(\\d+),\\s*Timestamp:\\s*(\\d+),\\s*Label:\\s*([^,]+),\\s*Data:\\s*(.+)");
    private final String serverUri;
    private WebSocketClient client;
    private DataStorage dataStorage;

    /**
     * @param serverUri the URI of the WebSocket server (e.g., "ws://localhost:8080")
     */
    public WebSocketDataReader(String serverUri) {
        this.serverUri = serverUri;
    }

    /**
     * @param dataStorage the DataStorage instance to store parsed data
     * @throws IOException if there is an error establishing the WebSocket connection
     */
    @Override
    public void readData(DataStorage dataStorage) throws IOException {
        this.dataStorage = dataStorage;
        try {
            URI uri = new URI(serverUri);
            client = new WebSocketClient(uri) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    System.out.println("Connected to WebSocket server: " + serverUri);
                }

                @Override
                public void onMessage(String message) {
                    try {
                        parseAndStoreData(message);
                    } catch (Exception e) {
                        System.err.println("Error processing WebSocket message: " + e.getMessage());
                    }
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    System.out.println("WebSocket connection closed: " + reason);
                }

                @Override
                public void onError(Exception ex) {
                    System.err.println("WebSocket error: " + ex.getMessage());
                }
            };
            client.connect();

        } catch (URISyntaxException e) {
            throw new IOException("Invalid WebSocket URI: " + serverUri, e);
        }
    }

    /**
     * @param message the raw message from WebSocket
     */
    private void parseAndStoreData(String message) {
        Matcher matcher = DATA_PATTERN.matcher(message.trim());
        if (matcher.matches()) {
            try {
                int patientId = Integer.parseInt(matcher.group(1));
                long timestamp = Long.parseLong(matcher.group(2));
                String label = matcher.group(3);
                String data = matcher.group(4);

                // Process data similar to FileDataReader
                double measurementValue;
                if (data.endsWith("%")) {
                    measurementValue = Double.parseDouble(data.substring(0, data.length() - 1));
                } else if ("triggered".equals(data)) {
                    measurementValue = 1.0;
                } else if ("resolved".equals(data)) {
                    measurementValue = 0.0;
                } else {
                    measurementValue = Double.parseDouble(data);
                }

                dataStorage.addPatientData(patientId, measurementValue, label, timestamp);
            } catch (NumberFormatException e) {
                System.err.println("Invalid data format in message: " + message);
            }
        } else {
            System.err.println("Message does not match expected format: " + message);
        }
    }

    /**
     * Stops connection.
     */
    public void stop() {
        if (client != null) {
            client.close();
        }
    }
}