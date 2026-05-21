package Ecogrid.modelos;

import java.time.LocalDateTime;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class EcoGridManager {
    private static final String HISTORIAL_PATH = "historial_transacciones.txt";

    private ListaEnlazada<NodoEnergia> nodosEnergia;
    private ListaEnlazada<Consumidor> consumidores;
    private Cola<SolicitudCarga> solicitudesCarga;
    private Historial historialTransacciones = new Historial();

    public EcoGridManager() {
        this.nodosEnergia = new ListaEnlazada<>();
        this.consumidores = new ListaEnlazada<>();
        this.solicitudesCarga = new Cola<>();
    }

    public void registrarNodo(NodoEnergia nodo) {
        nodosEnergia.agregar(nodo);
    }

    public void eliminarNodo(int idNodo) {
        nodosEnergia.eliminarPorId(idNodo);
    }

    public void registrarConsumidor(Consumidor consumidor) {
        consumidores.agregar(consumidor);
    }

    public void eliminarConsumidor(int idConsumidor) {
        consumidores.eliminarPorId(idConsumidor);
    }

    public NodoEnergia buscarNodo(int id) {
        return nodosEnergia.buscarPorId(id);
    }

    public Consumidor buscarConsumidor(int id) {
        return consumidores.buscarPorId(id);
    }

    public List<NodoEnergia> listarNodos() {
        return nodosEnergia.listar();
    }

    public List<Consumidor> listarConsumidores() {
        return consumidores.listar();
    }

    public List<SolicitudCarga> listarSolicitudes() {
        return solicitudesCarga.listarElementos();
    }

    public Transaccion obtenerUltimaTransaccion() {
        return historialTransacciones.obtenerUltimaTransaccion();
    }

    public boolean vincularConsumidorANodo(int idNodo, int idConsumidor) {
        NodoEnergia nodo = nodosEnergia.buscarPorId(idNodo);
        Consumidor consumidor = consumidores.buscarPorId(idConsumidor);

        if (nodo == null || consumidor == null) {
            return false;
        }

        if (nodo.consumidorPermitido(idConsumidor)) {
            return false;
        }

        nodo.agregarConsumidorPermitido(consumidor);
        return true;
    }

    public boolean encolarNuevaSolicitud(int idConsumidor, double cantidad) {
        Consumidor consumidor = consumidores.buscarPorId(idConsumidor);
        if (consumidor == null) return false;
        SolicitudCarga nuevaSolicitud = new SolicitudCarga(consumidor, cantidad, LocalDateTime.now());
        solicitudesCarga.encolar(nuevaSolicitud);
        return true;
    }

    // Esto decide cual solicitud tiene prioridad en base a la demanda.
    public SolicitudCarga obtenerSolicitudPrioritariCarga() {
        if (solicitudesCarga.estaVacia()) {
            return null;
        }

        SolicitudCarga solicitudMayor = null;
        double demandaMayor = -1;

        for (SolicitudCarga solicitud : solicitudesCarga.listarElementos()) {
            if (solicitud.getCantidadRequerida() > demandaMayor) {
                demandaMayor = solicitud.getCantidadRequerida();
                solicitudMayor = solicitud;
            }
        }

        return solicitudMayor;
    }

    public boolean procesarSolicitud() {
        if (solicitudesCarga.estaVacia()) {
            return false;
        }

        SolicitudCarga solicitud = solicitudesCarga.desencolar();
        NodoEnergia nodoAsignado = null;
        for (NodoEnergia nodo : nodosEnergia.listar()) {
            if (nodo.getConsumidoresPermitidos().listar()
                    .contains(consumidores.buscarPorId(Integer.parseInt(solicitud.getIdConsumidor())))
                    && nodo.getCapacidadDisponible() >= solicitud.getCantidadRequerida()) {
                nodoAsignado = nodo;
                break;
            }
        }

        if (nodoAsignado != null) {
            Transaccion transaccion = new Transaccion(String.valueOf(nodoAsignado.getId()), solicitud.getIdConsumidor(),
                    solicitud.getCantidadRequerida());
            historialTransacciones.registrarTransaccion(transaccion);
            historialTransacciones.registrarEnArchivo(HISTORIAL_PATH);
            nodoAsignado.setCargaActual(nodoAsignado.getCargaActual() + solicitud.getCantidadRequerida());
            return true;
        }

        return false;
    }

    public void deshacerUltimaTransaccion() {
        Transaccion ultimaTransaccion = historialTransacciones.obtenerUltimaTransaccion();

        if (ultimaTransaccion == null) {
            System.out.println("No hay transacciones para deshacer");
            return;
        }

        // Eliminar la última transacción de la pila
        historialTransacciones.eliminarUltimaTransaccion();

        // Encontrar el nodo y devolver la energía
        NodoEnergia nodo = nodosEnergia.buscarPorId(Integer.parseInt(ultimaTransaccion.getIdNodo()));
        if (nodo != null) {
            double cargaAnterior = nodo.getCargaActual();
            nodo.setCargaActual(cargaAnterior - ultimaTransaccion.getCantidadEnergia());
            System.out.println("Transacción deshecha: Se han devuelto " + ultimaTransaccion.getCantidadEnergia()
                    + " kWh al nodo " + ultimaTransaccion.getIdNodo());
        } else {
            System.err.println("Error: No se encontró el nodo " + ultimaTransaccion.getIdNodo());
        }

        // Limpiar el archivo de historial
        try {
            new FileWriter(HISTORIAL_PATH).close();
        } catch (IOException e) {
            System.err.println("Error al limpiar el archivo de historial: " + e.getMessage());
        }

        // Reescribir el archivo sin la última transacción
        historialTransacciones.registrarEnArchivo(HISTORIAL_PATH);
    }

}
