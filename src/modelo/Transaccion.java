package modelo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import otros.HoraLocalAdaptador;

public class Transaccion {
    private static List<Transaccion> historial_transacciones = new ArrayList<>();
    private static final String base_de_datos_json = "base_de_datos/transacciones.json";
    private LocalDate fecha;
    private double monto;
    private String moneda;
    private List<Producto> productos;
    private Usuario usuario;
    private String metodoPago;

    // Constructor 
    public Transaccion(LocalDate fecha, String metodoPago, List<Producto> productos, double monto, Usuario usuario) {
    this.fecha = fecha;
    this.monto = monto;
    this.moneda = "Bs.";
    this.productos = new ArrayList<>(productos);
    this.usuario = usuario;
    this.metodoPago = metodoPago;
    
        Transaccion.transaccion_guardada(this, base_de_datos_json);
    }
        
    public static void transaccion_guardada(Transaccion transaccion, String rutaArchivo) {
        if (transaccion != null) {
            historial_transacciones.add(transaccion);
            historial_archivo(rutaArchivo);
        }
    }

    public static void cargarHistorial() {
        historial_desde_archivo(base_de_datos_json);
    }
    
    public static void inicio() {
        cargarHistorial();
    }

    public static void transaccion_guardada(Transaccion transaccion) {
        if (transaccion != null) {
            historial_transacciones.add(transaccion);
        }
    }

    // Método para obtener historial
    public static List<Transaccion> historial() {
        return new ArrayList<>(historial_transacciones);
    }

    // Getters
    public LocalDate getFecha() {
        return fecha;
    }

    public double getMonto() {
        return monto;
    }

    public String getMoneda() {
        return moneda;
    }

    public List<Producto> getProductos() {
        return new ArrayList<>(productos);
    }

    public Usuario getUsuario() {
        return usuario;
    }
    
    public String getMetodoPago() {
        return metodoPago;
    }

    public String getMontoFormateado() {
        return String.format("%.2f %s", monto, moneda);
    }

    @Override
    public String toString() {
        return String.format("Transacción - %s - Total: %s", 
                           fecha, getMontoFormateado());
    }
    
    public static void historial_archivo(String rutaArchivo) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new HoraLocalAdaptador()) // Registrar adaptador
                .setPrettyPrinting()
                .create();

        try (FileWriter writer = new FileWriter(rutaArchivo)) {
            gson.toJson(historial_transacciones, writer);
        } catch (IOException e) {
        }
    }

    // Carga el historial desde un archivo JSON
    public static void historial_desde_archivo(String rutaArchivo) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new HoraLocalAdaptador()) // Registrar adaptador
                .create();

        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) {
            historial_transacciones = new ArrayList<>();
            return;
        }

        try (FileReader reader = new FileReader(archivo)) {
            Type lista = new TypeToken<List<Transaccion>>(){}.getType();
            historial_transacciones = gson.fromJson(reader, lista);
            if (historial_transacciones == null) {
                historial_transacciones = new ArrayList<>();
            }
        } catch (IOException e) {
        }
    }

    public static List<Transaccion> getHistorialTransacciones() {
        return historial_transacciones;
    }

    
}