package com.example.BankSystemApp.exception;

public class BankNotFoundException extends RuntimeException{

    public BankNotFoundException() {
        super();
    }

    public BankNotFoundException (String message) {
        super(message);
    }
}
