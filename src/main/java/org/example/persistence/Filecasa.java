package org.example.persistence;

import org.example.model.PlacaSolar;
import org.example.model.Aparell;
import org.example.model.Casa;

import java.io.*;
import java.util.ArrayList;

import static org.example.vista.Main.listaCasa;

public class Filecasa {
    String nameCasa = "casas.txt";
    String namePlaca = "placas.txt";
    String nameAparell = "aparell.txt";
    String nameFolder = "dades";
    String ruta;
    String pathFileCasa;
    String pathFilePlaca;
    String pathFileAparell;

    public Filecasa() throws IOException {
        ruta = "." + File.separator + nameFolder;
        pathFileCasa = ruta + File.separator + nameCasa;
        pathFilePlaca = ruta + File.separator + namePlaca;
        pathFileAparell = ruta + File.separator + nameAparell;
        File folder = new File(ruta);
        if (!folder.exists()) {
            folder.mkdir();
        }
        File archivo = new File(pathFileCasa);
        if (!archivo.exists()) {
            archivo.createNewFile();
        }

        File archivo2 = new File(pathFilePlaca);
        if (!archivo2.exists()) {
            archivo2.createNewFile();
        }

        File archivo3 = new File(pathFileAparell);
        if (!archivo3.exists()) {
            archivo3.createNewFile();
        }
    }

    public void writeCasasInFile(Casa p) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(pathFileCasa, true));
        writer.write(p.getNif() + "," + p.getNom() + "," + p.getSuperficie() + "," + p.getInterruptor());
        writer.newLine();
        writer.close();
    }

    public ArrayList<Casa> readCasa() throws IOException {
        ArrayList<Casa> Casas = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(pathFileCasa));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            String nif = data[0];
            String nom = data[1];
            int superficie = Integer.parseInt(data[2]);
            boolean interruptor = Boolean.parseBoolean(data[3]);
            Casas.add(new Casa(nif, nom, superficie, interruptor));
        }
        reader = new BufferedReader(new FileReader(pathFilePlaca));
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            String nif = data[0];
            int superficie = Integer.parseInt(data[1]);
            int potencia = Integer.parseInt(data[2]);
            double precio = Double.parseDouble(data[3]);
            PlacaSolar placa = new PlacaSolar(superficie, potencia, precio);
            int index = Casas.indexOf(new Casa(nif));
            Casas.get(index).addPlaca(placa);
        }
        reader = new BufferedReader(new FileReader(pathFileAparell));
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            String nif = data[0];
            String descripcion = data[1];
            int potencia = Integer.parseInt(data[2]);
            boolean interruptor = Boolean.parseBoolean(data[3]);
            Aparell aparell = new Aparell(descripcion, potencia, interruptor);
            int index = Casas.indexOf(new Casa(nif));
            Casas.get(index).addAparell(aparell);
        }
        return Casas;
    }

    public void borrar(ArrayList<Casa> p) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(pathFileCasa, false));
        for (int i = 0; i < listaCasa.size(); i++) {
            writer.write(p.get(i).getNif() + "," + p.get(i).getNom() + "," + p.get(i).getSuperficie() + "," + p.get(i).isInterruptor());
            writer.newLine();
        }
        writer.close();
    }

    public void writePlacaInFile(PlacaSolar s, String nif) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(pathFilePlaca, true));
        writer.write(nif + "," + s.getSuperficie() + "," + s.getPotencia() + "," + s.getPrecio());
        writer.newLine();
        writer.close();
    }

    public void writeAparellInFile(Aparell a, String nif) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(pathFileAparell, true));
        writer.write(nif + "," + a.getDescripcion() + "," + a.getPotencia() + "," + a.isInterruptor());
        writer.newLine();
        writer.close();
    }

    public void reescribirCasa(ArrayList<Casa> casa) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(pathFileCasa, false));
        for (Casa i : casa) {
            writeCasasInFile(i);
        }
    }

    public void reescribirAparell(ArrayList<Casa> casas) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(pathFileAparell, false));
        for (Casa i : casas) {
            for (Aparell a : i.getAparell()) {
                writeAparellInFile(a, i.getNif());
            }
        }
    }
}