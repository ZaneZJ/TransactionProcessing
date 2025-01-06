package com.transaction.processing.service;

import com.transaction.processing.model.CurrencyCode;
import com.transaction.processing.model.Transaction;
import com.transaction.processing.model.TransactionStatus;
import com.transaction.processing.model.TransactionType;
import com.transaction.processing.util.PANValidator;

/**
 * Service class responsible for processing financial transactions.
 * This class validates transactions based on their type, amount, and other attributes,
 * and assigns the appropriate status (Approved or Declined).
 */
public class PaymentService {
    /**
     * Processes a transaction based on its type, amount, and validity.
     *
     * @param transaction The transaction to process.
     * @return The processed transaction with an updated status.
     */
    public Transaction process(Transaction transaction) {
        try {
            // Validate initial transaction status
            if (!TransactionStatus.ACTIVE.getCode().equals(transaction.getStatus())) {
                transaction.setStatus(TransactionStatus.DECLINED.getCode());
                LoggingService.logError("Transaction status is not active: " + transaction.getStatus());
                return transaction;
            }

            // Validate transaction type
            TransactionType type;
            try {
                type = TransactionType.fromCode(transaction.getType());
            } catch (IllegalArgumentException e) {
                transaction.setStatus(TransactionStatus.DECLINED.getCode());
                LoggingService.logError("Invalid transaction type: " + transaction.getType());
                return transaction;
            }

            // Validate currency
            try {
                CurrencyCode.fromNumericCode(transaction.getCurrency());
            } catch (IllegalArgumentException e) {
                transaction.setStatus(TransactionStatus.DECLINED.getCode());
                LoggingService.logError("Invalid currency code: " + transaction.getCurrency());
                return transaction;
            }

            // Validate PAN
            if (!PANValidator.isValidPANForType(transaction.getPan(), type)) {
                transaction.setStatus(TransactionStatus.DECLINED.getCode());
                LoggingService.logError("Invalid PAN for type: " + transaction.getPan());
                return transaction;
            }

            // Validate the transaction amount
            if (transaction.getAmount() > type.getMaxAmount()) {
                transaction.setStatus(TransactionStatus.DECLINED.getCode());
                LoggingService.logError("Transaction amount exceeds limit: " + transaction.getAmount());
                return transaction;
            }

            // Approve the transaction if all validations pass
            transaction.setStatus(TransactionStatus.APPROVED.getCode());
            return transaction;

        } finally {
            try {
                LoggingService.logTransaction(transaction);
            } catch (IllegalArgumentException e) {
                LoggingService.logError("Error during logging transaction: " + e.getMessage());
            }
        }
    }
}
