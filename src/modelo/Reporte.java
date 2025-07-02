package modelo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import otros.HoraLocalAdaptador;
import otros.productos_vendidos;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Reporte {

    public static List<productos_vendidos> reporte_productos_vendidos() {
        List<productos_vendidos> reporte = new ArrayList<>();

        try (FileReader reader = new FileReader("base_de_datos/transacciones.json")) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new HoraLocalAdaptador())
                    .create();

            Type lista = new TypeToken<List<Transaccion>>() {}.getType();
            List<Transaccion> transacciones = gson.fromJson(reader, lista);

            for (Transaccion transaccion : transacciones) {
                for (Producto producto : transaccion.getProductos()) {
                    boolean encontrado = false;
                    for (productos_vendidos producto_vendido : reporte) {
                        if (producto_vendido.getProducto().getNombre().equalsIgnoreCase(producto.getNombre())
                                && producto_vendido.getProducto().getTalla() == producto.getTalla()
                                && producto_vendido.getProducto().getPrecioDolar() == producto.getPrecioDolar()) {
                            producto_vendido.sumarCantidad(1);
                            encontrado = true;
                            break;
                        }
                    }
                    if (!encontrado) {
                        reporte.add(new productos_vendidos(producto, 1));
                    }
                }
            }

        } catch (IOException e) { }
        return reporte;
    }

    public static String texto_reporte() {
        List<productos_vendidos> vendidos = reporte_productos_vendidos();
        if (vendidos.isEmpty()) {
            return "No hay productos vendidos aún.";
        }

        StringBuilder sb = new StringBuilder("📊 Reporte de Productos Vendidos:\n\n");
        for (productos_vendidos pv : vendidos) {
            sb.append("🔹 Producto: ").append(pv.getNombreProducto()).append("\n");
            sb.append("    Talla: ").append(pv.getProducto().getTalla()).append("\n");
            sb.append("    Precio: $").append(pv.getProducto().getPrecioDolar()).append("\n");
            sb.append("    Cantidad Vendida: ").append(pv.getCantidad()).append("\n\n");
        }
        return sb.toString();
    }
    
    public static String Resumen_Mensual() {
    List<Transaccion> transacciones = Almacenamiento.cargarTransaccionesDesdeJSON();
    if (transacciones == null || transacciones.isEmpty()) {
        return "No hay transacciones para analizar.";
    }

    StringBuilder resumen = new StringBuilder("📅 RESUMEN MENSUAL DE VENTAS\n\n");

    List<String> meses = new ArrayList<>();
    List<String> meses_nombre = List.of("Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                                        "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre");

    for (Transaccion aux_transacciones : transacciones) {
        String mes = meses_nombre.get(aux_transacciones.getFecha().getMonthValue() - 1) + " " + aux_transacciones.getFecha().getYear();
        if (!meses.contains(mes)) {
            meses.add(mes);
        }
    }

    for (String mes : meses) {
        List<productos_vendidos> vendidos_por_mes = new ArrayList<>();

        for (Transaccion aux_transacciones : transacciones) {
            String mesT = meses_nombre.get(aux_transacciones.getFecha().getMonthValue() - 1) + " " + aux_transacciones.getFecha().getYear();
            if (mesT.equals(mes)) {
                for (Producto aux_producto : aux_transacciones.getProductos()) {
                    boolean encontrado = false;
                    for (productos_vendidos producto_vendido : vendidos_por_mes) {
                        if (producto_vendido.getProducto().getNombre().equals(aux_producto.getNombre())) {
                            producto_vendido.sumarCantidad(1);
                            encontrado = true;
                            break;
                        }
                    }
                    if (!encontrado) {
                        vendidos_por_mes.add(new productos_vendidos(aux_producto, 1));
                    }
                }
            }
        }

        // MÁS Y MENOS VENDIDO
        if (!vendidos_por_mes.isEmpty()) {
            productos_vendidos mas_vendido = vendidos_por_mes.get(0);
            productos_vendidos menos_vendido = vendidos_por_mes.get(0);

            for (productos_vendidos producto_vendido : vendidos_por_mes) {
                if (producto_vendido.getCantidad() > mas_vendido.getCantidad()) {
                    mas_vendido = producto_vendido;
                }
                if (producto_vendido.getCantidad() < menos_vendido.getCantidad()) {
                    menos_vendido = producto_vendido;
                }
            }

            resumen.append("🗓 ").append(mes).append(":\n");
            resumen.append("🔝 Más vendido: ").append(mas_vendido.getNombreProducto())
                   .append(" (").append(mas_vendido.getCantidad()).append(" unidades)\n");
            resumen.append("🔻 Menos vendido: ").append(menos_vendido.getNombreProducto())
                   .append(" (").append(menos_vendido.getCantidad()).append(" unidades)\n\n");
        } else {
            resumen.append("🗓 ").append(mes).append(": Sin ventas registradas.\n\n");
        }
    }

    return resumen.toString();
    }

    
}
