package Ecogrid.modelos;

//ASUMO QUE NO SE MODIFICA UNA VEZ CREADA LA SOLICITUD

import java.time.LocalDateTime;

public class SolicitudCarga {

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

    public SolicitudCarga(String idConsumidor, double cantidadRequerida, LocalDateTime fecha) {
        this.idConsumidor = idConsumidor;
        this.cantidadRequerida = cantidadRequerida;
        this.fecha = fecha;
    }

}
