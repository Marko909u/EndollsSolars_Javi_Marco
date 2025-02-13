package org.example.vista;

import org.example.model.PlacaSolar;
import org.example.model.Aparell;
import org.example.model.Casa;
import org.example.persistence.Filecasa;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    private static AskData ask;
    public static ArrayList<Casa> listaCasa = new ArrayList<>();
    private static Filecasa copia1;

    public static void main(String[] args) {
        try {
            copia1 = new Filecasa();
            ask = new AskData();
            listaCasa = copia1.readCasa();
            System.out.println();
            System.out.println("=== Endolls Solars ===");
            int opcion;
            do {
                System.out.println("1. Afegir casa.");
                System.out.println("2. Afegir placa.");
                System.out.println("3. Afegir aparell.");
                System.out.println("4. Encendre interruptor general de la casa.");
                System.out.println("5. Encendre un aparell.");
                System.out.println("6. Apagar un aparell.");
                System.out.println("7. Veure les cases.");
                System.out.println("8. Veure informació d'una casa.");
                System.out.println("9. Sortir");
                System.out.println();
                opcion = ask.askInt("Què vols fer avui?: ");

                switch (opcion) {
                    case 1 -> NovaCasa(ask);
                    case 2 -> NuevaPlaca(ask);
                    case 3 -> NouAparell(ask);
                    case 4 -> EncendreCasa(ask);
                    case 5 -> EncenderAparell(ask);
                    case 6 -> ApagarAparell(ask);
                    case 7 -> MostrarCasas(ask);
                    case 8 -> InformacionDetallada(ask);
                    case 9 -> System.out.println("Hasta la proxima...");
                    default -> System.out.println("opcion incorrecta");
                }

            } while (opcion != 9);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * El metodo NovaCasa contiene diferentes funciones de la Clase AskData en las cuales tenemos que registrar un NIF válido para identificar la casa y otros atributos como la superficie y el nombre del dueño.
     * Se guarda la información tanto en la Array de casas como een el Archivo de casas ubicado en la carpeta dades.
     *
     * @param AskData
     * @throws IOException
     */

    private static void NovaCasa(AskData AskData) throws IOException {
        System.out.println("*** Nova Casa ***");
        String nif = ask.askNif("Nif del propietari de la casa: ", "Nif erroneo");
        if (listaCasa.contains(new Casa(nif))) {
            System.out.println("Error: Casa ya existe.");
        } else {
            String nom = ask.askString("Nom del propietari de la casa: ");
            int superficie = ask.askInt("Superficie de la casa:", "No puede ser menor a 10", 10);
            Casa novacasa = new Casa(nif, nom, superficie);
            listaCasa.add(novacasa);
            copia1.writeCasasInFile(novacasa);
            System.out.println("OK: Casa Registrada.");
        }
    }

    /**
     * La función MostrarCasas enseña ordenadamente y como especifica la práctica, la forma correcta de mostrar todas las casas es haciendo un bucle con toda la información de las casas y haciendo una variable
     * para definir el número de la casa creada que se va mostrando por cada una.
     *
     * @param AskData
     */

    private static void MostrarCasas(AskData AskData) {
        System.out.println("--- Endolls Solars, S.L. ---");
        System.out.println("Cases Enregistrades: " + listaCasa.size());
        System.out.println("");
        int contador = 0;
        for (Casa casa : listaCasa) {
            contador++;
            System.out.println("Casa: "+contador);
            System.out.println("Client: " + casa.getNif() + " " + casa.getNom());
            System.out.println("Superficie de la teulada: " + casa.getSuperficie());
            System.out.println("Superficie disponible: " + casa.EspacioRestante());
            System.out.println("Interruptor general:" + casa.getInterruptor());
            System.out.println("Plaques solars instal·lades: " + casa.getPlacasolar().size());
            System.out.println("Aparells registrats: " + casa.getAparell().size());
            System.out.println();
        }
    }

    /**
     * La funcion posterior nos sirve para adjuntar una nueva placa solar para una casa especificada por su NIF, a la vez que se le pide al usuario la información necesaria para saber si cabe o no teniendo en cuenta la superficie de la casa
     * Separando por su puesto el precio y la potencia a la hora de guardar la información del objeto en un Array dentro de la clase Casa para que forme parte de ella.
     *
     * @param Askdata
     * @throws IOException
     */

    private static void NuevaPlaca(AskData Askdata) throws IOException {
        if (listaCasa.isEmpty()) {
            System.out.println("No hay casas registradas.");
        } else {
            System.out.println("*** Nova Placa Solar ***");
            String nif = ask.askNif("Nif del propietari de la casa: ", "Nif erroneo");
            if (listaCasa.contains(new Casa(nif))) {
                System.out.println("Nif Correcto");
                int Index = listaCasa.indexOf(new Casa(nif));
                Casa c = listaCasa.get(Index);
                if (!EspacioCasa(nif)) {
                    System.out.println("No hi ha espai per a afegir una nova placa.");
                } else {
                    int superficie = ask.askInt("Superficie de la placa: ", "Tiene que ser minimo 1.", 1);
                    int potencia = ask.askInt("potencia de la placa:", "Tiene que ser minimo 1.", 1);
                    double precio = ask.askDouble("Precio placa: ", "Tiene que ser minimo 0.1.", 0.1);
                    PlacaSolar novaplaca = new PlacaSolar(superficie, potencia, precio);
                    c.addPlaca(novaplaca);
                    copia1.writePlacaInFile(novaplaca, nif);

                }

            } else {
                System.out.println("No existeix cap asa amb el nif indicat.");
            }

        }
    }

    /**
     * Usando la Funcion Espacio casa, corroboramos que a la hora de comparar la superficie de la placa con la de la casa, no ponga algo que no pueda caber dentro del límite establecido.
     *
     * @param nif
     * @return
     */

    private static boolean EspacioCasa(String nif) {
        for (Casa casa : listaCasa) {
            if (casa.getNif().equals(nif)) {
                int superficieCasa = casa.getSuperficie();
                int superficieOcupada = 0;
                boolean espacioDisponible;

                for (PlacaSolar placa : casa.getPlacasolar()) {
                    superficieOcupada += placa.getSuperficie();
                }
                if (superficieOcupada >= superficieCasa) {
                    espacioDisponible = false;
                } else {
                    int superficieDisponible = superficieCasa - superficieOcupada;
                    espacioDisponible = true;
                }
                return espacioDisponible;
            }
        }
        return false;
    }

    /**
     * Con este metodo concluimos con la integración de la casa, le pedimos nuevamente el NIF correcto de una casa ya creada previamente para posterior pedirle la descripcion unica del aparato la cual no puede ser igual a otra ya creada.
     * Para la información restante que es la potencia que sea mayor a uno y se guardaria como en el metodo anterior en una Array exclusiva de la casa que se ha especificado.
     *
     * @param AskData
     * @throws IOException
     */

    private static void NouAparell(AskData AskData) throws IOException {
        System.out.println("*** Nou Aparell");
        String nif = ask.askNif("Nif del propietari de la casa: ", "Nif erroneo");
        if (listaCasa.contains(new Casa(nif))) {
            System.out.println("Nif Correcto");
            int Index = listaCasa.indexOf(new Casa(nif));
            Casa c = listaCasa.get(Index);
            String descripcion = ask.askString("Descripció del aparell: ");
            if (c.getAparell().contains(new Aparell(descripcion))) {
                System.out.println("Error: Esta descripcion ya existe.");
            } else {
                int potencia = ask.askInt("Potencia del aparell: ", "Tiene que ser minimo 1.", 1);
                Aparell nouaparell = new Aparell(descripcion, potencia);
                c.addAparell(nouaparell);
                System.out.println("OK: Aparell registrada.");
                copia1.writeAparellInFile(nouaparell, nif);
            }

        } else {
            System.out.println("Nif Incorrecto");
        }
    }

    /**
     * En los ultimos metodods como en EncendreCasa dependen de la situacion de los objetos. En este caso si al encender los aparatos el interruptor de la casa se baja (False) este metodo nos sirve para que vuelva a estar en funcionamiento.
     * En el caso que la casa esté encendida (True) devuelve un mensaje acorde.
     *
     * @param AskData
     */

    private static void EncendreCasa(AskData AskData) throws IOException {
        System.out.println("*** Encendre Casa ***");
        String nif = ask.askNif("Nif del propietari de la casa: ", "Nif erroneo");
        if (listaCasa.contains(new Casa(nif))) {
            System.out.println("Nif Correcto");
            int Index = listaCasa.indexOf(new Casa(nif));
            Casa c = listaCasa.get(Index);
            if (c.isInterruptor()) {
                System.out.println("La casa ya esta encesa");
            } else {
                c.setInterruptor(true);
                copia1.reescribirCasa(listaCasa);
                System.out.println("La casa ha sigut encesa.");
            }
        } else {
            System.out.println("No existeix aquesta casa");
        }
    }

    /**
     * En el metodo ApagarAparell, como su nombre indica, nos sirve para devolver a la normalidad la estancia del interruptor de los aparatos (false)
     * Si ya está en esa instancia, devuelve el mensaje correspondiente.
     *
     * @param AskData
     */

    private static void ApagarAparell(AskData AskData) throws IOException {
        System.out.println("*** Apagar Aparell ***");
        String nif = ask.askNif("Nif del propietari de la casa: ", "Nif erroneo");
        if (listaCasa.contains(new Casa(nif))) {
            System.out.println("Nif Correcto");
            int Index = listaCasa.indexOf(new Casa(nif));
            Casa c = listaCasa.get(Index);
            String descripcion = ask.askString("Descripció del aparell: ");
            if (c.getAparell().contains(new Aparell(descripcion))) {
                System.out.println("Aparell Encontrado");
                Aparell aparell = c.getAparell().get(c.getAparell().indexOf(new Aparell(descripcion)));
                if (!aparell.isInterruptor()) {
                    System.out.println("L'aparell ja està apagat.");
                } else {
                    aparell.apagarAparell();
                    copia1.reescribirAparell(listaCasa);
                    System.out.println("L'aparell ha estat apagat.");
                }
            } else {
                System.out.println("No s'ha trobat l'aparell.");
            }
        } else {
            System.out.println("No existeix aquesta casa");
        }
    }

    /**
     * Este metodo es más completo que el MostrarCasas, en InformaciónDetallada nos devuelve toda la información posible sobre la potencia de la casa como lo que gastan todos los aparatos.
     * Conjunto con el número de cada uno de los aparatos y las placas.
     *
     * @param AskData
     */

    private static void InformacionDetallada(AskData AskData) {
        System.out.println("*** Informació Detallada ***");
        String nif = ask.askNif("Nif del propietari de la casa: ", "Nif erroneo");
        if (listaCasa.contains(new Casa(nif))) {
            System.out.println("Nif Correcto");
            int Index = listaCasa.indexOf(new Casa(nif));
            Casa c = listaCasa.get(Index);
            System.out.println("Client: " + c.getNif() + " " + c.getNom());
            System.out.println("Plaques solars instal·lades: " + c.getPlacasolar().size());
            System.out.println("Potencia Total: " + c.PotenciaTotal()+"W");
            System.out.println("Inversió total: " + c.Inverssió()+"€");
            System.out.println("Aparells registrats: " + c.getAparell().size());
            System.out.println("Consum Actual: " + c.DespesaTotal()+"W");
            for (Aparell aparell : c.getAparell()) {
                if (aparell.isInterruptor()) {
                    System.out.println("Aparells encesos:");
                    System.out.println("- " + aparell.getDescripcion());
                }
            }
        }
    }

    /**
     * EndencerAparell sirve expresamente para activar un aparato de una casa específica, sin enmabrgo, si el aparato y la suma de los aparatos encendidos super la pptencia
     * total de la casa, apaga la casa y los demás aparatos.
     * @param AskData
     * @throws IOException
     */

    private static void EncenderAparell(AskData AskData) throws IOException {
        System.out.println("*** Encender Aparell ***");
        String nif = ask.askNif("Nif del propietari de la casa: ", "Nif erroneo");
        if (listaCasa.contains(new Casa(nif))) {
            System.out.println("Nif Correcto");
            int Index = listaCasa.indexOf(new Casa(nif));
            Casa c = listaCasa.get(Index);
            String descripcion = ask.askString("Descripció del aparell: ");
            if (c.getAparell().contains(new Aparell(descripcion))) {
                System.out.println("Aparell Encontrado");
                Aparell aparell = c.getAparell().get(c.getAparell().indexOf(new Aparell(descripcion)));
                if (aparell.isInterruptor()) {
                    System.out.println("El aparell ja està encesa.");
                } else {
                    c.SaltoPlomos(nif,descripcion);
                    copia1.reescribirCasa(listaCasa);
                    copia1.reescribirAparell(listaCasa);
                }
            }
        }
    }


}