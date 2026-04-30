package Ecogrid.modelos;

public class NodoEnergia extends EntidadRed {
    private final double capacidadMaxima;
    private double cargaActual;

    //TODO: Consumidores Permitidos.
    

    public double getCapacidadDisponible(){
        return this.capacidadMaxima - this.cargaActual;
    }

    //TODO: ID ¿Automatico o Fijo en constructor? ¿Asignaion de consumidores permitidos?
    public NodoEnergia(double capacidadMaxima){
        this.capacidadMaxima = capacidadMaxima;
    }
}
