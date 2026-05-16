package Ecogrid.modelos;

import java.util.List;

public class Cola<T extends EntidadRed> {

    private ListaEnlazada<T> elementos;

    public Cola() {
        elementos = new ListaEnlazada<>();
    }

    public void encolar(T elemento) {
        elementos.agregarAlFinal(elemento);
    }

    public List<T> listarElementos() {
        return elementos.listar();
    }

    public T desencolar() {
        if (estaVacia()) {
            return null;
        }

        return elementos.eliminarPrimero();
    }

    public boolean estaVacia() {
        return elementos.estaVacia();
    }

    public boolean eliminarElemento(T elemento) {
        List<T> lista = elementos.listar();
        if (lista.contains(elemento)) {
            // Reconstruir la lista sin el elemento
            ListaEnlazada<T> nuevaLista = new ListaEnlazada<>();
            for (T item : lista) {
                if (!item.equals(elemento)) {
                    nuevaLista.agregarAlFinal(item);
                }
            }
            this.elementos = nuevaLista;
            return true;
        }
        return false;
    }
}