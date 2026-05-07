package Ecogrid.modelos;

public class Pila<T extends EntidadRed> {

    private ListaEnlazada<T> elementos;

    public Pila() {
        elementos = new ListaEnlazada<>();
    }

    public void push(T elemento) {
        elementos.agregar(elemento);
    }

    public T pop() {
        if (estaVacia()) {
            return null;
        }

        return elementos.eliminarPrimero();
    }

    public T cima() {
        if (estaVacia()) {
            return null;
        }

        return elementos.obtenerPrimero();
    }

    public boolean estaVacia() {
        return elementos.estaVacia();
    }
}