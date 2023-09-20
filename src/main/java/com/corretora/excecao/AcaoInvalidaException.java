package com.corretora.excecao;

public class AcaoInvalidaException extends Exception {
    public AcaoInvalidaException() {
    }

    public AcaoInvalidaException(String message) {
        super(message);
    }
}
