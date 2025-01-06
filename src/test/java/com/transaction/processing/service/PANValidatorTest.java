package com.transaction.processing.service;

import com.transaction.processing.model.TransactionType;
import com.transaction.processing.util.PANValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PANValidatorTest {

    @Test
    void testValidPANForAppType() {
        // Valid PAN for APP type
        String validAppPan = "1000000000000000";
        assertTrue(PANValidator.isValidPANForType(validAppPan, TransactionType.APP),
                "PAN should be valid for APP type");

        // Invalid PAN for APP type (out of range)
        String invalidAppPan = "2000000000000000";
        assertFalse(PANValidator.isValidPANForType(invalidAppPan, TransactionType.APP),
                "PAN should be invalid for APP type");
    }

    @Test
    void testValidPANForBrwType() {
        // Valid PAN for BRW type
        String validBrwPan = "2000000000000000";
        assertTrue(PANValidator.isValidPANForType(validBrwPan, TransactionType.BRW),
                "PAN should be valid for BRW type");

        // Invalid PAN for BRW type (out of range)
        String invalidBrwPan = "3000000000000000";
        assertFalse(PANValidator.isValidPANForType(invalidBrwPan, TransactionType.BRW),
                "PAN should be invalid for BRW type");
    }

    @Test
    void testValidPANForTriType() {
        // Valid PAN for 3RI type
        String validTriPan = "3000000000000000";
        assertTrue(PANValidator.isValidPANForType(validTriPan, TransactionType.TRI),
                "PAN should be valid for 3RI type");

        // Invalid PAN for 3RI type (out of range)
        String invalidTriPan = "4000000000000000";
        assertFalse(PANValidator.isValidPANForType(invalidTriPan, TransactionType.TRI),
                "PAN should be invalid for 3RI type");
    }

    @Test
    void testInvalidPanLength() {
        // PAN shorter than 16 digits
        String shortPan = "100000";
        assertFalse(PANValidator.isValidPANForType(shortPan, TransactionType.APP),
                "PAN shorter than 16 digits should be invalid");

        // PAN longer than 16 digits
        String longPan = "1000000000000000000";
        assertFalse(PANValidator.isValidPANForType(longPan, TransactionType.APP),
                "PAN longer than 16 digits should be invalid");
    }

    @Test
    void testNullPan() {
        // Null PAN
        assertFalse(PANValidator.isValidPANForType(null, TransactionType.APP),
                "Null PAN should be invalid");
    }

    @Test
    void testEmptyPan() {
        // Empty PAN
        String emptyPan = "";
        assertFalse(PANValidator.isValidPANForType(emptyPan, TransactionType.APP),
                "Empty PAN should be invalid");
    }
}
