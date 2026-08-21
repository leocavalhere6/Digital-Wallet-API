package com.wallet.api.exception;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException() {
        super("Payer has insufficient balance for this transfer");
    }
}
