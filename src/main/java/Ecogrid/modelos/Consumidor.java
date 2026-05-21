package Ecogrid.modelos;

public class Consumidor extends EntidadRed {

    private static int contador = 0; // contador de todas las instancias.
    // ATRIBUTOS
    private String nombre;
    private double demandaRequerida;
    private int prioridad;

    // GETTERS / SETTERS
    public double getDemanda() {
        return this.demandaRequerida;
    }

    public void setDemanda(double nuevaDemanda) {
        this.demandaRequerida = nuevaDemanda;
    }

    public String getNombre() {
        return this.nombre;
    }

    public int getPrioridad() {
        return this.prioridad;
    }

    // CONSTRUCTOR
    public Consumidor(String nombre, double demandaRequerida, int prioridad) {
        this.nombre = nombre;
        this.demandaRequerida = demandaRequerida;
        this.prioridad = prioridad;
        contador++;
        this.setId(contador);
    }
}
