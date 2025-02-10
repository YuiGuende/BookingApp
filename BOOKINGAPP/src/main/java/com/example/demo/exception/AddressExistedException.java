package com.example.demo.exception;

public class AddressExistedException extends RuntimeException {

    public AddressExistedException(String message) {
        super(message);
    }

}
