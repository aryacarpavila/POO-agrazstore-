/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author leona
 */
package com.proyecto;
public class Producto {

    // Atributos del producto
    private String nombre;
    private String descripcion;
    private int precioBs;
    private int precioDolar;
    private char talla;
    private String categoria;
    private String color;
    private String rutaImagen;

    // Constructor que valida cada campo usando la clase Validacion.
    public Producto(String nombre, String descripcion, int precioBs, int precioDolar, String categoria, String color, char talla, String rutaImagen) {
        if (!Validacion.esStringValido(nombre)) {
            throw new IllegalArgumentException("Nombre de producto inválido");
        }
        if (!Validacion.esStringValido(descripcion)) {
            throw new IllegalArgumentException("Descripción inválida");
        }
        if (!Validacion.esPrecioValido(precioBs)) {
            throw new IllegalArgumentException("Precio en Bs inválido");
        }
        if (!Validacion.esPrecioValido(precioDolar)) {
            throw new IllegalArgumentException("Precio en dólares inválido");
        }
        if (!Validacion.esStringValido(categoria)) {
            throw new IllegalArgumentException("Categoría inválida");
        }
        if (!Validacion.esStringValido(color)) {
            throw new IllegalArgumentException("Color inválido");
        }
        if (!Validacion.esTallaValida(talla)) {
            throw new IllegalArgumentException("Talla inválida (debe ser S, M, L o X)");
        }
        if (!Validacion.esStringValido(rutaImagen)) {
            throw new IllegalArgumentException("Ruta de imagen inválida");
        }

        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioBs = precioBs;
        this.precioDolar = precioDolar;
        this.talla = talla;
        this.categoria = categoria;
        this.color = color;
        this.rutaImagen = rutaImagen;
    }

    // Aplica un descuento (porcentaje entre 0 y 100) al producto.
    public void aplicarDescuento(double porcentaje) {
        if (porcentaje < 0 || porcentaje > 100) {
            throw new IllegalArgumentException("Porcentaje de descuento inválido");
        }
        this.precioBs = (int)(this.precioBs * (1 - (porcentaje / 100.0)));
        this.precioDolar = (int)(this.precioDolar * (1 - (porcentaje / 100.0)));
    }

    public void editarProducto(String nuevoNombre, String nuevaDescripcion, int nuevoPrecioBs,
                               int nuevoPrecioDolar, char nuevaTalla, String nuevaCategoria, String nuevoColor, String nuevaRutaImagen) {
        if (!Validacion.esStringValido(nuevoNombre)) {
            throw new IllegalArgumentException("Nombre inválido");
        }
        if (!Validacion.esStringValido(nuevaDescripcion)) {
            throw new IllegalArgumentException("Descripción inválida");
        }
        if (!Validacion.esPrecioValido(nuevoPrecioBs)) {
            throw new IllegalArgumentException("Precio en Bs inválido");
        }
        if (!Validacion.esPrecioValido(nuevoPrecioDolar)) {
            throw new IllegalArgumentException("Precio en dólares inválido");
        }
        if (!Validacion.esStringValido(nuevaCategoria)) {
            throw new IllegalArgumentException("Categoría inválida");
        }
        if (!Validacion.esStringValido(nuevoColor)) {
            throw new IllegalArgumentException("Color inválido");
        }
        if (!Validacion.esTallaValida(nuevaTalla)) {
            throw new IllegalArgumentException("Talla inválida");
        }
        if (!Validacion.esStringValido(nuevaRutaImagen)) {
            throw new IllegalArgumentException("Ruta de imagen inválida");
        }

        this.nombre = nuevoNombre;
        this.descripcion = nuevaDescripcion;
        this.precioBs = nuevoPrecioBs;
        this.precioDolar = nuevoPrecioDolar;
        this.talla = nuevaTalla;
        this.categoria = nuevaCategoria;
        this.color = nuevoColor;
        this.rutaImagen = nuevaRutaImagen;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        if (!Validacion.esStringValido(descripcion)) {
            throw new IllegalArgumentException("Descripción inválida");
        }
        this.descripcion = descripcion;
    }

    public int getPrecioBs() {
        return precioBs;
    }

    public void setPrecioBs(int precioBs) {
        if (!Validacion.esPrecioValido(precioBs)) {
            throw new IllegalArgumentException("Precio en Bs inválido");
        }
        this.precioBs = precioBs;
    }

    public int getPrecioDolar() {
        return precioDolar;
    }

    public void setPrecioDolar(int precioDolar) {
        if (!Validacion.esPrecioValido(precioDolar)) {
            throw new IllegalArgumentException("Precio en dólares inválido");
        }
        this.precioDolar = precioDolar;
    }

    public char getTalla() {
        return talla;
    }

    public void setTalla(char talla) {
        if (!Validacion.esTallaValida(talla)) {
            throw new IllegalArgumentException("Talla inválida");
        }
        this.talla = talla;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        if (!Validacion.esStringValido(categoria)) {
            throw new IllegalArgumentException("Categoría inválida");
        }
        this.categoria = categoria;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        if (!Validacion.esStringValido(color)) {
            throw new IllegalArgumentException("Color inválido");
        }
        this.color = color;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        if (!Validacion.esStringValido(rutaImagen)) {
            throw new IllegalArgumentException("Ruta de imagen inválida");
        }
        this.rutaImagen = rutaImagen;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precioBs=" + precioBs +
                ", precioDolar=" + precioDolar +
                ", talla=" + talla +
                ", categoria='" + categoria + '\'' +
                ", color='" + color + '\'' +
                ", rutaImagen='" + rutaImagen + '\'' +
                '}';
    }
}
