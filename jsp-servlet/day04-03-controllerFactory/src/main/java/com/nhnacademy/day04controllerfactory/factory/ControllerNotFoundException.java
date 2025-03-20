package com.nhnacademy.day04controllerfactory.factory;

public class ControllerNotFoundException extends RuntimeException {
    public ControllerNotFoundException(String message) {
        super("Controller not found: %s" + message);
    }

    public ControllerNotFoundException(String message, String servletPath) {
        super("Controller not found: %s_%s".formatted(message,  servletPath));
    }
}
