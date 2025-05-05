package com.patricia.exception;

public class ArticuloNotFoundException extends Exception {
    public ArticuloNotFoundException() {
        super("Artículo no encontrado");
    }
}