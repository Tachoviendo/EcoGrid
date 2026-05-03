package Ecogrid.modelos;
//TODO
import java.time.LocalDateTime;

//Atributos
public class Transaccion {
    private String idTransaccion;
    private String idNodo;
    private String idConsumidor;
    private Double cantidadEnergia;
    private LocalDateTime timeStamp;

//Getters - Setters
public void setIdTransaccion(String idTransaccion){
    this.idTransaccion = idTransaccion;
}

}