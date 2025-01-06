package com.transaction.processing.model;

/**
 * Represents a financial transaction in the system.
 * A transaction contains information such as:
 *         primary account number,
 *         transaction type,
 *         amount,
 *         currency,
 *         status.
 */
public class Transaction {
    private final String pan;
    private final String type;
    private final int amount;
    private final String currency;
    private String status;

    /**
     * Constructor for initializing a Transaction object with all attributes.
     *
     * @param pan The Primary Account Number (PAN).
     * @param type The type of the transaction ("01" = APP, "02" = BRW, "03" = 3RI).
     * @param amount The transaction amount in subunits (1.00 EUR = 100 subunits).
     * @param currency The currency code in numeric format (978 = EUR, 840 = USD).
     * @param status The status of the transaction (00 = Active, 01 = Approved, 02 = Declined).
     */
    public Transaction(String pan, String type, int amount, String currency, String status) {
        this.pan = pan;
        this.type = type;
        this.amount = amount;
        this.currency = currency;
        this.status = status;
    }

    public String getPan() {
        return pan;
    }

    public String getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "pan='" + pan + '\'' +
                ", type='" + type + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
