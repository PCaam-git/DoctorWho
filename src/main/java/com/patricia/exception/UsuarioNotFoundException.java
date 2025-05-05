package com.patricia.exception;

public class UsuarioNotFoundException extends Exception {
    public UsuarioNotFoundException() {
        super("Usuario no encontrado");
    }
}