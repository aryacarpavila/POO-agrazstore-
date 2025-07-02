package modelo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import otros.HoraLocalAdaptador;
import otros.productos_vendidos;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

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

    public static void mostrarReporteDialogo() {
        List<productos_vendidos> vendidos = reporte_productos_vendidos();

        if (vendidos.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                "No hay productos vendidos aún.",
                "Reporte de Ventas",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
       
        String[] columnas = {"PRODUCTO", "TALLA", "PRECIO", "CANTIDAD VENDIDA"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        for (productos_vendidos producto_vendido : vendidos) {
            modelo.addRow(new Object[]{
                producto_vendido.getNombreProducto(),
                producto_vendido.getProducto().getTalla(),
                "$" + producto_vendido.getProducto().getPrecioDolar(),
                producto_vendido.getCantidad()
            });
        }

        JTable tabla = new JTable(modelo);
        tabla.setFont(new Font("Open Sans", Font.BOLD, 16));
        tabla.setRowHeight(30);
        tabla.setBackground(Color.WHITE);
        tabla.setForeground(Color.BLACK);
        tabla.getTableHeader().setFont(new Font("Open Sans", Font.BOLD, 18));
        tabla.getTableHeader().setBackground(new Color(230, 230, 230));
        tabla.getTableHeader().setForeground(Color.BLACK);

        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setPreferredSize(new Dimension(750, 450));

        JOptionPane.showMessageDialog(null,
            scrollPane,
            "📊 Reporte de Productos Vendidos",
            JOptionPane.INFORMATION_MESSAGE);
    }


    
    public static String Resumen_Mensual() {
        List<Transaccion> transacciones = Almacenamiento.cargarTransaccionesDesdeJSON();
        if (transacciones == null || transacciones.isEmpty()) {
            return "📭 No hay transacciones para analizar.\n";
        }

        StringBuilder resumen = new StringBuilder("                                              📊✨ RESUMEN MENSUAL DE VENTAS ✨📊\n\n");
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
            String transaccion_mes = meses_nombre.get(aux_transacciones.getFecha().getMonthValue() - 1) + " " + aux_transacciones.getFecha().getYear();
            if (mes.equals(transaccion_mes)) {
                for (Producto p : aux_transacciones.getProductos()) {
                    boolean encontrado = false;
                    for (productos_vendidos pv : vendidos_por_mes) {
                        if (pv.getProducto().getNombre().equals(p.getNombre())) {
                            pv.sumarCantidad(1);
                            encontrado = true;
                            break;
                        }
                    }
                    if (!encontrado) {
                        vendidos_por_mes.add(new productos_vendidos(p, 1));
                    }
                }
            }
        }

        resumen.append("────────────────────────────────────────────\n");
        resumen.append("🗓️  *").append(mes).append("*\n");

        if (!vendidos_por_mes.isEmpty()) {
            productos_vendidos mas_vendido = vendidos_por_mes.get(0);
            productos_vendidos menos_vendido = vendidos_por_mes.get(0);

            for (productos_vendidos producto_vendido : vendidos_por_mes) {
                if (producto_vendido.getCantidad() > mas_vendido.getCantidad()) mas_vendido = producto_vendido;
                if (producto_vendido.getCantidad() < menos_vendido.getCantidad()) menos_vendido = producto_vendido;
            }

            resumen.append("🔝 Más vendido: *").append(mas_vendido.getNombreProducto())
                   .append("* — ").append(mas_vendido.getCantidad()).append(" unidades\n");
            resumen.append("🔻 Menos vendido: *").append(menos_vendido.getNombreProducto())
                   .append("* — ").append(menos_vendido.getCantidad()).append(" unidades\n");
        } else {
            resumen.append("❌ Sin ventas registradas este mes.\n");
        }

        resumen.append("\n");
    }

        resumen.append("────────────────────────────────────────────\n");
        return resumen.toString();
    }

    public static void mostrarResumenMensualDialogo() {
        String resumenTexto = Resumen_Mensual();

        JTextArea textArea = new JTextArea(resumenTexto);
        textArea.setEditable(false);
        textArea.setFont(new Font("Open Sans", Font.BOLD, 16)); 
        textArea.setBackground(Color.WHITE);                   
        textArea.setForeground(Color.BLACK);                    
        textArea.setLineWrap(true);                            
        textArea.setWrapStyleWord(true);                       

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(750, 500));   

        JOptionPane.showMessageDialog(null,
            scrollPane,
            "📅 Resumen Mensual de Ventas",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
}
