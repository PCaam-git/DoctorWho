package com.doctorwho.exception;

public class UsuarioNotFoundException extends Exception {
    
    public UsuarioNotFoundException() {
        super("Usuario no encontrado");
    }
    
    public UsuarioNotFoundException(String message) {
        super(message);
    }
    
    public UsuarioNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}