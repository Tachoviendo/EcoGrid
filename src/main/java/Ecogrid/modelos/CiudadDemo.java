package Ecogrid.modelos;

public class CiudadDemo {

    public static void cargar(EcoGridManager manager) {

        // Nodos de energía
        NodoEnergia solar1    = new NodoEnergia("solar",   200.0);
        NodoEnergia eolica1   = new NodoEnergia("eolica",  150.0);
        NodoEnergia hidro1    = new NodoEnergia("hidro",   300.0);
        NodoEnergia solar2    = new NodoEnergia("solar",   100.0);

        manager.registrarNodo(solar1);
        manager.registrarNodo(eolica1);
        manager.registrarNodo(hidro1);
        manager.registrarNodo(solar2);

        // Consumidores
        Consumidor hospital      = new Consumidor("Hospital Central",    80.0, 1);
        Consumidor bomberos      = new Consumidor("Bomberos",            40.0, 1);
        Consumidor barrio1       = new Consumidor("Barrio Norte",        60.0, 2);
        Consumidor barrio2       = new Consumidor("Barrio Sur",          60.0, 2);
        Consumidor fabrica       = new Consumidor("Fábrica Textil",     120.0, 3);
        Consumidor universidad   = new Consumidor("Universidad",         50.0, 2);

        manager.registrarConsumidor(hospital);
        manager.registrarConsumidor(bomberos);
        manager.registrarConsumidor(barrio1);
        manager.registrarConsumidor(barrio2);
        manager.registrarConsumidor(fabrica);
        manager.registrarConsumidor(universidad);

        // Vínculos nodo-consumidor
        manager.vincularConsumidorANodo(solar1.getId(),  hospital.getId());
        manager.vincularConsumidorANodo(solar1.getId(),  bomberos.getId());
        manager.vincularConsumidorANodo(solar1.getId(),  universidad.getId());

        manager.vincularConsumidorANodo(eolica1.getId(), barrio1.getId());
        manager.vincularConsumidorANodo(eolica1.getId(), barrio2.getId());

        manager.vincularConsumidorANodo(hidro1.getId(),  fabrica.getId());
        manager.vincularConsumidorANodo(hidro1.getId(),  hospital.getId());
        manager.vincularConsumidorANodo(hidro1.getId(),  barrio1.getId());

        manager.vincularConsumidorANodo(solar2.getId(),  barrio2.getId());
        manager.vincularConsumidorANodo(solar2.getId(),  universidad.getId());

        System.out.println("Ciudad demo cargada: 4 nodos, 6 consumidores.");
    }
}
