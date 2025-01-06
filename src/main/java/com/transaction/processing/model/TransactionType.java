package com.transaction.processing.model;

/**
 * Enum representing transaction types and their associated metadata.
 * Each type has a unique code and a maximum allowed transaction amount.
 */
public enum TransactionType {
    APP("01", 5000),
    BRW("02", 10000),
    TRI("03", 2000);

    private final String code;
    private final int maxAmount;

    /**
     * Constructor for initializing a TransactionStatus object with all attributes.
     *
     * @param code The numeric code of the transaction ("01", "02", "03").
     * @param maxAmount Maximum amount allowed for this type (1.00 EUR = 100 subunits).
     */
    TransactionType(String code, int maxAmount) {
        this.code = code;
        this.maxAmount = maxAmount;
    }

    public String getCode() {
        return code;
    }

    public int getMaxAmount() {
        return maxAmount;
    }

    /**
     * Finds the corresponding TransactionType for a given code.
     *
     * @param code The type of the transaction (APP, BRW, 3RI).
     * @return The matching TransactionType.
     * @throws IllegalArgumentException If no matching transaction type is found.
     */
    public static TransactionType fromCode(String code) {
        for (TransactionType type : values()) {
            if (type.name().equals(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid transaction type code: " + code);
    }
}
