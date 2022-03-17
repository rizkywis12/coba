package com.binar.grab.util;


public class ResourceNotFoundException extends Exception {

    public ResourceNotFoundException() {
    }

    public ResourceNotFoundException(String msg) {
        super(msg);
    }
}