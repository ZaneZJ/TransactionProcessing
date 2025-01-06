package com.transaction.processing;

import com.transaction.processing.model.Transaction;
import com.transaction.processing.service.PaymentService;

public class App {
    public static void main( String[] args ) {
        Transaction transaction = new Transaction(
                "1000000000000000",
                "APP",
                1000,
                "978",
                "00"
        );

        PaymentService service = new PaymentService();
        Transaction result = service.process(transaction);

        System.out.println("Transaction status: " + result.getStatus());
    }
}
