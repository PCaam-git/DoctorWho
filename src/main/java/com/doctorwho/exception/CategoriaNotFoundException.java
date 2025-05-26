package com.doctorwho.exception;

public class CategoriaNotFoundException extends Exception {
    public CategoriaNotFoundException() {
        super("Categoría no encontrada");
    }
}