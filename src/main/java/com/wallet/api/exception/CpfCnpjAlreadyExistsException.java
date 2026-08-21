package com.wallet.api.exception;

public class CpfCnpjAlreadyExistsException extends RuntimeException {
    public CpfCnpjAlreadyExistsException(String cpfCnpj) {
        super("CPF/CNPJ already registered: " + cpfCnpj);
    }
}
