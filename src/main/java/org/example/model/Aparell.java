package org.example.model;

public class Aparell {
    private String descripcion;
    private int potencia;
    private boolean interruptor;

    public Aparell(String descripcion, int potencia, boolean interruptor) {
        this.descripcion = descripcion;
        this.potencia = potencia;
        this.interruptor = interruptor;
    }

    public Aparell(String descripcion, int potencia) {
        this.descripcion = descripcion;
        this.potencia = potencia;
        this.interruptor = false;
    }

    public Aparell(String descipcion) {
        this.descripcion = descipcion;
    }

    public boolean equals(Object obj) {
        Aparell a = (Aparell) obj;
        return this.descripcion.equalsIgnoreCase(a.getDescripcion());
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getPotencia() {
        return potencia;
    }

    public boolean isInterruptor() {
        return interruptor;
    }

    public void setInterruptor(boolean interruptor) {
        this.interruptor = interruptor;
    }
    public void apagarAparell() {
        interruptor = false;
    }

    public void EncenderAparell() {
        interruptor = true;
    }
}
