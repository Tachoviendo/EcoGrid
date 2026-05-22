package Ecogrid.modelos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class ListaEnlazadaTest {

    @BeforeEach
    void resetContadores() throws Exception {
        Field fc = Consumidor.class.getDeclaredField("contador");
        fc.setAccessible(true);
        fc.set(null, 0);
        NodoEnergia.contador = 0;
    }

    @Test
    void agregarYBuscarPorId() {
        ListaEnlazada<Consumidor> lista = new ListaEnlazada<>();
        Consumidor c = new Consumidor("Hospital", 80, 1);
        lista.agregar(c);

        Consumidor encontrado = lista.buscarPorId(c.getId());
        assertNotNull(encontrado);
        assertEquals("Hospital", encontrado.getNombre());
    }

    @Test
    void buscarPorIdRetornaNullSiNoExiste() {
        ListaEnlazada<Consumidor> lista = new ListaEnlazada<>();
        lista.agregar(new Consumidor("Bomberos", 40, 1));

        assertNull(lista.buscarPorId(999));
    }

    @Test
    void eliminarPorIdRemueveElElemento() {
        ListaEnlazada<Consumidor> lista = new ListaEnlazada<>();
        Consumidor c = new Consumidor("Barrio Norte", 60, 2);
        lista.agregar(c);

        lista.eliminarPorId(c.getId());

        assertNull(lista.buscarPorId(c.getId()));
        assertTrue(lista.estaVacia());
    }

    @Test
    void listaVaciaAlCrear() {
        ListaEnlazada<Consumidor> lista = new ListaEnlazada<>();
        assertTrue(lista.estaVacia());
    }

    @Test
    void agregarVariosYListarTodos() {
        ListaEnlazada<Consumidor> lista = new ListaEnlazada<>();
        lista.agregar(new Consumidor("C1", 10, 1));
        lista.agregar(new Consumidor("C2", 20, 2));
        lista.agregar(new Consumidor("C3", 30, 3));

        assertEquals(3, lista.listar().size());
    }
}
