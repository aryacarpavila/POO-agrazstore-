

import controlador.Validacion;
import java.util.ArrayList;
import java.util.List;
import modelo.Producto;

public class CarritoCompra {

    private int id;
    private String nombre;
    private List<Producto> historialCompra;
    
    // Constructor que utiliza la clase Validacion para asegurar un nombre correcto.
    public CarritoCompra(int id, String nombre) {
        if (!Validacion.esStringValido(nombre)) {
            throw new IllegalArgumentException("Nombre inválido para el carrito");
        }
        this.id = id;
        this.nombre = nombre;
        this.historialCompra = new ArrayList<>();
    }
    
    // Muestra los productos actuales en el carrito.
    public void revisarCarrito() {
        System.out.println("Contenido del carrito:");
        for (Producto p : historialCompra) {
            System.out.println(p);
        }
    }
    
    // Método para modificar el carrito: se agrega o elimina un producto según el parámetro 'agregar'.
    public void modificarCarrito(Producto producto, boolean agregar) {
        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo");
        }
        if (agregar) {
            historialCompra.add(producto);
        } else {
            historialCompra.remove(producto);
        }
    }
    
    // Getters y Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        if (!Validacion.esStringValido(nombre)) {
            throw new IllegalArgumentException("Nombre inválido");
        }
        this.nombre = nombre;
    }
    
    public List<Producto> getHistorialCompra() {
        return historialCompra;
    }
    
    public void setHistorialCompra(List<Producto> historialCompra) {
        if (historialCompra == null) {
            throw new IllegalArgumentException("El historial no puede ser nulo");
        }
        this.historialCompra = historialCompra;
    }
}