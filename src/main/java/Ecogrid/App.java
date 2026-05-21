package Ecogrid;

import Ecogrid.modelos.*;
import java.util.List;
import java.util.Scanner;

public class App {

    private static final EcoGridManager manager = new EcoGridManager();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== EcoGrid - Simulador de Red Eléctrica ===");
        int opcion;
        do {
            System.out.println("\n--- Menú Principal ---");
            System.out.println("1. Gestión de Infraestructura");
            System.out.println("2. Gestión de Demanda");
            System.out.println("3. Historial");
            System.out.println("8. Ejecutar simulación");
            System.out.println("9. Cargar ciudad demo");
            System.out.println("0. Salir");
            opcion = leerInt("Opción: ");
            switch (opcion) {
                case 1 -> menuInfraestructura();
                case 2 -> menuDemanda();
                case 3 -> menuHistorial();
                case 8 -> ejecutarSimulacion();
                case 9 -> CiudadDemo.cargar(manager);
                case 0 -> System.out.println("Hasta luego.");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
        scanner.close();
    }

    // menu!
    private static void menuInfraestructura() {
        int opcion;
        do {
            System.out.println("\n--- Infraestructura ---");
            System.out.println("1. Registrar Nodo de Energía");
            System.out.println("2. Registrar Consumidor");
            System.out.println("3. Vincular Consumidor a Nodo");
            System.out.println("4. Buscar Nodo por ID");
            System.out.println("5. Buscar Consumidor por ID");
            System.out.println("6. Eliminar Nodo");
            System.out.println("7. Eliminar Consumidor");
            System.out.println("8. Listar Nodos");
            System.out.println("9. Listar Consumidores");
            System.out.println("0. Volver");
            opcion = leerInt("Opción: ");
            switch (opcion) {
                case 1 -> registrarNodo();
                case 2 -> registrarConsumidor();
                case 3 -> vincularConsumidorANodo();
                case 4 -> buscarNodo();
                case 5 -> buscarConsumidor();
                case 6 -> eliminarNodo();
                case 7 -> eliminarConsumidor();
                case 8 -> listarNodos();
                case 9 -> listarConsumidores();
                case 0 -> {}
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    private static void registrarNodo() {
        String tipo = leerString("Tipo de fuente (solar/eolica/hidro/otro): ");
        double capacidad = leerDouble("Capacidad máxima (kWh): ");
        NodoEnergia nodo = new NodoEnergia(tipo, capacidad);
        manager.registrarNodo(nodo);
        System.out.println("Nodo registrado con ID: " + nodo.getId());
    }

    private static void registrarConsumidor() {
        String nombre = leerString("Nombre: ");
        double demanda = leerDouble("Demanda requerida (kWh): ");
        int prioridad = leerInt("Prioridad (1=alta, 2=media, 3=baja): ");
        Consumidor c = new Consumidor(nombre, demanda, prioridad);
        manager.registrarConsumidor(c);
        System.out.println("Consumidor registrado con ID: " + c.getId());
    }

    private static void vincularConsumidorANodo() {
        int idNodo = leerInt("ID del nodo: ");
        int idConsumidor = leerInt("ID del consumidor: ");
        boolean ok = manager.vincularConsumidorANodo(idNodo, idConsumidor);
        if (ok) {
            System.out.println("Vinculación exitosa.");
        } else {
            System.out.println("No se pudo vincular: nodo/consumidor inexistente o ya estaban vinculados.");
        }
    }

    private static void buscarNodo() {
        int id = leerInt("ID del nodo: ");
        NodoEnergia nodo = manager.buscarNodo(id);
        if (nodo == null) {
            System.out.println("Nodo no encontrado.");
        } else {
            imprimirNodo(nodo);
        }
    }

    private static void buscarConsumidor() {
        int id = leerInt("ID del consumidor: ");
        Consumidor c = manager.buscarConsumidor(id);
        if (c == null) {
            System.out.println("Consumidor no encontrado.");
        } else {
            imprimirConsumidor(c);
        }
    }

    private static void eliminarNodo() {
        int id = leerInt("ID del nodo a eliminar: ");
        manager.eliminarNodo(id);
        System.out.println("Nodo eliminado.");
    }

    private static void eliminarConsumidor() {
        int id = leerInt("ID del consumidor a eliminar: ");
        manager.eliminarConsumidor(id);
        System.out.println("Consumidor eliminado.");
    }

    private static void listarNodos() {
        List<NodoEnergia> nodos = manager.listarNodos();
        if (nodos.isEmpty()) {
            System.out.println("No hay nodos registrados.");
            return;
        }
        System.out.println("\n--- Nodos de Energía ---");
        for (NodoEnergia n : nodos) {
            imprimirNodo(n);
        }
    }

    private static void listarConsumidores() {
        List<Consumidor> consumidores = manager.listarConsumidores();
        if (consumidores.isEmpty()) {
            System.out.println("No hay consumidores registrados.");
            return;
        }
        System.out.println("\n--- Consumidores ---");
        for (Consumidor c : consumidores) {
            imprimirConsumidor(c);
        }
    }

    //demanda
    private static void menuDemanda() {
        int opcion;
        do {
            System.out.println("\n--- Gestión de Demanda ---");
            System.out.println("1. Encolar Solicitud de Carga");
            System.out.println("2. Procesar Solicitud");
            System.out.println("3. Ver Cola de Solicitudes");
            System.out.println("0. Volver");
            opcion = leerInt("Opción: ");
            switch (opcion) {
                case 1 -> encolarSolicitud();
                case 2 -> procesarSolicitud();
                case 3 -> listarSolicitudes();
                case 0 -> {}
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    private static void encolarSolicitud() {
        int idConsumidor = leerInt("ID del consumidor: ");
        double cantidad = leerDouble("Cantidad requerida (kWh): ");
        boolean ok = manager.encolarNuevaSolicitud(idConsumidor, cantidad);
        if (ok) {
            System.out.println("Solicitud encolada.");
        } else {
            System.out.println("Consumidor no encontrado.");
        }
    }

    private static void procesarSolicitud() {
        if (manager.listarSolicitudes().isEmpty()) {
            System.out.println("La cola está vacía.");
            return;
        }
        boolean ok = manager.procesarSolicitud();
        if (ok) {
            System.out.println("Solicitud procesada y energía asignada.");
        } else {
            System.out.println("No se encontró ningún nodo disponible para esta solicitud.");
        }
    }

    private static void listarSolicitudes() {
        List<SolicitudCarga> solicitudes = manager.listarSolicitudes();
        if (solicitudes.isEmpty()) {
            System.out.println("La cola está vacía.");
            return;
        }
        System.out.println("\n--- Cola de Solicitudes ---");
        int pos = 1;
        for (SolicitudCarga s : solicitudes) {
            System.out.println("  " + pos++ + ". Consumidor ID: " + s.getIdConsumidor()
                    + " | Cantidad: " + s.getCantidadRequerida() + " kWh"
                    + " | Fecha: " + s.getFecha());
        }
    }

    // ── Historial ─────────────────────────────────────────────────────────────────

    private static void menuHistorial() {
        int opcion;
        do {
            System.out.println("\n--- Historial ---");
            System.out.println("1. Ver última transacción");
            System.out.println("2. Deshacer última transacción");
            System.out.println("0. Volver");
            opcion = leerInt("Opción: ");
            switch (opcion) {
                case 1 -> verUltimaTransaccion();
                case 2 -> manager.deshacerUltimaTransaccion();
                case 0 -> {}
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    private static void verUltimaTransaccion() {
        Transaccion t = manager.obtenerUltimaTransaccion();
        if (t == null) {
            System.out.println("No hay transacciones registradas.");
        } else {
            System.out.println("\n--- Última Transacción ---");
            System.out.println("  ID:         " + t.getIdTransaccion());
            System.out.println("  Nodo:       " + t.getIdNodo());
            System.out.println("  Consumidor: " + t.getIdConsumidor());
            System.out.println("  Energía:    " + t.getCantidadEnergia() + " kWh");
            System.out.println("  Fecha:      " + t.getTimeStamp());
        }
    }

    // utilidades
    private static void ejecutarSimulacion() {
        if (manager.listarNodos().size() < 2) {
            System.out.println("Necesitás al menos 2 nodos cargados para ejecutar la simulación.");
            return;
        }
        if (manager.listarConsumidores().isEmpty()) {
            System.out.println("No hay consumidores registrados.");
            return;
        }

        System.out.println("\nEjecutando simulación...");
        int[] resultado = manager.simularDemandaCiudad();
        int procesadas = resultado[0];
        int fallidas   = resultado[1];

        System.out.println("\n+--------------------------------------+");
        System.out.println("|        RESUMEN DE SIMULACION         |");
        System.out.println("+--------------------------------------+");
        System.out.printf( "|  Solicitudes atendidas:   %-10d|%n", procesadas);
        System.out.printf( "|  Sin nodo disponible:     %-10d|%n", fallidas);
        System.out.println("+--------------------------------------+");

        List<Transaccion> transacciones = manager.listarTransacciones();
        if (!transacciones.isEmpty()) {
            System.out.println("\n--- Transacciones generadas ---");
            for (Transaccion t : transacciones) {
                System.out.println("  [" + t.getIdTransaccion() + "]"
                        + " Nodo " + t.getIdNodo()
                        + " -> Consumidor " + t.getIdConsumidor()
                        + " | " + t.getCantidadEnergia() + " kWh"
                        + " | " + t.getTimeStamp());
            }
        }
    }

    private static void imprimirNodo(NodoEnergia n) {
        System.out.println("  [ID " + n.getId() + "] " + n.getTipoFuente()
                + " | Cap. máx: " + n.getCapacidadMaxima() + " kWh"
                + " | Carga actual: " + n.getCargaActual() + " kWh"
                + " | Disponible: " + n.getCapacidadDisponible() + " kWh");
    }

    private static void imprimirConsumidor(Consumidor c) {
        System.out.println("  [ID " + c.getId() + "] " + c.getNombre()
                + " | Demanda: " + c.getDemanda() + " kWh"
                + " | Prioridad: " + c.getPrioridad());
    }

    private static int leerInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Ingresá un número entero válido.");
            }
        }
    }

    private static double leerDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Ingresá un número válido.");
            }
        }
    }

    private static String leerString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}
