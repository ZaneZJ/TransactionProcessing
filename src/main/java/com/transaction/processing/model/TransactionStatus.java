package com.transaction.processing.model;

/**
 * Enum representing the possible statuses of a transaction.
 * Each status has a unique code used for identifying the current state of the transaction.
 */
public enum TransactionStatus {
    ACTIVE("00"),
    APPROVED("01"),
    DECLINED("02");

    private final String code;

    /**
     * Constructor for initializing a TransactionStatus object with all attributes.
     *
     * @param code The status of the transaction (00 = Active, 01 = Approved, 02 = Declined).
     */
    TransactionStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    /**
     * Finds the corresponding TransactionStatus for the given status code.
     *
     * @param code The status of the transaction (00 = Active, 01 = Approved, 02 = Declined).
     * @return The matching TransactionStatus.
     * @throws IllegalArgumentException If no matching status code is found.
     */
    public static TransactionStatus fromCode(String code) {
        for (TransactionStatus status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid transaction status code: " + code);
    }
}
