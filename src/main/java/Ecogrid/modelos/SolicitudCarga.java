package Ecogrid.modelos;

public class SolicitudCarga {

    // ATRIBUTOS
    private String idConsumidor;
    private double cantidadRequerida;
    private String fecha;

    // GETTERS / SETTERS
    public String getIdConsumidor() {
        return this.idConsumidor;
    }

    public double getCantidadRequerida() {
        return this.cantidadRequerida;
    }

    public String getFecha() {
        return this.fecha;
    }

    public SolicitudCarga(String idConsumidor, double cantidadRequerida, String fecha) {
        this.idConsumidor = idConsumidor;
        this.cantidadRequerida = cantidadRequerida;
        this.fecha = fecha;
    }

}
