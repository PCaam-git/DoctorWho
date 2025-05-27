package com.doctorwho.exception;

public class ArticuloNotFoundException extends Exception {
    
    public ArticuloNotFoundException() {
        super("Artículo no encontrado");
    }
    
    public ArticuloNotFoundException(String message) {
        super(message);
    }
    
    public ArticuloNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}