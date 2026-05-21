package Ecogrid.modelos;

public class NodoEnergia extends EntidadRed {

    public static int contador = 0;

    // Atributos
    private final double capacidadMaxima;
    private double cargaActual;

    // Consumidores permitidos (no quiero ni saber lo que nos va a costar esto)
    private ListaEnlazada<Consumidor> consumidoresPermitidos;

    // Constructor
    public NodoEnergia(double capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
        this.cargaActual = 0;
        this.consumidoresPermitidos = new ListaEnlazada<>();

        contador++;
        this.setId(contador);
    }

    // Getters

    public double getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public double getCargaActual() {
        return cargaActual;
    }

    public ListaEnlazada<Consumidor> getConsumidoresPermitidos() {
        return consumidoresPermitidos;
    }

    // Setters

    public void setCargaActual(double cargaActual) {
        this.cargaActual = cargaActual;
    }

    public void setConsumidoresPermitidos(ListaEnlazada<Consumidor> consumidoresPermitidos) {
        this.consumidoresPermitidos = consumidoresPermitidos;
    }

    // Métodos

    public double getCapacidadDisponible() {
        return this.capacidadMaxima - this.cargaActual;
    }

    public void agregarConsumidorPermitido(Consumidor consumidor) {
        consumidoresPermitidos.agregarAlFinal(consumidor);
    }

    public void eliminarConsumidorPermitido(int idConsumidor) {
        consumidoresPermitidos.eliminarPorId(idConsumidor);
    }

    public boolean consumidorPermitido(int idConsumidor) {
        return consumidoresPermitidos.buscarPorId(idConsumidor) != null;
    }
}