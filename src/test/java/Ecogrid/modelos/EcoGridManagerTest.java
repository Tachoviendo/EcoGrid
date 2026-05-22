package Ecogrid.modelos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class EcoGridManagerTest {

    private EcoGridManager manager;
    private NodoEnergia nodo;
    private Consumidor consumidor;

    @BeforeEach
    void setup() throws Exception {
        Field fc = Consumidor.class.getDeclaredField("contador");
        fc.setAccessible(true);
        fc.set(null, 0);
        NodoEnergia.contador = 0;

        manager = new EcoGridManager();
        nodo = new NodoEnergia("solar", 200);
        consumidor = new Consumidor("Hospital", 80, 1);

        manager.registrarNodo(nodo);
        manager.registrarConsumidor(consumidor);
        manager.vincularConsumidorANodo(nodo.getId(), consumidor.getId());
    }

    @Test
    void vincularConsumidorANodoExitoso() {
        assertTrue(nodo.consumidorPermitido(consumidor.getId()));
    }

    @Test
    void vincularConsumidorInexistenteFalla() {
        boolean resultado = manager.vincularConsumidorANodo(nodo.getId(), 999);
        assertFalse(resultado);
    }

    @Test
    void procesarSolicitudAsignaEnergiaAlNodo() {
        manager.encolarNuevaSolicitud(consumidor.getId(), 80);
        boolean procesada = manager.procesarSolicitud();

        assertTrue(procesada);
        assertEquals(80, nodo.getCargaActual());
    }

    @Test
    void procesarSolicitudFallaSiNoHayCapacidad() {
        nodo.setCargaActual(200); // nodo lleno
        manager.encolarNuevaSolicitud(consumidor.getId(), 80);

        boolean procesada = manager.procesarSolicitud();
        assertFalse(procesada);
    }

    @Test
    void deshacerTransaccionDevuelveEnergia() {
        manager.encolarNuevaSolicitud(consumidor.getId(), 80);
        manager.procesarSolicitud();

        assertEquals(80, nodo.getCargaActual());

        manager.deshacerUltimaTransaccion();

        assertEquals(0, nodo.getCargaActual());
    }
}
