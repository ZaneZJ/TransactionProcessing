package com.transaction.processing.service;

import com.transaction.processing.model.Transaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoggingServiceTest {
    private static final String LOG_FILE = "TransactionProcessing_"
            + LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd")) + ".log";

    @BeforeEach
    void setUp() {
        // Delete log file before each test to ensure clean slate
        File logFile = new File(LOG_FILE);
        if (logFile.exists()) {
            logFile.delete();
        }
    }

    @AfterEach
    void tearDown() {
        // Clean up log file after tests
        File logFile = new File(LOG_FILE);
        if (logFile.exists()) {
            logFile.delete();
        }
    }

    @Test
    void testLogTransaction() throws IOException {
        // Arrange
        Transaction transaction = new Transaction(
                "1000000000000000",
                "APP",
                1000,
                "978",
                "01"
        );

        // Act
        LoggingService.logTransaction(transaction);

        // Assert
        File logFile = new File(LOG_FILE);
        assertTrue(logFile.exists(), "Log file should be created");

        try (BufferedReader reader = new BufferedReader(new FileReader(logFile))) {
            String logEntry = reader.readLine();
            assertTrue(logEntry.contains("Processed transaction with card number 100000******0000"),
                    "Log entry should mask PAN correctly");
            assertTrue(logEntry.contains("amount 10.00 eur"),
                    "Log entry should include formatted transaction amount");
            assertTrue(logEntry.contains("status Approved"),
                    "Log entry should include transaction status");
        }
    }

    @Test
    void testLogError() throws IOException {
        // Arrange
        String errorMessage = "Test error message";

        // Act
        LoggingService.logError(errorMessage);

        // Assert
        File logFile = new File(LOG_FILE);
        assertTrue(logFile.exists(), "Log file should be created");

        try (BufferedReader reader = new BufferedReader(new FileReader(logFile))) {
            String logEntry = reader.readLine();
            assertTrue(logEntry.contains("ERROR: Test error message"),
                    "Log entry should include the error message");
        }
    }
}