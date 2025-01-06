package com.transaction.processing.util;

import com.transaction.processing.model.TransactionType;

/**
 * Utility class for validating PANs against allowed customer-defined ranges.
 */
public class PANValidator {
    /**
     * Validates if the given PAN is within the allowed range for the specified transaction type.
     *
     * @param pan The Primary Account Number (PAN) to validate.
     * @param transactionType The transaction type (APP, BRW, 3RI).
     * @return True if the PAN is valid for the transaction type, false otherwise.
     */
    public static boolean isValidPANForType(String pan, TransactionType transactionType) {
        // PAN must be 16 numeric characters
        if (pan == null || !pan.matches("\\d{16}")) {
            return false;
        }

        long panValue = Long.parseLong(pan);

        switch (transactionType) {
            case APP:
                return panValue >= 1000000000000000L && panValue <= 1000005999999999L;
            case BRW:
                return panValue >= 2000000000000000L && panValue <= 2000001234567890L;
            case TRI:
                return panValue >= 3000000000000000L && panValue <= 3000000000000100L;
            default:
                return false;
        }
    }
}
