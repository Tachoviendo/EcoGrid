package Ecogrid.modelos;

public class Nodo<T> {
    //Atributos
    private T dato;
    private Nodo<T> siguiente;

    //Constructor
    public Nodo(T dato){
        this.dato = dato;
        this.siguiente = null;
    }

    //Getters - Setters
    public T getDato(){
        return dato;
    }

    public void setDato(T dato){
        this.dato = dato;
    }

    public Nodo<T> getSiguiente(){
        return this.siguiente;
    }

    public void setSiguiente(Nodo<T> siguiente){
        this.siguiente = siguiente;
    }
}
