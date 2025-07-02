package otros;

import modelo.Almacenamiento;
import modelo.Moneda;
import modelo.Producto;

public class ProductoCarrito {
    private Producto producto;
    private int cantidad;

    public ProductoCarrito(Producto producto) {
        this.producto = producto;
        this.cantidad = 1;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void aumentarCantidad() {
        if (producto.getStock() > 0) {
            cantidad++;
            producto.incrementarStock();
        }
    }

    public void disminuirCantidad() {
        if (cantidad > 0) {
            cantidad--;
            producto.reducirStock();
        }
    }

    public double getTotalBs(double tasa) {
        return cantidad * producto.getPrecioBs(tasa);
    }

    @Override
    public String toString() {
        return producto.getNombre() + " | Talla: " + producto.getTalla() +
               " | Cant: " + cantidad +
               " | Bs. " + Moneda.formatear(getTotalBs(Almacenamiento.tasaDolar));
    }
}

