package com.transaction.processing.service;

import com.transaction.processing.model.Transaction;
import com.transaction.processing.model.TransactionStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentServiceTest {
    @Test
    void testTransactionApproved() {
        // Arrange
        Transaction transaction = new Transaction(
                "1000000000000000",
                "APP",
                1000,
                "978",
                "00"
        );

        PaymentService paymentService = new PaymentService();

        // Act
        Transaction result = paymentService.process(transaction);

        // Assert
        assertEquals(TransactionStatus.APPROVED.getCode(), result.getStatus(),
                "Transaction should be approved");
    }

    @Test
    void testTransactionDeclinedDueToInactiveStatus() {
        // Arrange
        Transaction transaction = new Transaction(
                "1000000000000000",
                "APP",
                1000,
                "978",
                "01"
        );

        PaymentService paymentService = new PaymentService();

        // Act
        Transaction result = paymentService.process(transaction);

        // Assert
        assertEquals(TransactionStatus.DECLINED.getCode(), result.getStatus(),
                "Transaction should be declined due to inactive status");
    }

    @Test
    void testTransactionDeclinedDueToInvalidType() {
        // Arrange
        Transaction transaction = new Transaction(
                "1000000000000000",
                "INVALID_TYPE",
                1000,
                "978",
                "00"
        );

        PaymentService paymentService = new PaymentService();

        // Act
        Transaction result = paymentService.process(transaction);

        // Assert
        assertEquals(TransactionStatus.DECLINED.getCode(), result.getStatus(),
                "Transaction should be declined due to invalid transaction type");
    }

    @Test
    void testTransactionDeclinedDueToInvalidPan() {
        // Arrange
        Transaction transaction = new Transaction(
                "9999999999999999",
                "APP",
                1000,
                "978",
                "00"
        );

        PaymentService paymentService = new PaymentService();

        // Act
        Transaction result = paymentService.process(transaction);

        // Assert
        assertEquals(TransactionStatus.DECLINED.getCode(), result.getStatus(),
                "Transaction should be declined due to invalid PAN");
    }

    @Test
    void testTransactionDeclinedDueToInvalidCurrency() {
        // Arrange
        Transaction transaction = new Transaction(
                "1000000000000000",
                "APP",
                1000,
                "999",
                "00"
        );

        PaymentService paymentService = new PaymentService();

        // Act
        Transaction result = paymentService.process(transaction);

        // Assert
        assertEquals(TransactionStatus.DECLINED.getCode(), result.getStatus(),
                "Transaction should be declined due to invalid currency");
    }
}