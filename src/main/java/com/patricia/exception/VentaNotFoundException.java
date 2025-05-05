package com.patricia.exception;

public class VentaNotFoundException extends Exception {
    public VentaNotFoundException() {
        super("Venta no encontrada");
    }
}