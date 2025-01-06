package com.transaction.processing.model;

/**
 * Enum representing supported currencies and their associated metadata.
 * Each currency has a numeric code and a symbol for identification.
 */
public enum CurrencyCode {
    EUR("978", "eur"),
    USD("840", "usd");

    private final String numericCode;
    private final String alphaCode;

    /**
     * Constructor for initializing a CurrencyCode object with all attributes.
     *
     * @param numericCode The currency code in numeric format (978 = EUR, 840 = USD).
     * @param alphaCode Symbol code for the currency ("EUR", "USD").
     */
    CurrencyCode(String numericCode, String alphaCode) {
        this.numericCode = numericCode;
        this.alphaCode = alphaCode;
    }

    public String getNumericCode() {
        return numericCode;
    }

    public String getAlphaCode() {
        return alphaCode;
    }

    /**
     * Finds the corresponding CurrencyCode for the given numeric currency code.
     *
     * @param numericCode The currency code in numeric format (978 = EUR, 840 = USD).
     * @return The matching CurrencyCode.
     * @throws IllegalArgumentException If no matching currency code is found.
     */
    public static CurrencyCode fromNumericCode(String numericCode) {
        for (CurrencyCode currency : values()) {
            if (currency.getNumericCode().equals(numericCode)) {
                return currency;
            }
        }
        throw new IllegalArgumentException("Invalid currency code: " + numericCode);
    }
}
