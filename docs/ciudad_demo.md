# Ciudad Demo — EcoGrid

## Nodos de Energía

| ID | Tipo    | Capacidad máx. |
|----|---------|----------------|
| 1  | Solar   | 200 kWh        |
| 2  | Eólica  | 150 kWh        |
| 3  | Hidro   | 300 kWh        |
| 4  | Solar   | 100 kWh        |

**Capacidad total de la red: 750 kWh**

---

## Consumidores

| ID | Nombre           | Demanda   | Prioridad     |
|----|------------------|-----------|---------------|
| 1  | Hospital Central | 80 kWh    | 1 — Alta      |
| 2  | Bomberos         | 40 kWh    | 1 — Alta      |
| 3  | Barrio Norte     | 60 kWh    | 2 — Media     |
| 4  | Barrio Sur       | 60 kWh    | 2 — Media     |
| 5  | Fábrica Textil   | 120 kWh   | 3 — Baja      |
| 6  | Universidad      | 50 kWh    | 2 — Media     |

**Demanda total: 410 kWh**

---

## Red de Conexiones

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

---

## Redundancia

Consumidores con más de un nodo disponible (resilientes a fallas):

| Consumidor       | Nodos que lo alimentan |
|------------------|------------------------|
| Hospital Central | N1 (Solar), N3 (Hidro) |
| Barrio Norte     | N2 (Eólica), N3 (Hidro)|
| Barrio Sur       | N2 (Eólica), N4 (Solar)|
| Universidad      | N1 (Solar), N4 (Solar) |

Consumidores con un solo nodo (punto único de falla):

| Consumidor     | Único nodo disponible |
|----------------|-----------------------|
| Bomberos       | N1 (Solar)            |
| Fábrica Textil | N3 (Hidro)            |
