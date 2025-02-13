package org.example.model;

public class PlacaSolar {
    private int superficie;
    private int potencia;
    private double precio;

    public PlacaSolar(int superficie, int potencia, double precio) {
        this.superficie = superficie;
        this.potencia = potencia;
        this.precio = precio;
    }

    public int getSuperficie() {
        return superficie;
    }

    public int getPotencia() {
        return potencia;
    }

    public double getPrecio() {
        return precio;
    }

    public void setSuperficie(int superficie) {
        this.superficie = superficie;
    }

    public void setPotencia(int potencia) {
        this.potencia = potencia;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
