package com.doctorwho.exception;

public class VentaNotFoundException extends Exception {
    public VentaNotFoundException() {
        super("Venta no encontrada");
    }
}