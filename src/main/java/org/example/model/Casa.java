package org.example.model;

import java.util.ArrayList;

import static org.example.vista.Main.listaCasa;


public class Casa {
    private String nif;
    private String nom;
    private int superficie;
    private boolean interruptor;
    private ArrayList<PlacaSolar> Placasolar;
    private ArrayList<org.example.model.Aparell> Aparell;

    public Casa(String nif, String nom, int superficie) {
        this.nif = nif;
        this.nom = nom;
        this.superficie = superficie;
        this.interruptor = true;
        Placasolar = new ArrayList<>();
        Aparell = new ArrayList<>();
    }

    public Casa(String nif, String nom, int superficie, boolean interruptor) {
        this.nif = nif;
        this.nom = nom;
        this.superficie = superficie;
        this.interruptor = interruptor;
        Placasolar = new ArrayList<>();
        Aparell = new ArrayList<>();
    }

    public Casa(String nif) {
        this.nif = nif;
    }

    public boolean equals(Object obj) {
        Casa c = (Casa) obj;
        return this.nif.equalsIgnoreCase(c.getNif());
    }

    public String getNif() {
        return nif;
    }

    public String getNom() {
        return nom;
    }

    public int getSuperficie() {
        return superficie;
    }

    public boolean isInterruptor() {
        return interruptor;
    }

    public boolean getInterruptor() {
        return interruptor;
    }

    public ArrayList<PlacaSolar> getPlacasolar() {
        return Placasolar;
    }

    public ArrayList<org.example.model.Aparell> getAparell() {
        return Aparell;
    }


    public void setNif(String nif) {
        this.nif = nif;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setSuperficie(int superficie) {
        this.superficie = superficie;
    }

    public void setPlacasolar(ArrayList<PlacaSolar> placasolar) {
        Placasolar = placasolar;
    }

    public void setAparell(ArrayList<org.example.model.Aparell> aparell) {
        Aparell = aparell;
    }

    public void addPlaca(PlacaSolar placasolar) {
        Placasolar.add(placasolar);
    }

    public void addAparell(org.example.model.Aparell aparell) {
        Aparell.add(aparell);
    }

    public void setInterruptor(boolean interruptor) {
        this.interruptor = interruptor;
    }

    public int EspacioRestante() {
        int superficieTotalPlacas = 0;
        for (PlacaSolar placa : Placasolar) {
            superficieTotalPlacas += placa.getSuperficie();
        }
        return superficie - superficieTotalPlacas;
    }

    public int PotenciaTotal() {
        int potenciaTotal = 0;
        for (PlacaSolar placa : Placasolar) {
            potenciaTotal += placa.getPotencia();
        }
        return potenciaTotal;
    }

    public double Inverssió() {
        double inversio = 0;
        for (PlacaSolar placa : Placasolar) {
            inversio += placa.getPrecio();
        }
        return inversio;
    }

    public int DespesaTotal() {
        int despesaTotal = 0;
        for (Aparell a : Aparell) {
            despesaTotal += a.getPotencia();
        }
        return despesaTotal;
    }


    public static boolean puedeAgregarPlaca(Casa casa, int superficiePlaca) {
        int superficieCasa = casa.getSuperficie();
        int superficieOcupada = 0;

        for (PlacaSolar placa : casa.getPlacasolar()) {
            superficieOcupada += placa.getSuperficie();
        }

        int superficieDisponible = superficieCasa - superficieOcupada;
        return superficiePlaca <= superficieDisponible;
    }

    /**
     * Este metodo no me ha dado tiempo a pasarlo a main.
     * SaltoPlomos identifica todos los aparatos de la casa para comprobar si estan encendidos tenerlos en cuenta al encender otro y que salten los plomos si sobre pasa la capacidad.
     * @param nif
     * @param descripcion
     */
    public void SaltoPlomos(String nif, String descripcion) {
        if (listaCasa.contains(new Casa(nif))) {
            int Index = listaCasa.indexOf(new Casa(nif));
            Casa c = listaCasa.get(Index);
            if (c.getAparell().contains(new Aparell(descripcion))) {
                Aparell aparell = c.getAparell().get(c.getAparell().indexOf(new Aparell(descripcion)));
                int potenciaAparell = 0;
                int potenciaTotal = c.PotenciaTotal();
                for (Aparell a : Aparell){
                    if(a.isInterruptor()){
                        potenciaAparell += a.getPotencia();
                    }
                }
                potenciaAparell += aparell.getPotencia();
                if (potenciaTotal < potenciaAparell) {
                    c.setInterruptor(false);
                    ApagarAparells();
                    System.out.println("S'ha ences l'aparell");
                    System.out.println("Han saltado los plomos de la casa");
                } else {
                    aparell.setInterruptor(true);
                    System.out.println("S'ha ences l'aparell");
                }
            }
        }
    }

    private void ApagarAparells() {
        for(Aparell c: Aparell){
            c.setInterruptor(false);
        }
    }
}

