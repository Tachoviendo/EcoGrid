package Ecogrid.modelos;

public class Consumidor extends EntidadRed {

    private static int contador = 0; // contador de todas las instancias.
    // ATRIBUTOS
    private String nombre;
    private double demandaRequerida;

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

    // CONSTRUCTOR
    public Consumidor(String nombre, double demandaRequerida, int prioridad) {
        this.nombre = nombre;
        this.demandaRequerida = demandaRequerida;
        contador++;
        this.setId(contador);
    }
}
