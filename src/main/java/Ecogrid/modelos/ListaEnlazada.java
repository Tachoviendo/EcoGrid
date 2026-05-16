package Ecogrid.modelos;

import java.util.ArrayList;
import java.util.List;

public class ListaEnlazada<T extends EntidadRed> {
    // Atributos
    private Nodo<T> cabeza;
    private int tamano;

    // Metodos

    // 1. cabeza -> 10 -> 20 -> null
    // 2. nuevo -> 10 -> 20 -> null
    // 3. cabeza -> nuevo -> 10 -> 20 -> null
    public void agregar(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        nuevo.setSiguiente(cabeza);
        cabeza = nuevo;
    }

    public void eliminarPorId(int id) {
        if (cabeza == null) {
            return;
        }

        if (cabeza.getDato().getId() == id) {
            cabeza = cabeza.getSiguiente();
            return;
        }

        Nodo<T> actual = cabeza;

        while (actual.getSiguiente() != null && actual.getSiguiente().getDato().getId() != id) {
            actual = actual.getSiguiente();
        }

        if (actual.getSiguiente() != null) {
            actual.setSiguiente(actual.getSiguiente().getSiguiente());
        }
    }

    public T buscarPorId(int id) {
        Nodo<T> actual = cabeza;

        while (actual != null) {
            if (actual.getDato().getId() == id) {
                return actual.getDato();
            }
            actual = actual.getSiguiente();
        }

        return null;
    }

    public List<T> listar() {
        List<T> lista = new ArrayList<>();
        Nodo<T> actual = cabeza;

        while (actual != null) {
            lista.add(actual.getDato());
            actual = actual.getSiguiente();
        }

        return lista;
    }

    public void agregarAlFinal(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);

        if (cabeza == null) {
            cabeza = nuevo;
            return;
        }

        Nodo<T> actual = cabeza;

        while (actual.getSiguiente() != null) {
            actual = actual.getSiguiente();
        }

        actual.setSiguiente(nuevo);
    }

    public T eliminarPrimero() {
        if (cabeza == null) {
            return null;
        }

        T dato = cabeza.getDato();
        cabeza = cabeza.getSiguiente();

        return dato;
    }

    // Retorna el primer elemento sin eliminarlo
    public T obtenerPrimero() {
        if (cabeza == null) {
            return null;
        }

        return cabeza.getDato();
    }

    public boolean estaVacia() {
        return cabeza == null;
    }
}
