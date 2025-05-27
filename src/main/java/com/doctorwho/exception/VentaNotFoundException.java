package com.doctorwho.exception;

public class VentaNotFoundException extends Exception {
    
    public VentaNotFoundException() {
        super("Venta no encontrada");
    }
    
    public VentaNotFoundException(String message) {
        super(message);
    }
    
    public VentaNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}