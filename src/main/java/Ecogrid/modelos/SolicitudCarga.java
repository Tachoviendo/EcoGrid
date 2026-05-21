package Ecogrid.modelos;

//ASUMO QUE NO SE MODIFICA UNA VEZ CREADA LA SOLICITUD

import java.time.LocalDateTime;

public class SolicitudCarga extends EntidadRed {

    private static int contador = 0;

    // ATRIBUTOS
    private final String idConsumidor;
    private final double cantidadRequerida;
    private final LocalDateTime fecha;

    // GETTERS / SETTERS
    public String getIdConsumidor() {
        return this.idConsumidor;
    }

    public double getCantidadRequerida() {
        return this.cantidadRequerida;
    }

    public String getFecha() {
        return this.fecha.toString();
    }

    public SolicitudCarga(Consumidor consumidor, double cantidadRequerida, LocalDateTime fecha) {
        this.idConsumidor = String.valueOf(consumidor.getId());
        this.cantidadRequerida = cantidadRequerida;
        this.fecha = fecha;
        this.setId(++contador);
    }

}
