

import controlador.Validacion;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import modelo.Producto;

public class Inventario {

    private List<Producto> productosInv;
    private int stock; // Representa la cantidad de productos (o bien, el total de elementos)
    
    public Inventario() {
        this.productosInv = new ArrayList<>();
        this.stock = 0;
    }
    
    // Agrega un producto al inventario; se valida que no sea nulo y cumpla los requisitos.
    public void agregarProducto(Producto producto) {
        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo");
        }
        if (!Validacion.esProductoValido(producto)) {
            throw new IllegalArgumentException("El producto no cumple las validaciones");
        }
        productosInv.add(producto);
        stock++;
    }
    
    // Busca un producto por nombre (se ignora mayúsculas/minúsculas).
    public Producto buscarProducto(String nombre) {
        for (Producto p : productosInv) {
            if (p.getNombre().equalsIgnoreCase(nombre)) {
                return p;
            }
        }
        return null;
    }
    
    // Aplica un descuento determinado a TODOS los productos del inventario.
    public void aplicarDescuento(double porcentaje) {
        for (Producto producto : productosInv) {
            producto.aplicarDescuento(porcentaje);
        }
    }
    
    // Controla el stock; en este ejemplo se muestra la presencia de cada producto.
    public void controlarStock() {
        System.out.println("Control de Stock en el Inventario:");
        for (Producto producto : productosInv) {
            System.out.println(producto.getNombre() + " - Disponible");
        }
    }
    
    // Métodos de filtrado:
    // 1. Filtrado por categoría.
    public List<Producto> filtrarPorCategoria(String categoria) {
        List<Producto> filtrados = new ArrayList<>();
        for (Producto producto : productosInv) {
            if (producto.getCategoria().equalsIgnoreCase(categoria)) {
                filtrados.add(producto);
            }
        }
        return filtrados;
    }
    
    // 2. Filtrado por precio: productos cuyo precio en dólares sea menor a un límite.
    public List<Producto> filtrarPorPrecioMenor(int precioLimite) {
        List<Producto> filtrados = new ArrayList<>();
        for (Producto producto : productosInv) {
            if (producto.getPrecioDolar() < precioLimite) {
                filtrados.add(producto);
            }
        }
        return filtrados;
    }
    
    // 3. Método genérico de filtrado utilizando la interfaz Predicate.
    public List<Producto> filtrarProductos(Predicate<Producto> filtro) {
        return productosInv.stream()
                           .filter(filtro)
                           .collect(Collectors.toList());
    }
    
    // Getters y Setters
    public List<Producto> getProductosInv() {
        return productosInv;
    }
    
    public void setProductosInv(List<Producto> productosInv) {
        if (productosInv == null) {
            throw new IllegalArgumentException("La lista de productos no puede ser nula");
        }
        this.productosInv = productosInv;
        this.stock = productosInv.size();
    }
    
    public int getStock() {
        return stock;
    }
    
    public void setStock(int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }
        this.stock = stock;
    }
}   