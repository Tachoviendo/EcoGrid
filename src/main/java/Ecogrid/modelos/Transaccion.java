package Ecogrid.modelos;

import java.time.LocalDateTime;

public class Transaccion extends EntidadRed {

    public static int contador = 0; // contador de todas las instancias.

    // Atributos
    private String idTransaccion;
    private String idNodo;
    private String idConsumidor;
    private Double cantidadEnergia;
    private LocalDateTime timeStamp;

    // Getters

    public String getIdTransaccion() {
        return idTransaccion;
    }

    public String getIdNodo() {
        return idNodo;
    }

    public String getIdConsumidor() {
        return idConsumidor;
    }

    public Double getCantidadEnergia() {
        return cantidadEnergia;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    // Setters

    public void setIdTransaccion(String idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public void setIdNodo(String idNodo) {
        this.idNodo = idNodo;
    }

    public void setIdConsumidor(String idConsumidor) {
        this.idConsumidor = idConsumidor;
    }

    public void setCantidadEnergia(Double cantidadEnergia) {
        this.cantidadEnergia = cantidadEnergia;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Transaccion(String idNodo, String idConsumidor, Double cantidadEnergia) {
        this.idNodo = idNodo;
        this.idConsumidor = idConsumidor;
        this.cantidadEnergia = cantidadEnergia;
        this.timeStamp = LocalDateTime.now();
        this.idTransaccion = "T" + (++contador);
    }
}