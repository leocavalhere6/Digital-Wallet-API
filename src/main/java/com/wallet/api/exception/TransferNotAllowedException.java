package com.wallet.api.exception;

public class TransferNotAllowedException extends RuntimeException {
    public TransferNotAllowedException(String message) {
        super(message);
    }
}
