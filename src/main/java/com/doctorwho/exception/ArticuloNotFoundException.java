package com.doctorwho.exception;

public class ArticuloNotFoundException extends Exception {
    public ArticuloNotFoundException() {
        super("Artículo no encontrado");
    }
}