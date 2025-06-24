package modelo;

import java.util.ArrayList;
import java.util.List;

public class Almacenamiento {
    public static double tasaDolar = 100.0;
    public static List<Producto> productos = new ArrayList<>();

    static {
        if (productos.isEmpty()) {
            productos.add(new Producto("Nike Air Force", "Zapatos cómodos", 100, "Zapatos", "Negro", 'M', "Zapatos_default.jpg", 5));
            productos.add(new Producto("Nike Top", "Camisa ejercicio", 120, "Top", "Rojo", 'L', "top_default.jpg", 0));
            productos.add(new Producto("Nike Bottom", "Ropa urbana", 80, "Ropa", "Negro", 'M', "bottom_default.jpg", 2));
        }
    }
}