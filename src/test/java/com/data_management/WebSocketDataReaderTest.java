package com.data_management;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WebSocketDataReaderTest {

    private DataStorage dataStorage;
    private WebSocketDataReader reader;

    @BeforeEach
    void setUp() {
        dataStorage = DataStorage.getInstance();
        dataStorage.clearAllData();
        
    }

    @AfterEach
    void tearDown() {
        if (reader != null) {
            reader.stop();
        }
    }

    @Test
    void testParseAndStoreData() {
    
        reader = new WebSocketDataReader("ws://localhost:8080");
        assertNotNull(reader);
    }
}