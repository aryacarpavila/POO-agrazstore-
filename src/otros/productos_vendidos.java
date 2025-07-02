package otros;

import modelo.Producto;

public class productos_vendidos {
    private Producto producto;
    private int cantidad;

    public productos_vendidos(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void sumarCantidad(int adicional) {
        this.cantidad += adicional;
    }

    public double getTotalVendido() {
        return producto.getPrecioDolar() * cantidad;
    }

    public String getNombreProducto() {
        return (producto != null) ? producto.getNombre() : "Producto no encontrado";
    }
}
