package com.transaction.processing.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.transaction.processing.model.CurrencyCode;
import com.transaction.processing.model.Transaction;
import com.transaction.processing.model.TransactionType;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Service class for logging transaction details and processing information.
 * This class handles logging in a specific format and ensures sensitive information (PAN) is masked.
 */
public class LoggingService {
    private static final String programName = "TransactionProcessing";
    private static final String LOG_FILE = programName + "_"
            + LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd")) + ".log";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Logs information about a transaction.
     *
     * @param transaction The transaction to log.
     */
    public static void logTransaction(Transaction transaction) {
        String logMessage = formatTransactionLog(transaction);
        log("INFO", logMessage);
    }

    /**
     * Logs an error message.
     *
     * @param errorMessage The error message to log.
     */
    public static void logError(String errorMessage) {
        log("ERROR", errorMessage);
    }

    /**
     * Logs a message with the specified level.
     *
     * @param level   The log level ("INFO", "ERROR").
     * @param message The message to log.
     */
    private static void log(String level, String message) {
        String timestamp = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss.SSS"));
        String logEntry = String.format("%s %s: %s", timestamp, level, message);

        try (FileWriter writer = new FileWriter(LOG_FILE, true)) {
            writer.write(logEntry + System.lineSeparator());
        } catch (IOException e) {
            System.err.println("Failed to write to log file: " + e.getMessage());
        }
    }

    /**
     * Formats the transaction log entry to meet the acceptance criteria.
     *
     * @param transaction The transaction to format.
     * @return A formatted log message for the transaction.
     */
    private static String formatTransactionLog(Transaction transaction) {
        String maskedPan = maskPan(transaction.getPan());
        String amountFormatted = String.format("%.2f", transaction.getAmount() / 100.0);
        String status = transaction.getStatus().equals("01") ? "Approved" : "Declined";
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
        CurrencyCode currency = CurrencyCode.fromNumericCode(transaction.getCurrency());

        // Build JSON transaction details
        ObjectNode transactionDetails = objectMapper.createObjectNode();
        ObjectNode transactionNode = transactionDetails.putObject("data").putObject("transaction");
        transactionNode.put("pan", maskedPan);
        transactionNode.put("type", TransactionType.fromCode(transaction.getType()).getCode());
        transactionNode.put("amount", transaction.getAmount());
        transactionNode.put("currency", currency.getNumericCode());
        transactionNode.put("status", transaction.getStatus());

        // Convert JSON to string
        String transactionDetailsJson;
        try {
            transactionDetailsJson = objectMapper.writeValueAsString(transactionDetails);
        } catch (IOException e) {
            // Fallback in case of serialization error
            transactionDetailsJson = "{}";
        }

        return String.format("Processed transaction with card number %s on %s, amount %s %s, status %s. Return transaction details: %s",
                maskedPan, timestamp, amountFormatted, currency.getAlphaCode(), status, transactionDetailsJson);
    }

    /**
     * Method to mask the middle digits of the primary account number (PAN) for security purposes:
     * <ul>
     *   <li>If the PAN is null or has a length less or more than 16, it is returned as it is (no masking is applied).</li>
     *   <li>Otherwise, the method retains the first 6 and last 4 digits of the PAN.</li>
     *   <li>The middle digits are replaced with asterisks ("******") to ensure sensitive information is not exposed.</li>
     * </ul>
     *
     * @return The masked PAN.
     */
    private static String maskPan(String pan) {
        if (pan == null) {
            return null;
        }
        if (pan.length() == 16) {
            return pan.substring(0, 6) + "******" + pan.substring(12);
        }
        return pan;
    }
}
