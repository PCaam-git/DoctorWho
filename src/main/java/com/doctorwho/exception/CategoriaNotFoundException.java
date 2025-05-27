package com.doctorwho.exception;

public class CategoriaNotFoundException extends Exception {
    
    public CategoriaNotFoundException() {
        super("Categoría no encontrada");
    }
    
    public CategoriaNotFoundException(String message) {
        super(message);
    }
    
    public CategoriaNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}