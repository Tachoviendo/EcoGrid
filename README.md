# EcoGrid — Simulador de Red Eléctrica Inteligente

Proyecto universitario para la materia **Algoritmos y Estructuras de Datos**. EcoGrid simula la distribución de energía renovable en una ciudad: nodos generadores (solar, eólico, hidro) reciben solicitudes de consumidores y las atienden respetando capacidades y vínculos preestablecidos. Toda asignación queda registrada y puede deshacerse.

---

## El problema

Una red eléctrica inteligente tiene que resolver tres cosas al mismo tiempo:

1. **Gestionar la infraestructura** — qué nodos generan energía, cuánto pueden entregar, y a qué consumidores están conectados.
2. **Atender la demanda** — las solicitudes de carga llegan en cualquier momento y hay que procesarlas en orden, asignando el primer nodo disponible con capacidad suficiente.
3. **Mantener trazabilidad** — cada asignación se registra y se puede deshacer si algo salió mal.

El proyecto implementa todo esto **sin usar las colecciones de Java** (`ArrayList`, `Stack`, etc.). Cada estructura de datos está construida desde cero.

---

## Estructuras de datos implementadas

| Estructura | Clase | Uso en el sistema |
|---|---|---|
| Lista enlazada | `ListaEnlazada<T>` | Almacena nodos de energía, consumidores y los vínculos entre ellos |
| Cola (FIFO) | `Cola<T>` | Gestiona la cola de solicitudes de carga pendientes |
| Pila (LIFO) | `Pila<T>` | Historial de transacciones para el undo |
| Nodo genérico | `Nodo<T>` | Elemento base de lista y pila |

---

## Arquitectura

```
App (CLI)
  └── EcoGridManager          ← orquestador central
        ├── ListaEnlazada<NodoEnergia>     ← infraestructura generadora
        ├── ListaEnlazada<Consumidor>      ← entidades que consumen energía
        ├── Cola<SolicitudCarga>           ← demanda pendiente
        └── Historial                      ← Pila<Transaccion> + persistencia en .txt
```

**Jerarquía de dominio:**
```
EntidadRed  (id autoincremental)
  ├── NodoEnergia   (tipo, capacidadMaxima, cargaActual, consumidoresPermitidos)
  └── Consumidor    (nombre, demanda, prioridad 1-3)
```

**Flujo de una solicitud:**
1. Se encola una `SolicitudCarga` (consumidor + cantidad + timestamp).
2. `procesarSolicitud()` desencola la solicitud y recorre los nodos buscando el primero que: (a) tenga al consumidor en su lista de permitidos, y (b) tenga capacidad disponible.
3. Si encuentra un nodo, registra la `Transaccion` en el historial y actualiza la carga del nodo.
4. Si no encuentra ninguno, la solicitud falla.

---

## Cómo correr el proyecto

**Requisitos:** Java 17+ y Maven.

```bash
# compilar
mvn compile

# ejecutar
mvn exec:java -Dexec.mainClass="Ecogrid.App"

# tests
mvn test
```

Al iniciar, el menú principal ofrece:

```
1. Gestión de Infraestructura   → registrar/buscar/eliminar nodos y consumidores, vincularlos
2. Gestión de Demanda           → encolar solicitudes, procesar una a una, ver la cola
3. Historial                    → ver última transacción, deshacer
8. Ejecutar simulación          → simula toda la demanda de la ciudad de una vez
9. Cargar ciudad demo           → carga el escenario de ejemplo preconfigurado
```

---

## Ciudad demo

Escenario hardcodeado para probar el sistema rápido. Se carga con la opción **9** del menú.

### Nodos de energía

| ID | Tipo | Capacidad |
|----|------|-----------|
| 1 | Solar | 200 kWh |
| 2 | Eólica | 150 kWh |
| 3 | Hidro | 300 kWh |
| 4 | Solar | 100 kWh |

**Capacidad total: 750 kWh**

### Consumidores

| ID | Nombre | Demanda | Prioridad |
|----|--------|---------|-----------|
| 1 | Hospital Central | 80 kWh | Alta |
| 2 | Bomberos | 40 kWh | Alta |
| 3 | Barrio Norte | 60 kWh | Media |
| 4 | Barrio Sur | 60 kWh | Media |
| 5 | Fábrica Textil | 120 kWh | Baja |
| 6 | Universidad | 50 kWh | Media |

**Demanda total: 410 kWh**

### Red de conexiones

```
  NODOS                          CONSUMIDORES
  ──────                         ────────────

  [N1] Solar 200kWh  ────────── [C1] Hospital Central  80kWh  ★★★
                     ────────── [C2] Bomberos           40kWh  ★★★
                     ────────── [C6] Universidad        50kWh  ★★

  [N2] Eólica 150kWh ────────── [C3] Barrio Norte      60kWh  ★★
                     ────────── [C4] Barrio Sur         60kWh  ★★

  [N3] Hidro 300kWh  ────────── [C5] Fábrica Textil   120kWh  ★
                     ────────── [C1] Hospital Central  80kWh  ★★★
                     ────────── [C3] Barrio Norte      60kWh  ★★

  [N4] Solar 100kWh  ────────── [C4] Barrio Sur        60kWh  ★★
                     ────────── [C6] Universidad        50kWh  ★★

  ★★★ = Prioridad Alta   ★★ = Prioridad Media   ★ = Prioridad Baja
```

### Redundancia

Consumidores con más de un nodo disponible (toleran la caída de un nodo):

| Consumidor | Nodos |
|---|---|
| Hospital Central | N1 (Solar), N3 (Hidro) |
| Barrio Norte | N2 (Eólica), N3 (Hidro) |
| Barrio Sur | N2 (Eólica), N4 (Solar) |
| Universidad | N1 (Solar), N4 (Solar) |

Consumidores con un único nodo (punto único de falla):

| Consumidor | Nodo |
|---|---|
| Bomberos | N1 (Solar) |
| Fábrica Textil | N3 (Hidro) |

---

## Decisiones de diseño destacadas

- **Prioridad por demanda:** cuando hay varias solicitudes en cola, `obtenerSolicitudPrioritariaCarga()` devuelve la de mayor demanda — no hay un heap, se recorre la cola linealmente.
- **Persistencia simple:** cada vez que se registra o deshace una transacción, el historial se reescribe completo en `historial_transacciones.txt`.
- **IDs autoincrementales:** `EntidadRed` lleva un contador estático de clase; cada nueva entidad recibe el siguiente ID disponible.

---

## Estructura del repositorio

```
src/
  main/java/Ecogrid/
    App.java                    ← CLI principal
    modelos/
      EntidadRed.java           ← base de dominio
      NodoEnergia.java
      Consumidor.java
      SolicitudCarga.java
      Transaccion.java
      Historial.java
      EcoGridManager.java       ← lógica de negocio
      CiudadDemo.java           ← escenario de prueba
      ListaEnlazada.java
      Cola.java
      Pila.java
      Nodo.java
  test/java/Ecogrid/
    AppTest.java
docs/
  ciudad_demo.md                ← detalle del escenario demo
```
