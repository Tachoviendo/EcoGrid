package Ecogrid.modelos;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class Historial {
    private Pila<Transaccion> transacciones;

    public Historial() {
        this.transacciones = new Pila<>();
    }

    public void registrarTransaccion(Transaccion transaccion) {
        if (transaccion != null) {
            this.transacciones.apilar(transaccion);
        }
    }

    public Transaccion obtenerUltimaTransaccion() {
        return this.transacciones.cima();
    }

    public boolean estaVacio() {
        return this.transacciones.estaVacia();
    }

    public Transaccion eliminarUltimaTransaccion() {
        return this.transacciones.pop();
    }

    public void registrarEnArchivo(String ruta) {
        try {
            FileWriter fw = new FileWriter(ruta, true); // true = append
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write("=== Registro de Historial de Transacciones ===");
            bw.newLine();

            bw.write("Fecha de registro: " + LocalDateTime.now());
            bw.newLine();

            bw.write("-------------------------------------------");
            bw.newLine();

            // Pila temporal para restaurar datos
            Pila<Transaccion> pilaTemp = new Pila<>();

            while (!transacciones.estaVacia()) {
                Transaccion transaccion = transacciones.pop();

                if (transaccion != null) {
                    bw.write("ID Transacción: " + transaccion.getIdTransaccion());
                    bw.newLine();

                    bw.write("  Nodo: " + transaccion.getIdNodo());
                    bw.newLine();

                    bw.write("  Consumidor: " + transaccion.getIdConsumidor());
                    bw.newLine();

                    bw.write("  Cantidad Energía: " + transaccion.getCantidadEnergia() + " kWh");
                    bw.newLine();

                    bw.write("  Fecha/Hora: " + transaccion.getTimeStamp());
                    bw.newLine();

                    bw.newLine();

                    pilaTemp.apilar(transaccion);
                }
            }

            // Restaurar pila original
            while (!pilaTemp.estaVacia()) {
                transacciones.apilar(pilaTemp.pop());
            }

            bw.write("-------------------------------------------");
            bw.newLine();
            bw.newLine();

            bw.close();
            fw.close();

        } catch (IOException e) {
            System.err.println("Error al grabar el historial en archivo: " + e.getMessage());
        }
    }

    // PROBABLEMENTE NO LO USEMOS PERO QUEDA POR LAS DUDAS.
    public void registrarTransaccionEnArchivo(String ruta, Transaccion transaccion) {
        if (transaccion == null) {
            return;
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(ruta, true))) {
            writer.println("ID Transacción: " + transaccion.getIdTransaccion());
            writer.println("  Nodo: " + transaccion.getIdNodo());
            writer.println("  Consumidor: " + transaccion.getIdConsumidor());
            writer.println("  Cantidad Energía: " + transaccion.getCantidadEnergia() + " kWh");
            writer.println("  Fecha/Hora: " + transaccion.getTimeStamp());
            writer.println("");
            writer.flush();
        } catch (IOException e) {
            System.err.println("Error al grabar la transacción en archivo: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
